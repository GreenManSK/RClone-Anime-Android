package net.greenmanov.android.rcloneanime.persistance;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import net.greenmanov.android.rcloneanime.data.Anime;
import net.greenmanov.android.rcloneanime.data.AnimeFileEntity;

import java.util.List;

@Dao
public interface AnimeFileDao {
    @Update
    void update(AnimeFileEntity anime);

    @Insert()
    long insert(AnimeFileEntity anime);

    @Delete
    void delete(AnimeFileEntity anime);

    @Transaction
    @Query("SELECT * FROM AnimeFileEntity WHERE id = :id LIMIT 1")
    LiveData<AnimeFileEntity> get(int id);

    @Transaction
    @Query("SELECT * FROM AnimeFileEntity WHERE animeId = :animeId")
    List<AnimeFileEntity> listSync(int animeId);
}
