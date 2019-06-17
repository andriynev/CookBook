package com.receipt_app.network.api;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface FoodApi {
    @POST("/user/signIn")
    Call<AuthResponse> signIn(@Body HashMap<String, String> body);

    @POST("/user/signUp")
    Call<AuthResponse> signUp(@Body HashMap<String, String> body);
}
