package dev.doyeong.theillogical.music;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;

import java.io.IOException;

import dev.doyeong.theillogical.api.APIClient;
import dev.doyeong.theillogical.api.APIInterface;
import dev.doyeong.theillogical.api.APIUtils;

public class MusicManager {
    private static MediaPlayer player;

    private static void startMusic(Context context, String albumId, int index) {
        if (player == null) {
            player = new MediaPlayer();
        } else {
            player.reset();
        }

        try {
            player.setDataSource(context, Uri.parse(APIUtils.getURLFromPath(context, "/api/v1/album/" + albumId + "/" + index + "/music")));
            player.prepareAsync();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void getSongData(Context context, String albumId, int index) {
        APIInterface api = APIClient.getAPI(context);

        api.getMusic(albumId, index);
    }
}
