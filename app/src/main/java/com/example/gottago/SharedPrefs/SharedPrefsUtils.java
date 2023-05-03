package com.example.gottago.SharedPrefs;


import android.content.Context;
import android.content.SharedPreferences;

import com.example.gottago.Announces.DetailActivity;

public class SharedPrefsUtils {

    private static final String PREFS_NAME = "MyPrefsFile";
    private static final String KEY_UID = "uid";

    public static String getUid(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getString(KEY_UID, null);
    }

    public static void setUid(Context context, String uid) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit();
        editor.putString(KEY_UID, uid);
        editor.apply();
    }

    public static void clearUid(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit();
        editor.remove(KEY_UID);
        editor.apply();
    }
}
