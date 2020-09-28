package dev.doyeong.theillogical.ui.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import dev.doyeong.theillogical.R;
import dev.doyeong.theillogical.api.APIClient;
import dev.doyeong.theillogical.api.APIInterface;
import dev.doyeong.theillogical.api.APIUtils;
import dev.doyeong.theillogical.databinding.ActivityLoginBinding;
import dev.doyeong.theillogical.models.LoginRequestModel;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        binding.btnLoginSignup.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
            startActivity(intent);
        });

        binding.btnLogin.setOnClickListener(view -> {
            if (TextUtils.isEmpty(binding.getUserId()) || TextUtils.isEmpty(binding.getPassword())) {
                alertError("빈 칸을 모두 채워주세요.");
                return;
            }

            this.requestLogin(binding.getUserId(), binding.getPassword());
        });
    }
    private void alertError(String error) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(error).setTitle("에러").setNeutralButton("확인", (dialogInterface, i) -> {});
        builder.create().show();
    }

    private void requestLogin(String userId, String password) {
        APIInterface api = APIClient.getAPI(this);
        Call<ResponseBody> call = api.createToken(new LoginRequestModel(userId, password));

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        String body = response.body().string();
                        String token = new JSONObject(body).getString("data");

                        APIUtils.registerLoginToken(LoginActivity.this, token);
                        finish();
                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                        alertError("오류가 발생했습니다.");
                    }
                    return;
                }

                alertError(APIUtils.getErrorMessage(response));
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                alertError("에러가 발생했습니다. 다시 시도해주세요.");
            }
        });
    }
}