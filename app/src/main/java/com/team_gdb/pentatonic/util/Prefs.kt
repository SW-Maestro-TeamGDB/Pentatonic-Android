package com.team_gdb.pentatonic.util

import android.content.Context
import android.content.Context.MODE_PRIVATE

class Prefs(context: Context) {
    private val prefName = "preference"
    private val prefs = context.getSharedPreferences(prefName, MODE_PRIVATE)

    var token: String?
        get() = prefs.getString(TOKEN, null)
        set(value) {
            prefs.edit().putString(TOKEN, value).apply()
        }

    var userId: String?
        get() = prefs.getString(USER_ID, null)
        set(value) {
            prefs.edit().putString(USER_ID, value).apply()
        }

    companion object {
        const val TOKEN = "token"
        const val USER_ID = "user_id"
    }
}