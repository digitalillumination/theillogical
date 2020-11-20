package dev.doyeong.theillogical.ui.player;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import dev.doyeong.theillogical.R;
import dev.doyeong.theillogical.databinding.ActivityPlayerBinding;
import dev.doyeong.theillogical.models.SongModel;
import dev.doyeong.theillogical.music.MusicManager;

public class PlayerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityPlayerBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_player);

        MusicManager.setOnInformationReceivedListener(new MusicManager.OnInformationReceivedListener() {
            @Override
            public void onInformationReceived(SongModel song) {
                binding.setSong(song);
            }

            @Override
            public void onDurationReceived(int duration) {
                binding.setDuration(duration);
            }

            @Override
            public void onIsPlayingChanged(boolean isPlaying) {
                binding.setIsPlaying(isPlaying);
            }
        });
        MusicManager.setOnTimeUpdateListener(binding::setCurrentTime);
        binding.playerPlayBtn.setOnClickListener(view -> {
            MusicManager.setIsPlaying(!MusicManager.getIsPlaying());
        });

    }
}