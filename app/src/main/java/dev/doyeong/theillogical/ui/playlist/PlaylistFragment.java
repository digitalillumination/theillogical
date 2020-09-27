package dev.doyeong.theillogical.ui.playlist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import dev.doyeong.theillogical.R;
import dev.doyeong.theillogical.databinding.FragmentPlaylistBinding;
import dev.doyeong.theillogical.ui.login.LoginActivity;

public class PlaylistFragment extends Fragment {
    private FragmentPlaylistBinding binding;
    private Context context;

    public static PlaylistFragment newInstance() {
        return new PlaylistFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_playlist, container, false);
        binding.setIsLogin(false);

        Button loginButton = binding.playlistLoginBtn;
        loginButton.setOnClickListener(view -> {
            Intent intent = new Intent(this.context, LoginActivity.class);
            startActivity(intent);
        });
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

}