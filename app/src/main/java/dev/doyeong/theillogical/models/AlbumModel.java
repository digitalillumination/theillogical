package dev.doyeong.theillogical.models;

import java.io.Serializable;

public class AlbumModel implements Serializable {
    private String imageUrl;
    private String title;
    private String artist;
    private String id;

    public AlbumModel(String id, String title, String artist, String imageUrl) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
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

    @Override
    public String toString() {
        return "AlbumModel{" +
                "imageUrl='" + imageUrl + '\'' +
                ", title='" + title + '\'' +
                ", artist='" + artist + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
