package com.receipt_app.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.receipt_app.R;
import com.receipt_app.adapters.DatabaseAdapter;
import com.receipt_app.network.NetworkManager;
import com.receipt_app.network.api.AuthResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends ToolbarActivity {

    private TextInputLayout mLoginEmail;
    private TextInputLayout mLoginPassword;

    private NetworkManager networkManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        networkManager = new NetworkManager(this);

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Log in to your account");

        mLoginEmail = findViewById(R.id.login_email);
        mLoginPassword = findViewById(R.id.login_password);
    }

    public void onLoginPressed(View view) {
        String email = mLoginEmail.getEditText().getText().toString();
        String password = mLoginPassword.getEditText().getText().toString();

        if (!TextUtils.isEmpty(email) || !TextUtils.isEmpty(password)) {
            loginUser(email, password);
        }
    }

    private void loginUser(String username, String password) {
        LoginActivity app = this;
        networkManager.signIn(username, password, new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(app, "Invalid credentials.", Toast.LENGTH_LONG).show();
                    return;
                }
                Intent intent = new Intent(app, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                Toast.makeText(app, "Invalid credentials.", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void onCreateAccountPressed(View view) {
        startActivity(new Intent(this, RegisterActivity.class));
    }
}
