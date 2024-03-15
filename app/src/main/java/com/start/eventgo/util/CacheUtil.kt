package com.start.eventgo.util

import android.content.Context
import androidx.preference.PreferenceManager

class CacheUtil {

    fun saveBooleanValue(context: Context, key: String?, value: Boolean?) {
        if (value != null) {
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            val editor = preferences.edit()
            editor.putBoolean(key, value)
            editor.apply()
        }
    }

    fun saveStringValue(context: Context, key: String?, value: String?) {
        if (value != null) {
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            val editor = preferences.edit()
            editor.putString(key, value)
            editor.apply()
        }
    }

    fun saveLongValue(context: Context, key: String?, value: Long?) {
        if (value != null) {
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            val editor = preferences.edit()
            editor.putLong(key, value)
            editor.apply()
        }
    }

    fun saveIntegerValue(context: Context, key: String?, value: Int?) {
        if (value != null) {
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            val editor = preferences.edit()
            editor.putInt(key, value)
            editor.apply()
        }
    }

    fun getBooleanValueByKey(context: Context, key: String?): Boolean {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        return preferences.getBoolean(key, false)
    }

    fun getStringValueByKey(context: Context, key: String?): String? {
        val preferences =
            PreferenceManager.getDefaultSharedPreferences(context)
        return preferences.getString(key, "")
    }

    fun getLongValueByKey(context: Context, key: String?): Long {
        val preferences =
            PreferenceManager.getDefaultSharedPreferences(context)
        return preferences.getLong(key, 0L)
    }

    fun getIntValueByKey(context: Context, key: String?): Int {
        val preferences =
            PreferenceManager.getDefaultSharedPreferences(context)
        return preferences.getInt(key, 0)
    }
}
