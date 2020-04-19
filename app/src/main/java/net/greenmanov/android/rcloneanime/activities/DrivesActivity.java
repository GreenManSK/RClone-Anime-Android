package net.greenmanov.android.rcloneanime.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import net.greenmanov.android.rcloneanime.R;
import net.greenmanov.android.rcloneanime.adapters.AnimeFileAdapter;
import net.greenmanov.android.rcloneanime.adapters.DriveAdapter;
import net.greenmanov.android.rcloneanime.data.Drive;
import net.greenmanov.android.rcloneanime.data.DriveEntity;
import net.greenmanov.android.rcloneanime.dialogs.DriveDialog;
import net.greenmanov.android.rcloneanime.persistance.AppDatabase;

import java.util.ArrayList;
import java.util.List;

public class DrivesActivity extends AbstractActivity implements DriveDialog.OnSubmitListener {

    private static final String DRIVE_DIALOG_TAG = "driveDialog";

    private AppDatabase database;
    private List<Drive> drives;
    private DriveAdapter adapter;

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
            initAdapter();
        });

        findViewById(R.id.addButton).setOnClickListener(v -> {
            onAddButton();
        });
        findViewById(R.id.refreshAllButton).setOnClickListener(v -> {
            onRefreshButton();
        });
    }

    private void initAdapter() {
        adapter = new DriveAdapter(drives);
        final RecyclerView fileList = findViewById(R.id.driveList);
        fileList.setLayoutManager(new LinearLayoutManager(this));
        fileList.setHasFixedSize(false);
        fileList.setAdapter(adapter);
    }

    private void onAddButton() {
        DriveDialog dialog = new DriveDialog(getApplicationContext());
        dialog.show(getSupportFragmentManager(), DRIVE_DIALOG_TAG);
        dialog.setOnSubmitListener(this);
    }

    private void onRefreshButton() {
        Toast.makeText(getApplicationContext(),"Refresh all",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSubmit() {
        database.driveDao().list().observe(this, drives -> {
            this.drives.clear();
            this.drives.addAll(drives);
            adapter.notifyDataSetChanged();
        });
    }
}
