package dev.doyeong.theillogical.ui.player;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import dev.doyeong.theillogical.R;
import dev.doyeong.theillogical.databinding.ActivityPlayerBinding;

public class PlayerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityPlayerBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_player);
    }
}