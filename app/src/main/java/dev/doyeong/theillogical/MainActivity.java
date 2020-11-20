package dev.doyeong.theillogical;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import dev.doyeong.theillogical.databinding.ActivityMainBinding;
import dev.doyeong.theillogical.models.SongModel;
import dev.doyeong.theillogical.music.MusicManager;
import dev.doyeong.theillogical.ui.player.PlayerActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_playlist, R.id.navigation_my_profile, R.id.navigation_preference)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);

        MusicManager.setOnInformationReceivedListener(new MusicManager.OnInformationReceivedListener() {
            @Override
            public void onInformationReceived(SongModel song) {
                binding.setSong(song);
            }

            @Override
            public void onDurationReceived(int duration) {
                binding.setDuration(MusicManager.getTimeString(duration));
            }

            @Override
            public void onIsPlayingChanged(boolean isPlaying) {
                binding.setIsPlaying(isPlaying);
            }
        });

        MusicManager.setOnTimeUpdateListener(time -> {
            binding.setCurrentTime(MusicManager.getTimeString(time));
        });

        binding.mainPlayerController.setOnClickListener(view -> {
            MusicManager.setIsPlaying(!MusicManager.getIsPlaying());
        });
        binding.player.setOnClickListener(view -> {
           Intent intent = new Intent(MainActivity.this, PlayerActivity.class);
           startActivity(intent);
        });
    }

}