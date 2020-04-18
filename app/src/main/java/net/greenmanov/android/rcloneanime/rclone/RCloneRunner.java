package net.greenmanov.android.rcloneanime.rclone;

import android.content.Context;
import android.util.Log;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class RCloneRunner {

    private final static String LOGGER_TAG = RCloneRunner.class.getName();

    private final static String RCLONE_EXECUTABLE = "rclone";
    private final static String RCLONE_CONFIG = "rclone.conf";

    private final String rclonePath;
    private final String rcloneConfigPath;

    private final Context context;
    private IPasswordStorage passwordStorage;

    public RCloneRunner(Context context, IPasswordStorage passwordStorage) {
        this.context = context;
        this.passwordStorage = passwordStorage;
        rclonePath = context.getFilesDir() + File.separator + RCLONE_EXECUTABLE;
        rcloneConfigPath = context.getFilesDir() + File.separator + RCLONE_CONFIG;
    }

    /**
     * Make sure that executable exist and can be used
     */
    public void prepareExecutable() throws IOException {
        if (isExecutableValid()) {
            return;
        }
        try (InputStream inputStream = context.getAssets().open(RCLONE_EXECUTABLE);
             OutputStream outputStream = new FileOutputStream(new File(rclonePath))) {
            IOUtils.copy(inputStream, outputStream);
            Runtime.getRuntime().exec("chmod 0777 " + rclonePath);
        }
    }

    /**
     * Save input stream as config file for rclone
     */
    public void saveConfig(InputStream inputStream) throws IOException {
        File file = new File(rcloneConfigPath);
        if (file.exists()) {
            file.delete();
        }
        try (OutputStream outputStream = new FileOutputStream(file)) {
            IOUtils.copy(inputStream, outputStream);
        } catch (FileNotFoundException e) {
            Log.e(LOGGER_TAG, "Problem while saving configuration", e);
        }
    }

    public List<RcloneFile> ls(String drive, String path) throws RCloneException {
        String[] command = createCommand("lsjson", "" + drive + ":" + path + "");

        JSONArray results;
        Process process;
        try {
            Log.i(LOGGER_TAG, "Running command " + String.join(" ", command));
            process = Runtime.getRuntime().exec(command);

            try (OutputStream out = process.getOutputStream(); PrintStream writer = new PrintStream(out)) {
                writer.println(passwordStorage.getPassword());
            }

            StringBuilder output = new StringBuilder();
            try (InputStream in = process.getInputStream(); InputStreamReader ir = new InputStreamReader(in); BufferedReader reader = new BufferedReader(ir)) {
                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line);
                }
            }

            process.waitFor();
            if (process.exitValue() != 0) {
                logErrorOutput(process);
                throw new RCloneException("Process ended with invalid output value");
            }

            results = new JSONArray(output.toString());

            List<RcloneFile> files = new ArrayList<>();
            for (int i = 0; i < results.length(); i++) {
                JSONObject fileJson = results.getJSONObject(i);
                String filePath = fileJson.getString("Path");
                String name = fileJson.getString("Name");
                boolean isDir = fileJson.getBoolean("IsDir");

                files.add(new RcloneFile(filePath, name, isDir));
            }

            return files;
        } catch (IOException | InterruptedException | JSONException e) {
            throw new RCloneException("Error while doing lsjson", e);
        }
    }

    // TODO: start download

    private boolean isExecutableValid() {
        File file = new File(rclonePath);
        return file.exists() && file.canExecute();
    }

    private String[] createCommand(String command, String params) {
        return new String[]{rclonePath, "--config", rcloneConfigPath, command, params};
    }

    private void logErrorOutput(Process process) {
        try (InputStream in = process.getErrorStream(); InputStreamReader ir = new InputStreamReader(in); BufferedReader reader = new BufferedReader(ir)) {
            String line;
            StringBuilder stringBuilder = new StringBuilder(100);
            try {
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            Log.e(LOGGER_TAG, stringBuilder.toString());
        } catch (IOException e) {
            Log.e(LOGGER_TAG, "Error while reading RClone error output", e);
        }
    }
}
