package com.example.gottago.SharedPrefs;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.firebase.auth.FirebaseAuth;

public class MyApplication extends Application {

    private FirebaseAuth mAuth;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    public void onCreate() {
        super.onCreate();

        mAuth = FirebaseAuth.getInstance();
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        // Check if the user is already logged in
        if (mAuth.getCurrentUser() != null) {
            editor.putString("uid", mAuth.getCurrentUser().getUid());
            editor.apply();
        }
    }
}