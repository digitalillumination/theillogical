package dev.doyeong.theillogical.api;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Base64;

import dev.doyeong.theillogical.R;

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
        try {
            URL url = new URL(context.getString(R.string.api_url));
            URL profileURL = new URL(url.getProtocol(), url.getHost(), url.getPort(), "/api/v1/user/" + this.id + "/profile_image");

            return profileURL.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
