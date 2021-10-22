package com.yamal.storage

import android.content.SharedPreferences
import com.google.gson.Gson

class KeyValueStorage(
    private val sharedPreferences: SharedPreferences,
    private val gson: Gson
) {
    fun getBoolean(key: String, defaultValue: Boolean): Boolean =
        sharedPreferences.getBoolean(key, defaultValue)

    fun putBoolean(key: String, value: Boolean) {
        sharedPreferences.edit()
            .putBoolean(key, value)
            .apply()
    }

    fun getInt(key: String, defaultValue: Int): Int =
        sharedPreferences.getInt(key, defaultValue)

    fun putInt(key: String, value: Int) {
        sharedPreferences.edit()
            .putInt(key, value)
            .apply()
    }

    fun <T> getJson(key: String, clazz: Class<T>): T? =
        sharedPreferences.getString(key, null)?.let {
            gson.fromJson(it, clazz)
        }

    fun <T> putJson(key: String, value: T, clazz: Class<T>) {
        gson.toJson(value, clazz).let {
            sharedPreferences.edit()
                .putString(key, it)
                .apply()
        }
    }

    fun remove(key: String) {
        sharedPreferences.edit()
            .remove(key)
            .apply()
    }
}
