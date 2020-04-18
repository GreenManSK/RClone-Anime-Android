package net.greenmanov.android.rcloneanime.data;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class Drive {
    @Embedded
    public DriveEntity drive;

    @Relation(
            parentColumn = "id",
            entityColumn = "id"
    )
    private List<AnimeEntity> animeList;

    public List<AnimeEntity> getAnimeList() {
        return animeList;
    }

    public void setAnimeList(List<AnimeEntity> animeList) {
        this.animeList = animeList;
    }
}
