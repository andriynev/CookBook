package com.receipt_app.ui;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.elmargomez.typer.Font;
import com.elmargomez.typer.Typer;
import com.receipt_app.R;
import com.receipt_app.adapters.DatabaseAdapter;
import com.receipt_app.adapters.IngredientAdapter;
import com.receipt_app.adapters.IngredientsAdapter;
import com.receipt_app.adapters.MainPagerAdapter;
import com.receipt_app.models.Category;
import com.receipt_app.models.Ingredient;
import com.receipt_app.models.Recipe;
import com.receipt_app.network.NetworkManager;
import com.receipt_app.network.api.IngredientData;
import com.receipt_app.network.api.IngredientsResponse;
import com.receipt_app.ui.fragments.CategorizedFragment;
import com.receipt_app.utils.ActivityTransition;
import com.receipt_app.utils.ResultCodes;
import com.receipt_app.utils.UserPreferences;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IngredientsActivity extends ToolbarActivity {
    private NetworkManager networkManager;
    private List<IngredientData> ingredientList = new ArrayList<>();
    private IngredientsAdapter ingredientAdapter;

    private RecyclerView ingredientRecyclerView;
    private TextView emptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients);
        networkManager = new NetworkManager(this);
        networkManager.getIngredients(new Callback<IngredientsResponse>() {
            @Override
            public void onResponse(Call<IngredientsResponse> call, Response<IngredientsResponse> response) {
                if (!response.isSuccessful()) {
                    return;
                }

                if (response.body() == null) {
                    return;
                }

                ingredientList = response.body().getList();
            }

            @Override
            public void onFailure(Call<IngredientsResponse> call, Throwable t) {

            }
        });

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Ingredients");

        ingredientRecyclerView = findViewById(R.id.recyclerView);
        emptyView = findViewById(R.id.empty_view);

        ingredientAdapter = new IngredientsAdapter(this, ingredientList);
        ingredientAdapter.setIngredientListener(position -> {
            ingredientList.remove(position);
            toggleEmptyView();
            ingredientAdapter.notifyDataSetChanged();
        });

        toggleEmptyView();

        ingredientRecyclerView.setHasFixedSize(true);
        ingredientRecyclerView.setAdapter(ingredientAdapter);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.ingredients:
                intent = new Intent(this, IngredientsActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.sign_out:
                UserPreferences.clear(this);
                navigateToLogin();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void navigateToLogin() {
        Intent startIntent = new Intent(this, LoginActivity.class);
        startActivity(startIntent);
        finish();
    }

    private void toggleEmptyView() {
        if (ingredientList.size() == 0) {
            emptyView.setVisibility(View.VISIBLE);
            ingredientRecyclerView.setVisibility(View.GONE);
        } else {
            emptyView.setVisibility(View.GONE);
            ingredientRecyclerView.setVisibility(View.VISIBLE);
        }
    }
}
