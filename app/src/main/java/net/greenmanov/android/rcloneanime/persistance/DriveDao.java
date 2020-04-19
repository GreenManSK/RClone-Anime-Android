package net.greenmanov.android.rcloneanime.persistance;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import net.greenmanov.android.rcloneanime.data.Drive;
import net.greenmanov.android.rcloneanime.data.DriveEntity;

import java.util.List;

@Dao
public interface DriveDao {
    @Update
    void update(DriveEntity drive);

    @Insert()
    long insert(DriveEntity drive);

    @Delete
    void delete(DriveEntity drive);

    @Transaction
    @Query("SELECT * FROM DriveEntity d ORDER BY d.Path, d.Name")
    LiveData<List<Drive>> list();

    @Transaction
    @Query("SELECT * FROM DriveEntity WHERE id = :id LIMIT 1")
    LiveData<Drive> get(int id);
}
