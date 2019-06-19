package com.receipt_app.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.receipt_app.models.User;

public class UserPreferences {
    private static final String TAG = UserPreferences.class.getSimpleName();
    private static final String ID = "id";
    private static final String USERNAME = "username";
    private static final String FIRST_RUN = "firstRun";
    private static final String TOKEN = "token";

    public static void saveCurrentUser(Context context, User currentUser) {
        if (currentUser != null) {
            PreferenceManager.getDefaultSharedPreferences(context)
                    .edit()
                    .putInt(ID, currentUser.getId())
                    .putString(USERNAME, currentUser.getUsername())
                    .putString(TOKEN, currentUser.getToken())
                    .apply();
        } else {
            Log.i(TAG, "cannot save current user: user is NULL.");
        }
    }

    public static boolean isUserLoggedIn(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .contains(ID);
    }

    public static void setIsFirstRun(Context context, boolean isFirstRun) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putBoolean(FIRST_RUN, isFirstRun)
                .apply();
    }

    public static boolean isFirstRun(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(FIRST_RUN, true);
    }

    public static String getToken(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(TOKEN, null);
    }

    public static void clear(Context context) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .remove(ID)
                .remove(USERNAME)
                .remove(TOKEN)
                .apply();
    }

    public static void setIp(Context context, String ip) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit().putString("ip", ip).apply();
    }

    public static String getIp(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString("ip", null);
    }
}
