package dev.doyeong.theillogical.ui.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Debug;
import android.text.TextUtils;
import android.util.Log;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;

import dev.doyeong.theillogical.R;
import dev.doyeong.theillogical.api.APIClient;
import dev.doyeong.theillogical.api.APIInterface;
import dev.doyeong.theillogical.databinding.ActivitySignupBinding;
import dev.doyeong.theillogical.models.SignUpRequestModel;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {


    private ActivitySignupBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_signup);

        // init variables
        binding.setOnProgress(false);

        binding.btnSignupConfirm.setOnClickListener(view -> {
            String userId = binding.getUserId();
            String username = binding.getUsername();
            String password = binding.getPassword();
            String passwordAccept = binding.getPasswordAccept();

            if (TextUtils.isEmpty(userId) || TextUtils.isEmpty(password) || TextUtils.isEmpty(passwordAccept) || TextUtils.isEmpty(username)) {
                alertError("빈 칸을 모두 입력해주세요.");
                return;
            }
            if (!TextUtils.equals(password, passwordAccept)) {
                alertError("비밀번호가 같지 않습니다.");
                return;
            }

            createUser(userId, username, password);
        });
    }

    private void alertError(String error) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(error).setTitle("에러").setNeutralButton("확인", (dialogInterface, i) -> {});
        builder.create().show();
    }
    private void createUser(String userId, String username, String password) {
        APIInterface api = APIClient.getInstance(this).getRetrofit().create(APIInterface.class);
        SignUpRequestModel model = new SignUpRequestModel(userId, password, username);

        Call<ResponseBody> call = api.createUser(model);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, @NotNull Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    finish();
                    return;
                }

                try {
                    if (response.errorBody() == null) {
                        alertError("오류가 발생했습니다. 다시 시도해주세요.");
                        return;
                    }
                    String body = response.errorBody().string();
                    JSONObject object = new JSONObject(body);

                    alertError(object.getString("message"));
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                alertError("서버에서 오류가 발생했습니다.");
            }
        });
    }
}