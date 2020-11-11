package dev.doyeong.theillogical.music;

import dev.doyeong.theillogical.models.SongModel;

public interface MusicListener {
    public void modelRecived(SongModel model);
    public void timestampUpdated(String timestamp);
}
