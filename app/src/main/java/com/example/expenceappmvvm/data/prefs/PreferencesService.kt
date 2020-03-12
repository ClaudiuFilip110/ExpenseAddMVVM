package com.example.expenceappmvvm.data.prefs

import android.content.Context
import android.content.SharedPreferences

class PreferencesService(private val context: Context) {
    private var sharedPreferences: SharedPreferences =
            context.getSharedPreferences(name, Context.MODE_PRIVATE)

    private fun getLong(key: String): Long {
        return sharedPreferences.getLong(key, 0)
    }

    fun getUserID(): Long? {
        val userId = getLong(USER_ID)
        if (userId > 0) {
            return userId
        }
        return null
    }

    fun <T> setValue(key: String, value: T) {
        val editor = sharedPreferences.edit()

        if (value is Boolean) {
            editor.putBoolean(key, value as Boolean)
        } else if (value is String) {
            editor.putString(key, value as String)
        } else if (value is Long) {
            editor.putLong(key, value as Long)
        }

        editor.apply()
    }

    fun clearUserId() {
        sharedPreferences.edit().remove(USER_ID).apply()
    }

    companion object {
        const val USER_ID = "USER_ID"
        private const val name = "app"
    }
}