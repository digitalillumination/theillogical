package dev.doyeong.theillogical.models;

import java.io.Serializable;
import java.util.Arrays;

public class PlaylistModel implements Serializable {
    private String name;
    private String[] artists;
    private String imageUrl;

    public PlaylistModel(String name, String[] artists, String imageUrl) {
        this.name = name;
        this.artists = artists;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public String[] getArtists() {
        return artists;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getArtistsFormatted() {
        if (artists.length < 2) {
            return artists[0];
        }

        return String.join(", ", Arrays.copyOfRange(artists, 0, 2)) + (
                artists.length > 2 ? "외 " + (artists.length - 2) + "명" : ""
                );
    }
}
