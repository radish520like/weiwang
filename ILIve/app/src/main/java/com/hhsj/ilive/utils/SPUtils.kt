package com.hhsj.ilive.utils

import android.content.Context
import android.content.SharedPreferences

object SPUtils {

    private lateinit var sharedPreferences: SharedPreferences
    fun init(context: Context) {
        sharedPreferences =
            context.getSharedPreferences(context.applicationInfo.packageName, Context.MODE_PRIVATE)
    }

    fun putString(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    fun getString(key: String, default: String = "") = sharedPreferences.getString(key, default)!!
}