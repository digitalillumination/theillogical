package dev.doyeong.theillogical.ui.player;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import dev.doyeong.theillogical.R;
import dev.doyeong.theillogical.databinding.ActivityPlayerBinding;

import static dev.doyeong.theillogical.music.MusicManager.setPlayerActivityBinding;

public class PlayerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityPlayerBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_player);

        setPlayerActivityBinding(binding);
    }
}