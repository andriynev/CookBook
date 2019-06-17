package com.receipt_app.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.receipt_app.R;
import com.receipt_app.adapters.DatabaseAdapter;
import com.receipt_app.models.User;
import com.receipt_app.network.NetworkManager;
import com.receipt_app.network.api.AuthResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends ToolbarActivity {

    private DatabaseAdapter databaseAdapter;
    private NetworkManager networkManager;

    private TextInputLayout mUsername;
    private TextInputLayout mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        databaseAdapter = DatabaseAdapter.getInstance(this);
        networkManager = new NetworkManager(this);

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Create Account");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mUsername = findViewById(R.id.reg_display_name);
        mPassword = findViewById(R.id.login_password);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onCreateAccountPressed(View view) {
        String username = mUsername.getEditText().getText().toString();
        String password = mPassword.getEditText().getText().toString();

        registerUser(new User(username, password));
    }

    private void registerUser(User user) {
        RegisterActivity app = this;
        networkManager.signUp(user.getUsername(), user.getPassword(), new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(app, "Invalid params.", Toast.LENGTH_LONG).show();
                    return;
                }
                Intent intent = new Intent(app, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                Toast.makeText(app, "Invalid params.", Toast.LENGTH_LONG).show();
            }
        });
    }
}
