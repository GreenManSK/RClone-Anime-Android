package net.greenmanov.android.rcloneanime.rclone;

import android.util.Log;

import com.google.crypto.tink.Aead;
import com.google.crypto.tink.KeysetHandle;
import com.google.crypto.tink.aead.AeadKeyTemplates;
import com.google.crypto.tink.config.TinkConfig;

import java.security.GeneralSecurityException;

public class TinkPasswordStorage implements IPasswordStorage {

    private static final String LOG_TAG = TinkPasswordStorage.class.getName();

    private final KeysetHandle _k;
    private final Aead _a;
    private final byte[] _p;

    public TinkPasswordStorage(String password) throws GeneralSecurityException {
        _k = KeysetHandle.generateNew(AeadKeyTemplates.AES128_GCM);
        _a = _k.getPrimitive(Aead.class);
        _p = _a.encrypt(password.getBytes(), null);
    }

    @Override
    public String getPassword() {
        try {
            return new String(_a.decrypt(_p, null));
        } catch (GeneralSecurityException e) {
            Log.e(LOG_TAG, "Problem while decrypting password", e);
            return "";
        }
    }
}
