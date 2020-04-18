package net.greenmanov.android.rcloneanime.rclone;

public class RcloneFile {
    private String path;
    private String name;
    private boolean isDir;

    public RcloneFile(String path, String name, boolean isDir) {
        this.path = path;
        this.name = name;
        this.isDir = isDir;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDir() {
        return isDir;
    }

    public void setDir(boolean dir) {
        isDir = dir;
    }

    @Override
    public String toString() {
        return "RcloneFile{" +
                "path='" + path + '\'' +
                ", name='" + name + '\'' +
                ", isDir=" + isDir +
                '}';
    }
}
