package net.greenmanov.android.rcloneanime.data;

import java.util.List;

public class Drive {
    private String Name;
    private String Path;
    private boolean Watched;
    private List<Anime> animeList;

    public Drive() {
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

    public List<Anime> getAnimeList() {
        return animeList;
    }

    public void setAnimeList(List<Anime> animeList) {
        this.animeList = animeList;
    }
}
