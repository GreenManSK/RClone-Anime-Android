package net.greenmanov.android.rcloneanime.activities;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;

import net.greenmanov.android.rcloneanime.R;

public class AnimeActivity extends AbstractActivity {

    public static final String ANIME_ID = "animeId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anime);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        enableBackButton(toolbar);

        int animeId = getIntent().getIntExtra(AnimeActivity.ANIME_ID, 0);
    }
}
