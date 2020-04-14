package net.greenmanov.android.rcloneanime.activities;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import net.greenmanov.android.rcloneanime.R;
import net.greenmanov.android.rcloneanime.dialogs.UnlockDialog;

public abstract class AbstractActivity extends AppCompatActivity {

    private final static String UNLOCK_DIALOG_TAG = "unlockDialog";

    private UnlockDialog unlockDialog;

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
        MenuItem item = menu.findItem(R.id.menu_lock);
        item.setEnabled(false); // TODO: base on real state
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.menu_settings:
                return true;
            case R.id.menu_unlock:
                showUnlockDialog();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private  void showUnlockDialog() {
        unlockDialog = new UnlockDialog();
        unlockDialog.show(getSupportFragmentManager(), UNLOCK_DIALOG_TAG);
    }
}
