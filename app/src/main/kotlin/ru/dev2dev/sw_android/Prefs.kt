package ru.dev2dev.sw_android

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

object Prefs {
    private val KEY_PEOPLE = "people"

    fun getPrefs(context: Context): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    fun savePeople(context: Context, people: String) {
        getPrefs(context).edit().putString(KEY_PEOPLE, people).apply()
    }

    fun getPeople(context: Context): String {
        return getPrefs(context).getString(KEY_PEOPLE, "")
    }
}
