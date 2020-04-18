package net.greenmanov.android.rcloneanime.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.GridView;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

import net.greenmanov.android.rcloneanime.R;
import net.greenmanov.android.rcloneanime.adapters.AnimeAdapter;
import net.greenmanov.android.rcloneanime.data.Anime;
import net.greenmanov.android.rcloneanime.persistance.AppDatabase;

import java.util.List;
import java.util.stream.Collectors;

public class MainActivity extends AbstractActivity {

    private static boolean watched = false;
    private static String filterText = null;

    private GridView gridView;
    private EditText filterInput;
    private Switch switchButton;

    private List<Anime> anime;
    private Filter filter;

    private AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES); // Dark theme

        database = AppDatabase.getInstance(getApplicationContext());

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        database.animeDao().list().observe(this, anime -> {
            this.anime = anime;
            refreshGrid(anime);

            init();
        });
    }

    private void init() {

        filterInput = findViewById(R.id.filterText);
        filterInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (filter != null) {
                    filter.filter(s);
                    filterText = s.toString();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        switchButton = findViewById(R.id.watched);
        switchButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
            watched = isChecked;
            if (isChecked) {
                refreshGrid(anime.stream().filter(Anime::isWatched).collect(Collectors.toList()));
            } else {
                refreshGrid(anime);
            }
            filter.filter(filterInput.getText().toString());
        });

        gridView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(getBaseContext(), AnimeActivity.class);
            intent.putExtra(AnimeActivity.ANIME_ID, id);
            startActivity(intent);
            finish();
        });

        switchButton.setChecked(watched);
        if (filterText != null && filter != null) {
            filter.filter(filterText);
            filterInput.setText(filterText);
        }
    }

    private void refreshGrid(List<Anime> anime) {
        if (gridView == null) {
            gridView = findViewById(R.id.gridview);
        }
        AnimeAdapter booksAdapter = new AnimeAdapter(this, anime);
        gridView.setAdapter(booksAdapter);
        gridView.setTextFilterEnabled(true);
        filter = booksAdapter.getFilter();
    }
}
