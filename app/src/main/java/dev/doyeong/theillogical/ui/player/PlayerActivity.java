package dev.doyeong.theillogical.ui.player;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.widget.SeekBar;

import dev.doyeong.theillogical.R;
import dev.doyeong.theillogical.databinding.ActivityPlayerBinding;
import dev.doyeong.theillogical.models.SongModel;
import dev.doyeong.theillogical.music.MusicManager;

public class PlayerActivity extends AppCompatActivity {

    private boolean isSeeking = false;
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
        MusicManager.setOnTimeUpdateListener(time -> {
            if (isSeeking) return;
            binding.setCurrentTime(time);
        });
        binding.playerPlayBtn.setOnClickListener(view -> {
            MusicManager.setIsPlaying(!MusicManager.getIsPlaying());
        });
        binding.playerSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                isSeeking = true;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                isSeeking = false;
                if (MusicManager.getCurrentMusic() == null) return;
                MusicManager.getCurrentMusic().getMediaPlayer().seekTo(seekBar.getProgress());
            }
        });
        binding.playerViewPlaylistBtn.setOnClickListener(view -> {
            Intent intent = new Intent(PlayerActivity.this, PlaylistActivity.class);
            startActivity(intent);
        });
        binding.playerPrevBtn.setOnClickListener(view -> {
            MusicManager.prevSong(this);
        });
        binding.playerNextBtn.setOnClickListener(view -> {
            MusicManager.nextSong(this);
        });
    }
}