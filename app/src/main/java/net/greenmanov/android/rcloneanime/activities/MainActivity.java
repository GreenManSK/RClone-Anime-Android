package net.greenmanov.android.rcloneanime.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.GridView;
import android.widget.Switch;

import net.greenmanov.android.rcloneanime.R;
import net.greenmanov.android.rcloneanime.adapters.AnimeAdapter;
import net.greenmanov.android.rcloneanime.data.Anime;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class MainActivity extends AbstractActivity {

    private static boolean watched = false;
    private static String filterText = null;

    private GridView gridView;
    private EditText filterInput;
    private Switch switchButton;

    private Anime[] anime;
    private Filter filter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES); // Dark theme

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        generateAnime();
        refreshGrid(Arrays.asList(anime));

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
                refreshGrid(Arrays.stream(anime).filter(Anime::isWatched).collect(Collectors.toList()));
            } else {
                refreshGrid(Arrays.asList(anime));
            }
            filter.filter(filterInput.getText().toString());
        });

        gridView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(getBaseContext(), AnimeActivity.class);
            intent.putExtra(AnimeActivity.ANIME_ID, id);
            startActivity(intent);
            finish();
        });

        init();
    }

    private void init() {
        switchButton.setChecked(watched);
        if (filterText != null && filter != null) {
            filter.filter(filterText);
            filterInput.setText(filterText);
        }
    }

    private void generateAnime() {
        Random rand = new Random();

        int count = 50;
        anime = new Anime[count];
        for (int i = 0; i < count; i++) {
            anime[i] = new Anime("3-gatsu no lion");
            if (rand.nextFloat() > 0.5) {
                anime[i].setImage("https://static.tvtropes.org/pmwiki/pub/images/SangatsuNoLionMain_3703.jpg");
            }
            anime[i].setWatched(rand.nextFloat() > 0.5);
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
