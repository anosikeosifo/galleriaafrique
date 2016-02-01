package com.galleriafrique.util.helpers;

import android.content.Context;
import android.content.SharedPreferences;

import com.galleriafrique.GalleriaApplication;
import com.galleriafrique.util.tools.Strings;

/**
 * Created by osifo on 10/5/15.
 */
public class PreferenceManager {
    private static final String DEFAULT_PREFERENCES = "galleriafrique";
    public static final String PROPERTY_REG_ID = "reg_id";
    public static final String PROPERTY_APP_VERSION = "app_id";

    public static SharedPreferences getSharedPreferences() {
        return GalleriaApplication.getContext().getSharedPreferences(DEFAULT_PREFERENCES, Context.MODE_PRIVATE);
    }

    public static SharedPreferences.Editor getSharedPreferencesEditor() {
        return getSharedPreferences().edit();
    }

    public static void saveStringPreference(String key, String value) {
        getSharedPreferencesEditor().putString(key, value).commit();
    }

    public static String getStringPreference(String key) {
        return getSharedPreferences().getString(key, Strings.EMPTY);
    }

    public static void saveBooleanPreference(String key, boolean value) {
        getSharedPreferencesEditor().putBoolean(key, value).commit();
    }

    public static boolean getBooleanPreference(String key) {
        return getSharedPreferences().getBoolean(key, false);
    }

    public static int getIntPreference(String key) {
        return getSharedPreferences().getInt(key, 0);
    }

    public static void saveIntPreference(String key, int value) {
        getSharedPreferencesEditor().putInt(key, value).commit();
    }

    public static void removeStringPreference(String key) {
        getSharedPreferencesEditor().putString(key, "").commit();
    }
}
