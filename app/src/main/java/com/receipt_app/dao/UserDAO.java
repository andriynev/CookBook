package com.receipt_app.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.receipt_app.adapters.DatabaseAdapter;
import com.receipt_app.models.User;

public class UserDAO {

    private SQLiteDatabase db;

    public UserDAO(SQLiteDatabase db) {
        this.db = db;
    }

    public void insert(User user) {
        ContentValues values = new ContentValues();
        values.put(Config.KEY_USERNAME, user.getUsername());
        values.put(Config.KEY_PASSWORD, user.getPassword());

        insert(values);
    }

    private long insert(ContentValues values) {
        return db.insert(Config.TABLE_NAME, null, values);
    }


    public static class Config {
        public static final String TABLE_NAME = "users";
        public static final String KEY_ID = "id";
        public static final String KEY_USERNAME = "username";
        public static final String KEY_FULLNAME = "fullname";
        public static final String KEY_EMAIL = "email";
        public static final String KEY_PASSWORD = "password";

        public static final String CREATE_TABLE_STATEMENT =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        KEY_USERNAME + " TEXT NOT NULL, " +
                        KEY_FULLNAME + " TEXT NOT NULL, " +
                        KEY_EMAIL + " TEXT NOT NULL, " +
                        KEY_PASSWORD + " TEXT NOT NULL)";
    }
}
