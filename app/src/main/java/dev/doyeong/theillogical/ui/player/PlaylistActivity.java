package dev.doyeong.theillogical.ui.player;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import dev.doyeong.theillogical.R;
import dev.doyeong.theillogical.databinding.ActivityPlaylistBinding;

public class PlaylistActivity extends AppCompatActivity {

    private ActivityPlaylistBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_playlist);

        PlaylistViewAdapter adapter = new PlaylistViewAdapter();
        binding.playlistRecycler.setAdapter(adapter);
    }
}