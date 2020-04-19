package net.greenmanov.android.rcloneanime.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class DriveEntity {

    public static final int VERSION = 1;

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String Name;

    private String Path;

    private boolean Watched;

    public DriveEntity() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPath() {
        return Path;
    }

    public void setPath(String path) {
        Path = path;
    }

    public boolean isWatched() {
        return Watched;
    }

    public void setWatched(boolean watched) {
        Watched = watched;
    }
}
