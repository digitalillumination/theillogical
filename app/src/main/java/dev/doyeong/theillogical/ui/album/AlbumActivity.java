package dev.doyeong.theillogical.ui.album;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import dev.doyeong.theillogical.R;
import dev.doyeong.theillogical.api.APIClient;
import dev.doyeong.theillogical.api.APIInterface;
import dev.doyeong.theillogical.api.APIUtils;
import dev.doyeong.theillogical.databinding.ActivityAlbumBinding;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlbumActivity extends AppCompatActivity {

    private String albumId;
    private ActivityAlbumBinding binding;
    private APIInterface apiInterface;

    private String title;
    private String by;
    private String image;
    private ObservableArrayList<String> songs = new ObservableArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_album);

        Intent intent = getIntent();
        albumId = intent.getStringExtra("albumId");
        apiInterface = APIClient.getAPI(this);

        AlbumViewAdapter adapter = new AlbumViewAdapter();
        binding.albumViewSongs.setAdapter(adapter);
        binding.setSongs(songs);

        binding.setId(albumId);


    }

    @Override
    protected void onResume() {
        super.onResume();

        Call<ResponseBody> call = apiInterface.getAlbum(this.albumId);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (!response.isSuccessful()) {
                        Toast.makeText(AlbumActivity.this, APIUtils.getErrorMessage(response), Toast.LENGTH_SHORT).show();
                        AlbumActivity.this.finish();
                        return;
                    }
                    if (response.body() == null) return;
                    String body = response.body().string();
                    JSONObject object = new JSONObject(body);
                    JSONObject data = object.getJSONObject("data");

                    title = data.getString("title");
                    by = data.getJSONObject("by").getString("username");
                    image = data.getString("image");
                    JSONArray songTitles = data.getJSONArray("songTitles");

                    songs.clear();
                    for (int i = 0; i < songTitles.length(); i++) {
                        songs.add(songTitles.get(i).toString());
                    }

                    binding.setTitle(title);
                    binding.setBy(by);
                    binding.setImage(image);
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(AlbumActivity.this, "오류가 발생했습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                    AlbumActivity.this.finish();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                call.cancel();
                t.printStackTrace();
                Toast.makeText(AlbumActivity.this, "오류가 발생했습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                AlbumActivity.this.finish();
            }
        });
    }
}