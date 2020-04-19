package net.greenmanov.android.rcloneanime.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class AnimeFileEntity {

    public static final int VERSION = 1;

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;

    public AnimeFileEntity() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
