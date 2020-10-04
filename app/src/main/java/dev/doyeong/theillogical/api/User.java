package dev.doyeong.theillogical.api;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Base64;

public class User {
    private String token;
    private String id;
    private String username;
    private String userId;
    private Context context;
    public User(String token, Context context) {
        this.token = token;
        this.context = context;
        this.decodeToken();
    }
    public String getToken() {
        return token;
    }
    private void decodeToken() {
        byte[] target = this.token.split("\\.")[1].getBytes();
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] decodedBytes = decoder.decode(target);
        String decodedString = new String(decodedBytes);
        try {
            JSONObject json = new JSONObject(decodedString);
            this.id = json.getString("id");
            this.username = json.getString("username");
            this.userId = json.getString("userId");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getUserId() {
        return userId;
    }

    public String getProfileImageURL() {
        return APIUtils.getURLFromPath(this.context, "/api/v1/user/" + this.id + "/profile_image");
    }
}
