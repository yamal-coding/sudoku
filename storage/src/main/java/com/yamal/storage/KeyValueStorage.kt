package com.yamal.storage

import android.content.SharedPreferences

class KeyValueStorage(
    private val sharedPreferences: SharedPreferences,
) {
    fun getInt(key: String, defaultValue: Int): Int =
        sharedPreferences.getInt(key, defaultValue)

    fun putInt(key: String, value: Int) {
        sharedPreferences.edit()
            .putInt(key, value)
            .apply()
    }
}
