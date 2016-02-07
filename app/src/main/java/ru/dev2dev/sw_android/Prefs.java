package ru.dev2dev.sw_android;

import android.content.Context;
import android.content.SharedPreferences;

public class Prefs {
    private static final String PREFS_FILE = "settings";
    private static final String KEY_PEOPLE = "people";

    public static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE);
    }

    public static void savePeople(Context context, String people) {
        getPrefs(context).edit().putString(KEY_PEOPLE, people).apply();
    }

    public static String getPeople(Context context) {
        return getPrefs(context).getString(KEY_PEOPLE, null);
    }
}
