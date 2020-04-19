package net.greenmanov.android.rcloneanime.activities;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;

import net.greenmanov.android.rcloneanime.R;
import net.greenmanov.android.rcloneanime.data.Drive;
import net.greenmanov.android.rcloneanime.persistance.AppDatabase;

import java.util.List;

public class DrivesActivity extends AbstractActivity {

    private AppDatabase database;

    private List<Drive> drives;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drives);

        database = AppDatabase.getInstance(getApplicationContext());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        enableBackButton(toolbar);

        database.driveDao().list().observe(this, drives -> {
            this.drives = drives;
        });
    }
}
