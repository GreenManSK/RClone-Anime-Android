package net.greenmanov.android.rcloneanime.persistance;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import net.greenmanov.android.rcloneanime.data.AnimeEntity;
import net.greenmanov.android.rcloneanime.data.AnimeFileEntity;
import net.greenmanov.android.rcloneanime.data.DriveEntity;

@Database(
        entities = {AnimeEntity.class, AnimeFileEntity.class, DriveEntity.class},
        version = AnimeEntity.VERSION + AnimeFileEntity.VERSION + DriveEntity.VERSION,
        exportSchema = false
)
public abstract class AppDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "db";

    private static AppDatabase instance;

    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = buildDatabase(context);
        }
        return instance;
    }

    private static AppDatabase buildDatabase(Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, DATABASE_NAME).build();
    }

    public abstract AnimeDao animeDao();

    public abstract DriveDao driveDao();

    public abstract AnimeFileDao animeFileDao();

}
