package dev.doyeong.theillogical.models;

import java.io.Serializable;

public class LoginRequestModel implements Serializable {
    private String userId;
    private String password;

    public LoginRequestModel(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }
}
