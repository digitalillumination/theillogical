package dev.doyeong.theillogical.ui.profile;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;
import java.net.URI;

import dev.doyeong.theillogical.R;
import dev.doyeong.theillogical.api.APIClient;
import dev.doyeong.theillogical.api.APIInterface;
import dev.doyeong.theillogical.api.APIUtils;
import dev.doyeong.theillogical.databinding.FragmentProfileBinding;
import dev.doyeong.theillogical.ui.login.LoginActivity;
import gun0912.tedimagepicker.builder.TedImagePicker;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

        binding.btnProfileChange.setOnClickListener(view -> {
            TedImagePicker
                    .with(context)
                    .showTitle(false)
                    .start(this::setProfileImage);
        });
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();

        binding.setUser(APIUtils.getUser(context));

    }

    private void setProfileImage(Uri uri) {
        Glide.with(this).load(uri).circleCrop().into(binding.imageProfile);
        if (uri.getPath() == null) return;

        File file = new File(uri.getPath());
        RequestBody requestBody = RequestBody.create(file, MediaType.parse("image/*"));
        MultipartBody.Part image = MultipartBody.Part.createFormData("image", file.getName(), requestBody);
        APIInterface api = APIClient.getAPI(context);
        api.putProfileImage(image).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(context, APIUtils.getErrorMessage(response), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(context, "오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
            }
        });


    }
}