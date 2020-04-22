package net.greenmanov.android.rcloneanime.data;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class Anime {
    @Embedded
    public AnimeEntity anime;

    @Relation(
            parentColumn = "id",
            entityColumn = "animeId"
    )
    private List<AnimeFileEntity> fileList;

    public int getId() {
        return anime.getId();
    }

    public String getName() {
        return anime.getName();
    }

    public String getImage() {
        return anime.getImage();
    }

    public boolean isWatched() {
        return anime.isWatched();
    }

    public List<AnimeFileEntity> getFileList() {
        return fileList;
    }

    public void setFileList(List<AnimeFileEntity> fileList) {
        this.fileList = fileList;
    }
}
