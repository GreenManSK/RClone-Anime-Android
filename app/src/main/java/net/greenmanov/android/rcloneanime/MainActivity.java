package net.greenmanov.android.rcloneanime;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;

import net.greenmanov.android.rcloneanime.adapters.AnimeAdapter;
import net.greenmanov.android.rcloneanime.data.Anime;

public class MainActivity extends AbstractActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES); // Dark theme

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        int count = 50;
        Anime[] anime = new Anime[count];
        for (int i = 0; i < count; i++) {
            anime[i] = new Anime("3-gatsu no lion");
        }

        GridView gridView = (GridView) findViewById(R.id.gridview);
        AnimeAdapter booksAdapter = new AnimeAdapter(this, anime);
        gridView.setAdapter(booksAdapter);
    }
}
