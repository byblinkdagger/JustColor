package com.blink.dagger.justcolor.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 *
 */
public class PreferencesUtils {
    private static final String SHARED_ID = "lab";

    public static void addToSharedPreferences(Context context, String sharedName, String sharedValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PreferencesUtils.SHARED_ID, Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.putString(sharedName, sharedValue);
        editor.commit();
    }

    public static void addToSharedPreferences(Context context, String sharedName, boolean sharedValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PreferencesUtils.SHARED_ID, Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.putBoolean(sharedName, sharedValue);
        editor.commit();
    }

    public static void addToSharedPreferences(Context context, String sharedName, int sharedValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PreferencesUtils.SHARED_ID, Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.putInt(sharedName, sharedValue);
        editor.commit();
    }

    public static String getValueOfSharedPreferences(Context context, String sharedName, String defaultValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_ID, Context.MODE_PRIVATE);
        return sharedPreferences.getString(sharedName, defaultValue);
    }

    public static boolean getValueOfSharedPreferences(Context context, String sharedName, boolean defaultValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_ID, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(sharedName, defaultValue);
    }

    public static int getValueOfSharedPreferences(Context context, String sharedName, int defaultValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_ID, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(sharedName, defaultValue);
    }

    public static void removeSharedPreferences(Context context, String sharedName) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_ID, Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.remove(sharedName);
        editor.commit();
    }
}
