package dev.doyeong.theillogical.music;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

import dev.doyeong.theillogical.api.APIClient;
import dev.doyeong.theillogical.api.APIInterface;
import dev.doyeong.theillogical.api.APIUtils;
import dev.doyeong.theillogical.models.SongModel;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MusicManager {
    private static MediaPlayer player;
    private static SongModel song;
    private static List<OnInformationReceivedListener> listeners = new ArrayList<>();

    public static void startMusic(Context context, String albumId, int index) {
        if (player == null) {
            player = new MediaPlayer();
        } else {
            player.reset();
        }

        try {
            player.setDataSource(context, Uri.parse(APIUtils.getURLFromPath(context, "/api/v1/album/" + albumId + "/" + index + "/music")));
            player.prepareAsync();

            player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    mediaPlayer.start();
                }
            });
            getSongData(context, albumId, index);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void getSongData(Context context, String albumId, int index) {
        APIInterface api = APIClient.getAPI(context);

        Call<ResponseBody> call = api.getMusic(albumId, index);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (!response.isSuccessful() || response.body() == null) {
                    return;
                }
                try {
                    JSONObject data = new JSONObject(response.body().string()).getJSONObject("data");
                    Gson gson = new Gson();
                    song = gson.fromJson(data.toString(), SongModel.class);
                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                }

                for (OnInformationReceivedListener listener : listeners) {
                    listener.onInformationReceived(song);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public static void setOnInformationReceivedListener(OnInformationReceivedListener listener) {
        listeners.add(listener);
    }

    public static interface OnInformationReceivedListener extends EventListener {
        public void onInformationReceived(SongModel song);
    }
}
