package dev.doyeong.theillogical.models;

import java.io.Serializable;

public class SongModel implements Serializable {
    private String albumId;
    private String albumTitle;
    private String albumImage;
    private String title;
    private ArtistModel by;

    public SongModel(String albumId, String albumTitle, String albumImage, String title, ArtistModel by) {
        this.albumId = albumId;
        this.albumTitle = albumTitle;
        this.albumImage = albumImage;
        this.title = title;
        this.by = by;
    }

    public String getAlbumId() {
        return albumId;
    }

    public String getAlbumTitle() {
        return albumTitle;
    }

    public String getAlbumImage() {
        return albumImage;
    }

    public String getTitle() {
        return title;
    }

    public ArtistModel getBy() {
        return by;
    }

    public static class ArtistModel implements Serializable {
        private String _id;
        private String username;
        private String profile_image;

        public ArtistModel(String _id, String username, String profile_image) {
            this._id = _id;
            this.username = username;
            this.profile_image = profile_image;
        }

        public String get_id() {
            return _id;
        }

        public String getUsername() {
            return username;
        }

        public String getProfile_image() {
            return profile_image;
        }
    }
}
