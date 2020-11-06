package dev.doyeong.theillogical;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.MediaPlayer;
import android.media.TimedText;
import android.net.Uri;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import dev.doyeong.theillogical.api.APIClient;
import dev.doyeong.theillogical.api.APIInterface;
import dev.doyeong.theillogical.api.APIUtils;
import dev.doyeong.theillogical.databinding.ActivityMainBinding;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MusicManager {
    private static MediaPlayer player;
    private static ActivityMainBinding binding;
    private static Timer timer = new Timer();

    private static JSONObject data;
    public static void start(Context context, String albumId, int index) {
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
                public void onPrepared(MediaPlayer player) {
                    player.start();
                    getDataAndBind(context, albumId, index);

                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            if (binding == null) return;

                            binding.setCurrentTime(getMillisToString(player.getCurrentPosition()));
                        }
                    }, player.getDuration() / 100, 100);
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setMainActivityBinding(ActivityMainBinding binding) {
        MusicManager.binding = binding;

        MusicManager.binding.setIsPlaying(player != null && player.isPlaying());
        MusicManager.binding.mainPlayerController.setOnClickListener(view -> {
            if (player == null) {
                return;
            }

            MusicManager.binding.setIsPlaying(!player.isPlaying());

            if (player.isPlaying()) {
                player.pause();
            } else {
                player.start();
            }
        });
    }

    private static void getDataAndBind(Context context, String albumId, int index) {
        if (binding == null) return;

        APIInterface api = APIClient.getAPI(context);
        Call<ResponseBody> request = api.getMusic(albumId, index);

        request.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(context, APIUtils.getErrorMessage(response), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                try {
                    String body = response.body().string();
                    JSONObject object = new JSONObject(body);

                    data = object.getJSONObject("data");
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }

                try {
                    binding.setTitle(data.getString("title"));
                    binding.setArtist(data.getJSONObject("by").getString("username"));

                    binding.setDuration(getMillisToString(player.getDuration()));
                    binding.setImageId(data.getString("albumImage"));
                    binding.setIsPlaying(player.isPlaying());
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(context, "오류가 발생했습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show();

                player.reset();
            }
        });
    }

    @SuppressLint("DefaultLocale")
    private static String getMillisToString(int millis) {
        int sec = millis/ 1000;

        return String.format("%02d:%02d", sec / 60, sec % 60);
    }
}
