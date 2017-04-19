package com.wengelef.kotlinmvvmtest.db

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.wengelef.kotlinmvvmtest.extension.put
import com.wengelef.kotlinmvvmtest.model.User
import javax.inject.Inject

class UserDB @Inject constructor(context: Context) {

    private val sharedPrefs: SharedPreferences by lazy { context.getSharedPreferences("UsersDB", Context.MODE_PRIVATE) }

    private val gson = GsonBuilder().setPrettyPrinting().create()

    private val KEY = "USERS_KEY"

    fun put(users: List<User>) {
        sharedPrefs.put { putString(KEY, gson.toJson(users).toString()) }
    }

    fun get(): List<User>? {
        return sharedPrefs.getString(KEY, null)?.let {
            gson.fromJson(it, object : TypeToken<List<User>>(){}.type)
        }
    }
}