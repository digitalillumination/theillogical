package dev.doyeong.theillogical.api;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import dev.doyeong.theillogical.R;
import retrofit2.Response;

public class APIUtils {
    public static String getErrorMessage(Response<?> response) {
        if (response.errorBody() == null) {
            return "오류가 발생했습니다. 다시 시도해주세요.";
        }
        try {
            String body = response.errorBody().string();
            JSONObject object = new JSONObject(body);

            return object.getString("message");
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return "오류가 발생했습니다. 다시 시도해주세요.";
        }
    }
    public static void registerLoginToken(Context context, String token) {
        SharedPreferences preferences = context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString(context.getString(R.string.login_token), token);
        editor.apply();
    }
    public static String getLoginToken(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        return preferences.getString(context.getString(R.string.login_token), null);
    }
    public static boolean isLogined(Context context) {
        return getLoginToken(context) != null;
    }
    public static User getUser(Context context) {
        String token = getLoginToken(context);
        if (token == null) return null;

        return new User(token, context);
    }
}
