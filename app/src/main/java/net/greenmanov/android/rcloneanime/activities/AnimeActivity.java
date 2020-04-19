package net.greenmanov.android.rcloneanime.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import net.greenmanov.android.rcloneanime.R;
import net.greenmanov.android.rcloneanime.adapters.AnimeFileAdapter;
import net.greenmanov.android.rcloneanime.data.Anime;
import net.greenmanov.android.rcloneanime.data.AnimeFileEntity;
import net.greenmanov.android.rcloneanime.persistance.AppDatabase;

public class AnimeActivity extends AbstractActivity implements AnimeFileAdapter.OnClickListener {

    public static final String ANIME_ID = "animeId";

    private Anime anime;
    private AnimeFileAdapter adapter;

    private MenuItem downloadMenuItem;
    private MenuItem selectAllMenuItem;
    private MenuItem deselectMenuItem;

    private AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anime);

        database = AppDatabase.getInstance(getApplicationContext());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        enableBackButton(toolbar);

        int animeId = getIntent().getIntExtra(AnimeActivity.ANIME_ID, 0);
        database.animeDao().get(animeId).observe(this, anime -> {
            this.anime = anime;

            getSupportActionBar().setTitle(anime.getName());
            init();
        });
    }

    private void init() {
        adapter = new AnimeFileAdapter(anime.getFileList(), this);
        final RecyclerView fileList = findViewById(R.id.fileList);
        fileList.setLayoutManager(new LinearLayoutManager(this));
        fileList.setHasFixedSize(true);
        fileList.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_file, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        downloadMenuItem = menu.findItem(R.id.menu_download);
        selectAllMenuItem = menu.findItem(R.id.menu_select_all);
        deselectMenuItem = menu.findItem(R.id.menu_deselect);
        enableMenuButtons();
        return true;
    }

    private void enableMenuButtons() {
        boolean selected = adapter != null && adapter.getSelectedCount() > 0;
        downloadMenuItem.setEnabled(selected);
        downloadMenuItem.getIcon().setAlpha(selected ? 255 : 130);
        deselectMenuItem.setEnabled(selected);
        deselectMenuItem.getIcon().setAlpha(selected ? 255 : 130);
    }

    @Override
    public void onClick(AnimeFileEntity file) {
        if (adapter.getSelectedCount() == 0) {
            getSupportActionBar().setTitle(anime.getName());
        } else {
            getSupportActionBar().setTitle(String.valueOf(adapter.getSelectedCount()));
        }
        enableMenuButtons();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.menu_download:
                break;
            case R.id.menu_select_all:
                adapter.selectAll();
                break;
            case R.id.menu_deselect:
                adapter.clearSelect();
                break;
        }
        onClick(null);

        return super.onOptionsItemSelected(item);
    }
}
