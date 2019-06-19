package com.receipt_app.network;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.receipt_app.models.User;
import com.receipt_app.network.api.AuthResponse;
import com.receipt_app.network.api.FoodApi;
import com.receipt_app.utils.UserPreferences;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkManager {
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private Context context;
    private OkHttpClient client;
    private Gson gson;
    private Retrofit retrofit;
    private FoodApi service;

    public NetworkManager(Context context) {
        this.context = context;
        this.client = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request.Builder b = chain.request().newBuilder();
                String token = getAccessToken();
                if (token != null) {
                    b.addHeader("Authorization", String.format("Bearer %s", token));
                }

                return chain.proceed(b.build());
            }
        }).addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();

        gson = new GsonBuilder().setLenient().create();

        String ip = UserPreferences.getIp(context);
        if (ip == null) {
            ip = "192.168.0.105";
        }
        retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl("http://"+ip)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        service = retrofit.create(FoodApi.class);
    }

    public String getAccessToken() {
        return UserPreferences.getToken(this.context);
    }

    public void signIn(String username, String password, Callback<AuthResponse> callback) {
        service.signIn(new HashMap<String, String>(){
            {
                put(USERNAME, username);
                put(PASSWORD, password);
            }
        }).enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, retrofit2.Response<AuthResponse> response) {
                if (response.isSuccessful()) {
                    setAuthData(username, response.body());
                }
                callback.onResponse(call, response);
            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                callback.onFailure(call, t);
            }
        });
    }

    public void signUp(String username, String password, Callback<AuthResponse> callback) {
        service.signUp(new HashMap<String, String>(){
            {
                put(USERNAME, username);
                put(PASSWORD, password);
            }
        }).enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, retrofit2.Response<AuthResponse> response) {
                if (response.isSuccessful()) {
                    setAuthData(username, response.body());
                }
                callback.onResponse(call, response);
            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                callback.onFailure(call, t);
            }
        });
    }

    private void setAuthData(String username, AuthResponse response) {
        if (response == null) {
            return;
        }

        User user = new User(response.getId(), username, response.getAccessToken());
        UserPreferences.saveCurrentUser(this.context, user);
    }
}
