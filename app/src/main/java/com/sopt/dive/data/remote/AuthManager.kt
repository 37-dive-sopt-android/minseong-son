package com.sopt.dive.data.remote

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object AuthManager {
    private const val KEY_USER_ID = "user_id"
    private const val KEY_USER_PASSWORD = "user_password"
    private const val KEY_USER_NICKNAME = "user_nickname"
    private const val KEY_USER_ALCOHOL = "user_alcohol"

    private lateinit var appContext: Context

    fun init(context: Context) {
        appContext = context.applicationContext
    }
    private val prefs: SharedPreferences by lazy {
        appContext.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
    }

    suspend fun saveUserCredentials(
        id: String,
        password: String,
        nickname: String,
        alcohol: String
    ) {
        withContext(Dispatchers.IO) {
            prefs.edit {
                putString(KEY_USER_ID, id)
                putString(KEY_USER_PASSWORD, password)
                putString(KEY_USER_NICKNAME, nickname)
                putString(KEY_USER_ALCOHOL, alcohol)
            }
        }
    }

    // Todo : 나중에 묶기
    fun getSavedId(): String {
        return prefs.getString(KEY_USER_ID, "") ?: ""
    }

    fun getSavedPassword(): String {
        return prefs.getString(KEY_USER_PASSWORD, "") ?: ""
    }

    fun getSavedNickname(): String {
        return prefs.getString(KEY_USER_NICKNAME, "") ?: ""
    }

    fun getSavedAlcohol(): String {
        return prefs.getString(KEY_USER_ALCOHOL, "") ?: ""
    }

    suspend fun clearUserCredentials() {
        withContext(Dispatchers.IO) {
            prefs.edit {
                clear()
            }
        }
    }
}