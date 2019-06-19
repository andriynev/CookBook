package com.receipt_app.ui;

import android.content.Intent;
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
import com.receipt_app.utils.UserPreferences;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingsActivity extends ToolbarActivity {
    private TextInputLayout mIp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Settings");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mIp = findViewById(R.id.ip);
        String ip = UserPreferences.getIp(this);
        if (ip != null) {
            mIp.getEditText().setText(ip);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onSetSettings(View view) {
        String ip = mIp.getEditText().getText().toString();
        if (ip != null) {
            UserPreferences.setIp(this, ip);
        }
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}
