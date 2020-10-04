package dev.doyeong.theillogical.models;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public class AlbumModel implements Serializable {
    private String image;
    private String title;
    private String artist;
    private String id;

    public AlbumModel(String id, String title, String artist, String image) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.image = image;
    }

    public String getImageId() {
        return this.image;
    }

    public String getTitle() {
        return title;
    }
    public String getShortTitle() {
        if (title.length() < 14) return title;

        return title.substring(0, 13) + "...";
    }
    public String getArtist() {
        return artist;
    }


    public String getId() {
        return id;
    }

    @NotNull
    @Override
    public String toString() {
        return "AlbumModel{" +
                "image='" + image + '\'' +
                ", title='" + title + '\'' +
                ", artist='" + artist + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
