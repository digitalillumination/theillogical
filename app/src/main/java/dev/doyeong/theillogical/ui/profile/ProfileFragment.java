package dev.doyeong.theillogical.ui.profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dev.doyeong.theillogical.R;
import dev.doyeong.theillogical.api.APIUtils;
import dev.doyeong.theillogical.databinding.FragmentProfileBinding;
import dev.doyeong.theillogical.ui.login.LoginActivity;

public class ProfileFragment extends Fragment {
    private Context context;
    private FragmentProfileBinding binding;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);
        binding.setUser(APIUtils.getUser(context));

        binding.btnProfileLogin.setOnClickListener(view -> {
            Intent intent = new Intent(this.context, LoginActivity.class);
            startActivity(intent);
        });
        return binding.getRoot();
    }
}