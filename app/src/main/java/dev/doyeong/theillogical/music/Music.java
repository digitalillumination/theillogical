package dev.doyeong.theillogical.music;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import dev.doyeong.theillogical.api.APIClient;
import dev.doyeong.theillogical.api.APIInterface;
import dev.doyeong.theillogical.api.APIUtils;
import dev.doyeong.theillogical.models.SongModel;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Music {
    private String albumId;
    private int index;

    private MediaPlayer player;
    private Music.OnDataReceivedListener listener;
    private Music.OnTimeUpdateListener updateListener;
    private Handler handler = new Handler();
    private SongModel song = null;


    public Music(String albumId, int index) {
        this.albumId = albumId;
        this.index = index;
    }

    public void createMediaPlayer(Context context) {
        player = new MediaPlayer();

        try {
            player.setDataSource(context, Uri.parse(APIUtils.getURLFromPath(context, "/api/v1/album/" + albumId + "/" + index + "/music")));
            player.prepareAsync();
            handler.postDelayed(updateTimeRunner, 100);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void getData(Context context) {
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

                if (listener != null)
                    listener.onDataReceived(song);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(context, "오류가 발생했습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public SongModel getSong() {
        return song;
    }
    public void stopPlay() {
        if (player == null) return;

        handler.removeCallbacksAndMessages(null);
        player.stop();
        player.release();
    }
    public MediaPlayer getMediaPlayer() {
        return player;
    }

    private Runnable updateTimeRunner = new Runnable() {
        @Override
        public void run() {
            if (player == null) return;

            updateListener.onTimeUpdate(player.getCurrentPosition());
            handler.postDelayed(this, 100);
        }
    };

    public void setOnInformationReceivedListener(OnDataReceivedListener listener) {
        this.listener = listener;

        if (song != null) {
            this.listener.onDataReceived(song);
        }
    }

    public void setOnTimeUpdateListener(OnTimeUpdateListener listener) {
        this.updateListener = listener;
    }

    public static interface OnDataReceivedListener {
        public void onDataReceived(SongModel song);
    }
    public static interface OnTimeUpdateListener {
        public void onTimeUpdate(int time);
    }
}
