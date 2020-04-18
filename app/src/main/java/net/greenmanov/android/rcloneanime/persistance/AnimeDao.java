package net.greenmanov.android.rcloneanime.persistance;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import net.greenmanov.android.rcloneanime.data.Anime;
import net.greenmanov.android.rcloneanime.data.AnimeEntity;

import java.util.List;

@Dao
public interface AnimeDao {
    @Update
    void update(AnimeEntity anime);

    @Insert()
    long insert(AnimeEntity anime);

    @Delete
    void delete(AnimeEntity anime);

    @Transaction
    @Query("SELECT * FROM AnimeEntity")
    LiveData<List<Anime>> list();

    @Transaction
    @Query("SELECT * FROM AnimeEntity WHERE id = :id LIMIT 1")
    LiveData<Anime> get(int id);
}
