package com.receipt_app.network.api;

import com.receipt_app.models.Ingredient;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface FoodApi {
    @POST("/user/signIn")
    Call<AuthResponse> signIn(@Body HashMap<String, String> body);

    @POST("/user/signUp")
    Call<AuthResponse> signUp(@Body HashMap<String, String> body);

    @GET("/v1/ingredients")
    Call<IngredientsResponse> getIngredients();

    @GET("/v1/ingredients/{id}")
    Call<IngredientResponse> getIngredient(@Path("id") int ingredientId);
}
