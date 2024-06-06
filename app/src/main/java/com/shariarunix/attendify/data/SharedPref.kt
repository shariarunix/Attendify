package com.shariarunix.attendify.data

import android.content.SharedPreferences
import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPref @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {
    private val editor = sharedPreferences.edit()

    companion object {
        const val USER_ID = "userID"
        const val REMEMBER_USER = "rememberUser"
        const val IS_DATA_LOADED = "isDataLoaded"
        const val IS_DYNAMIC_THEME = "isDynamicTheme"
        const val IS_DARK_THEME = "isDarkTheme"
    }

    init {
        sharedPreferences.registerOnSharedPreferenceChangeListener { _, key ->
            when (key) {
                "isDynamicTheme" -> {
                    _isDynamicTheme.value = sharedPreferences.getBoolean(IS_DYNAMIC_THEME, false)
                }
                "isDarkTheme" -> {
                    _isDarkTheme.value = sharedPreferences.getBoolean(IS_DARK_THEME, false)
                }
            }
        }
    }

    private var _isDarkTheme = MutableStateFlow(
        sharedPreferences.getBoolean(
            IS_DARK_THEME, false
        )
    )
    val isDarkTheme = _isDarkTheme.asStateFlow()

    fun setDarkTheme(isDark: Boolean) {
        editor.putBoolean(IS_DARK_THEME, isDark)
        editor.apply()
    }

    private var _isDynamicTheme = MutableStateFlow(
        sharedPreferences.getBoolean(
            IS_DYNAMIC_THEME, false
        )
    )
    val isDynamicTheme = _isDynamicTheme.asStateFlow()

    fun setDynamicTheme(isDynamic: Boolean) {
        editor.putBoolean(IS_DYNAMIC_THEME, isDynamic)
        editor.apply()
    }

    fun dataLoaded(isLoaded: Boolean) {
        editor.putBoolean(IS_DATA_LOADED, isLoaded)
        editor.apply()
    }

    fun isDataLoaded(): Boolean {
        return sharedPreferences.getBoolean(IS_DATA_LOADED, false)
    }

    fun saveUserID(userID: String) {
        editor.putString(USER_ID, userID)
        editor.apply()
    }

    fun getUserID(): String? {
        return sharedPreferences.getString(USER_ID, null)
    }

    fun rememberUser(isRemember: Boolean) {
        editor.putBoolean(REMEMBER_USER, isRemember)
        editor.apply()
    }

    fun isRememberUser(): Boolean {
        return sharedPreferences.getBoolean(REMEMBER_USER, false)
    }
}