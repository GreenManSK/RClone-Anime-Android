package net.greenmanov.android.rcloneanime.activities;

import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import net.greenmanov.android.rcloneanime.AppController;
import net.greenmanov.android.rcloneanime.R;
import net.greenmanov.android.rcloneanime.dialogs.UnlockDialog;

import java.security.GeneralSecurityException;

public abstract class AbstractActivity extends AppCompatActivity {

    private final static String LOGGER_TAG = AbstractActivity.class.getName();
    private final static String UNLOCK_DIALOG_TAG = "unlockDialog";

    private UnlockDialog unlockDialog;
    private MenuItem lockMenuItem;

    protected void enableBackButton(Toolbar toolbar) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        lockMenuItem = menu.findItem(R.id.menu_lock);
        updateLockMenuItem();
        return super.onPrepareOptionsMenu(menu);
    }

    protected void updateLockMenuItem() {
        lockMenuItem.setEnabled(AppController.getPasswordStorage() != null);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.menu_drives:
                Intent intent = new Intent(getBaseContext(), DrivesActivity.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.menu_unlock:
                showUnlockDialog();
                return true;
            case R.id.menu_lock:
                AppController.removePassword();
                updateLockMenuItem();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private  void showUnlockDialog() {
        unlockDialog = new UnlockDialog((password, durationInMs) -> {
            try {
                AppController.setPassword(password, durationInMs);
            } catch (GeneralSecurityException e) {
                Log.e(LOGGER_TAG, "Error while setting password", e);
                Toast.makeText(getApplicationContext(), R.string.passwordError, Toast.LENGTH_SHORT).show();
            }
            updateLockMenuItem();
        });
        unlockDialog.show(getSupportFragmentManager(), UNLOCK_DIALOG_TAG);
    }
}
