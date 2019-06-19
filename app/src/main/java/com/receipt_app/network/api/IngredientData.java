package com.receipt_app.network.api;

public class AuthResponse {
    private int id;
    private String access_token;

    public AuthResponse() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccessToken() {
        return access_token;
    }

    public void setAccessToken(String accessToken) {
        this.access_token = accessToken;
    }
}
