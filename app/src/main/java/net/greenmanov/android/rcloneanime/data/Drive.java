package net.greenmanov.android.rcloneanime.data;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.time.LocalDateTime;
import java.util.List;

public class Drive {
    @Embedded
    public DriveEntity drive;

    @Relation(
            parentColumn = "id",
            entityColumn = "driveId"
    )
    private List<AnimeEntity> animeList;


    public int getId() {
        return drive.getId();
    }

    public String getName() {
        return drive.getName();
    }

    public String getPath() {
        return drive.getPath();
    }

    public boolean isWatched() {
        return drive.isWatched();
    }

    public LocalDateTime getLastRefresh() {
        return drive.getLastRefresh();
    }
    public List<AnimeEntity> getAnimeList() {
        return animeList;
    }

    public void setAnimeList(List<AnimeEntity> animeList) {
        this.animeList = animeList;
    }

    public DriveEntity getDrive() {
        return drive;
    }

    public String getFullName() {
        return getName() + ":" + getPath();
    }
}
