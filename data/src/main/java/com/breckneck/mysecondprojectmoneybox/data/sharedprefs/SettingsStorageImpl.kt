package com.breckneck.mysecondprojectmoneybox.data.sharedprefs

import android.content.Context
import com.breckneck.mysecondprojectmoneybox.data.storage.SettingsStorage

private const val APP_PREFERENCES = "settings"
private const val APP_PREFERENCES_APP_WAS_OPENED = "MainActivityOpened"
private const val APP_PREFERENCES_CHARACTER = "character"
private const val APP_PREFERENCES_AUDIO = "audio"
private const val APP_PREFERENCES_VIBRO = "vibro"
private const val APP_PREFERENCES_GOAL_ID = "goalid"
private const val APP_PREFERENCES_AD = "ad";

class SettingsStorageImpl(context: Context): SettingsStorage {

    private val sp = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)

    override fun mainActivityWasOpened(): Boolean {
        val mainActivityWasOpened = sp.getBoolean(APP_PREFERENCES_APP_WAS_OPENED, false)
        return if (!mainActivityWasOpened) {
            sp.edit().putBoolean(APP_PREFERENCES_APP_WAS_OPENED, true).apply()
            mainActivityWasOpened
        } else
            mainActivityWasOpened
    }

    override fun getVibroSetting(): Boolean {
        return sp.getBoolean(APP_PREFERENCES_VIBRO, true)
    }

    override fun setVibro(isEnabled: Boolean) {
        sp.edit().putBoolean(APP_PREFERENCES_VIBRO, isEnabled).apply()
    }

    override fun getCharacter(): Int {
        return sp.getInt(APP_PREFERENCES_CHARACTER, 1)
    }

    override fun setCharacter(character: Int) {
        sp.edit().putInt(APP_PREFERENCES_CHARACTER, character).apply()
    }

    override fun setAudio(audio: Boolean) {
        sp.edit().putBoolean(APP_PREFERENCES_AUDIO, audio).apply()
    }

    override fun getAudio(): Boolean {
        return sp.getBoolean(APP_PREFERENCES_AUDIO, true)
    }

    override fun setLastShowGoalId(id: Int) {
        sp.edit().putInt(APP_PREFERENCES_GOAL_ID, id).apply()
    }

    override fun getLastShowGoalId(): Int {
        return sp.getInt(APP_PREFERENCES_GOAL_ID, 0)
    }

    override fun addButtonClickQuantity() {
        sp.edit().putInt(APP_PREFERENCES_AD, sp.getInt(APP_PREFERENCES_AD, 0) + 1).apply()
     }

    override fun getButtonClickQuantity(): Int {
        return sp.getInt(APP_PREFERENCES_AD, 0)
    }
}