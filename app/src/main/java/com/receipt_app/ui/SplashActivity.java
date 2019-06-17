package com.receipt_app.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.receipt_app.R;
import com.receipt_app.adapters.DatabaseAdapter;
import com.receipt_app.models.Direction;
import com.receipt_app.models.Ingredient;
import com.receipt_app.models.Recipe;
import com.receipt_app.models.User;
import com.receipt_app.utils.UserPreferences;

public class SplashActivity extends AppCompatActivity {

    private static final int REQUEST_TO_WRITE = 1;
    private static final int REQUEST_TO_ACCESS_INTERNET = 2;
    private DatabaseAdapter databaseAdapter;

    private void onFirstRun() {
        UserPreferences.setIsFirstRun(this, false);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_TO_WRITE);
        }

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.INTERNET},
                    REQUEST_TO_ACCESS_INTERNET);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseAdapter = DatabaseAdapter.getInstance(this);

        if (UserPreferences.isFirstRun(this)) {
            onFirstRun();
            navigateToLogin();
            return;
        }

        if (UserPreferences.isUserLoggedIn(this)) {
            navigateToMainPage();
        }
        else {
            navigateToLogin();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_TO_WRITE:
                if (grantResults.length == 0) {
                    Toast.makeText(this, "Permission denied to write default recipes.", Toast.LENGTH_LONG)
                            .show();
                    navigateToLogin();
                    return;
                }

                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission denied to write default recipes.", Toast.LENGTH_LONG)
                            .show();
                    navigateToLogin();
                }

                navigateToLogin();
                break;
            case REQUEST_TO_ACCESS_INTERNET:
                if (grantResults.length == 0) {
                    Toast.makeText(this, "Permission denied to access to the Internet.", Toast.LENGTH_LONG)
                            .show();
                    navigateToLogin();
                    return;
                }

                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission denied to access to the Internet.", Toast.LENGTH_LONG)
                            .show();
                    navigateToLogin();
                }

                navigateToLogin();
                break;
        }
    }

    /*private void loadAsianRecipes() throws IOException {
        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.asian_chicken);
        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();

        File file = new File(extStorageDirectory, "asian_chicken.PNG");
        FileOutputStream outStream = new FileOutputStream(file);
        bm.compress(Bitmap.CompressFormat.PNG, 100, outStream);
        outStream.flush();
        outStream.close();

        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(new Ingredient("1 Pound thinly sliced Chicken Breasts, or boneless chicken thighs"));
        ingredients.add(new Ingredient("1 Tablespoon Olive Oil"));
        ingredients.add(new Ingredient("¾ cup brown sugar"));
        ingredients.add(new Ingredient("⅓ cup soy sauce"));
        ingredients.add(new Ingredient("2 Tablespoons hoisin sauce"));
        ingredients.add(new Ingredient("1 Tablespoon Sweet Chili Sauce"));
        ingredients.add(new Ingredient("1 Tablespoons ginger, peeled and grated"));
        ingredients.add(new Ingredient("Pinch of dried red pepper flakes, to taste"));
        ingredients.add(new Ingredient("½ teaspoon minced garlic"));
        ingredients.add(new Ingredient("Juice of one lime"));
        List<Direction> directions = new ArrayList<>();
        directions.add(new Direction("Heat a large skillet over medium high heat and add 1 Tbs of olive oil. Add the chicken and salt and pepper. Cook chicken about 3 minutes on each side or until thermometer reads 165 degrees and brown on each side. Set chicken aside on plate"));
        directions.add(new Direction("In the skillet whisk together brown sugar, soy sauce, hoisin sauce, sweet chili sauce, ginger, red pepper flakes, garlic and lime juice. Bring to a boil over medium heat for 1-2 minutes until sauce thickens"));
        directions.add(new Direction("Add chicken back to the sauce and coat each side with the sauce. Garnish with sesame seeds and chopped green onions.\n"));
        databaseAdapter.addNewRecipe(new Recipe("Asian chicken",
                "Asian", "Tender and juicy chicken breasts that get coated in a sticky sweet asian sauce.  This meal is ready in just thirty minutes and the flavor is awesome!", ingredients, directions, file.getAbsolutePath()));
    }*/

    private void navigateToLogin() {
        Intent startIntent = new Intent(this, LoginActivity.class);
        startActivity(startIntent);
        finish();
    }

    private void navigateToMainPage() {
        Intent startIntent = new Intent(this, MainActivity.class);
        startActivity(startIntent);
        finish();
    }
}
