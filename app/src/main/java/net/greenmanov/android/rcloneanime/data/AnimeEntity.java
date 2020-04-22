package net.greenmanov.android.rcloneanime.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class AnimeEntity {

    public static final int VERSION = 1;

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int driveId;

    private String name;

    private String image;

    private boolean watched;

    public AnimeEntity() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDriveId() {
        return driveId;
    }

    public void setDriveId(int driveId) {
        this.driveId = driveId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isWatched() {
        return watched;
    }

    public void setWatched(boolean watched) {
        this.watched = watched;
    }
}
