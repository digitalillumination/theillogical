package dev.doyeong.theillogical.models;

import java.io.Serializable;

public class SignUpRequestModel implements Serializable {
    private String userId;
    private String password;
    private String username;

    public SignUpRequestModel(String userId, String password, String username) {
        this.userId = userId;
        this.password = password;
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }
}
