package com.breckneck.mysecondprojectmoneybox.data.sharedprefs

import android.content.Context
import com.breckneck.mysecondprojectmoneybox.data.storage.SettingsStorage

private val APP_PREFERENCES = "settings"
private val MainActivityOpened = "MainActivityOpened"
private val APP_PREFERENCES_CHARACTER = "character"
private val APP_PREFERENCES_AUDIO = "audio"
private val APP_PREFERENCES_VIBRO = "vibro"
private val APP_PREFERENCES_GOAL_ID = "goalid";

class SettingsStorageImpl(context: Context): SettingsStorage {

    private val sp = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)

    override fun mainActivityWasOpened(): Boolean {
        val mainActivityWasOpened = sp.getBoolean(MainActivityOpened, false)
        return if (!mainActivityWasOpened) {
            sp.edit().putBoolean(MainActivityOpened, true).apply()
            mainActivityWasOpened
        } else
            mainActivityWasOpened
    }

    override fun getLastGoalId(): Int {
        return sp.getInt(APP_PREFERENCES_GOAL_ID, 1)
    }
}