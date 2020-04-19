package net.greenmanov.android.rcloneanime;

import com.google.crypto.tink.aead.AeadConfig;
import com.google.crypto.tink.config.TinkConfig;

import net.greenmanov.android.rcloneanime.persistance.AppDatabase;
import net.greenmanov.android.rcloneanime.rclone.TinkPasswordStorage;

import java.security.GeneralSecurityException;
import java.util.Timer;
import java.util.TimerTask;

public class AppController {

    private static boolean tinkRegistered = false;
    private static TinkPasswordStorage passwordStorage;
    private static Timer timer;

    private AppController() {
    }

    public static TinkPasswordStorage getPasswordStorage() {
        return passwordStorage;
    }

    public static void setPassword(String password, int durationInMs) throws GeneralSecurityException {
        if (!tinkRegistered) {
            AeadConfig.register();
            tinkRegistered = true;
        }
        removePassword();
        passwordStorage = new TinkPasswordStorage(password);
        startTimer(durationInMs);
    }

    public static void removePassword() {
        if (passwordStorage != null) {
            passwordStorage.Destroy();
            passwordStorage = null;
        }
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    private static void startTimer(int durationInMs) {
        timer = new Timer(true);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                removePassword();
            }
        }, durationInMs);
    }
}
