package com.breckneck.mysecondprojectmoneybox.data.migration

import android.content.Context
import androidx.room.Room
import com.breckneck.mysecondprojectmoneybox.data.database.GoalDatabase
import com.breckneck.mysecondprojectmoneybox.data.entity.Goal
import com.breckneck.mysecondprojectmoneybox.data.storage.MigrationStorage

private val GOALS_LIST_MEMORY = "goals list memory"
private val FIRST_ITEM = "firstitem"
private val FIRST_COST = "firstcost"
private val FIRST_MONEY = "firstmoney"
private val SECOND_ITEM = "seconditem"
private val SECOND_COST = "secondcost"
private val SECOND_MONEY = "secondmoney"
private val THIRD_ITEM = "thirditem"
private val THIRD_COST = "thirdcost"
private val THIRD_MONEY = "thirdmoney"

private val APP_PREFERENCES = "settings"
private val APP_PREFERENCES_ITEM = "item"
private val APP_PREFERENCES_MONEY = "money"
private val APP_PREFERENCES_COST = "cost"
private val APP_PREFERENCES_GOAL_ID = "goalid"

class MigrationStorageImpl(val context: Context): MigrationStorage {

    val db = Room.databaseBuilder(context, GoalDatabase::class.java, "GoalDatabase").build()
    val listGoals = context.getSharedPreferences(GOALS_LIST_MEMORY, Context.MODE_PRIVATE)
    val appSettings = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)

    override fun migration() {
        if (appSettings.contains(APP_PREFERENCES_GOAL_ID)) {
            val id = appSettings.getInt(APP_PREFERENCES_GOAL_ID, 0)
            if (id == 1) {
                if ((appSettings.contains(APP_PREFERENCES_COST)) && (appSettings.contains(APP_PREFERENCES_MONEY)) && (appSettings.contains(APP_PREFERENCES_ITEM)))
                    db.appDao().insertGoal(Goal(id = 1, cost = appSettings.getFloat(APP_PREFERENCES_COST, 0F).toDouble(), money = appSettings.getFloat(APP_PREFERENCES_MONEY, 0F).toDouble(), item = appSettings.getString(APP_PREFERENCES_ITEM, "").toString()))
                if ((listGoals.contains(SECOND_COST)) && (listGoals.contains(SECOND_MONEY)) && (listGoals.contains(SECOND_ITEM)))
                    db.appDao().insertGoal(Goal(id = 2, cost = listGoals.getFloat(SECOND_COST, 0F).toDouble(), money = listGoals.getFloat(SECOND_MONEY, 0F).toDouble(), item = listGoals.getString(SECOND_ITEM, "").toString()))
                if ((listGoals.contains(THIRD_COST)) && (listGoals.contains(THIRD_MONEY)) && (listGoals.contains(THIRD_ITEM)))
                    db.appDao().insertGoal(Goal(id = 3, cost = listGoals.getFloat(THIRD_COST, 0F).toDouble(), money = listGoals.getFloat(THIRD_MONEY, 0F).toDouble(), item = listGoals.getString(THIRD_ITEM, "").toString()))
            } else if (id == 2) {
                if ((listGoals.contains(FIRST_COST)) && (listGoals.contains(FIRST_MONEY)) && (listGoals.contains(FIRST_ITEM)))
                    db.appDao().insertGoal(Goal(id = 1, cost = listGoals.getFloat(FIRST_COST, 0F).toDouble(), money = listGoals.getFloat(FIRST_MONEY, 0F).toDouble(), item = listGoals.getString(FIRST_ITEM, "").toString()))
                if ((appSettings.contains(APP_PREFERENCES_COST)) && (appSettings.contains(APP_PREFERENCES_MONEY)) && (appSettings.contains(APP_PREFERENCES_ITEM)))
                    db.appDao().insertGoal(Goal(id = 2, cost = appSettings.getFloat(APP_PREFERENCES_COST, 0F).toDouble(), money = appSettings.getFloat(APP_PREFERENCES_MONEY, 0F).toDouble(), item = appSettings.getString(APP_PREFERENCES_ITEM, "").toString()))
                if ((listGoals.contains(THIRD_COST)) && (listGoals.contains(THIRD_MONEY)) && (listGoals.contains(THIRD_ITEM)))
                    db.appDao().insertGoal(Goal(id = 3, cost = listGoals.getFloat(THIRD_COST, 0F).toDouble(), money = listGoals.getFloat(THIRD_MONEY, 0F).toDouble(), item = listGoals.getString(THIRD_ITEM, "").toString()))
            } else if (id == 3) {
                if ((listGoals.contains(FIRST_COST)) && (listGoals.contains(FIRST_MONEY)) && (listGoals.contains(FIRST_ITEM)))
                    db.appDao().insertGoal(Goal(id = 1, cost = listGoals.getFloat(FIRST_COST, 0F).toDouble(), money = listGoals.getFloat(FIRST_MONEY, 0F).toDouble(), item = listGoals.getString(FIRST_ITEM, "").toString()))
                if ((listGoals.contains(SECOND_COST)) && (listGoals.contains(SECOND_MONEY)) && (listGoals.contains(SECOND_ITEM)))
                    db.appDao().insertGoal(Goal(id = 2, cost = listGoals.getFloat(SECOND_COST, 0F).toDouble(), money = listGoals.getFloat(SECOND_MONEY, 0F).toDouble(), item = listGoals.getString(SECOND_ITEM, "").toString()))
                if ((appSettings.contains(APP_PREFERENCES_COST)) && (appSettings.contains(APP_PREFERENCES_MONEY)) && (appSettings.contains(APP_PREFERENCES_ITEM)))
                    db.appDao().insertGoal(Goal(id = 3, cost = appSettings.getFloat(APP_PREFERENCES_COST, 0F).toDouble(), money = appSettings.getFloat(APP_PREFERENCES_MONEY, 0F).toDouble(), item = appSettings.getString(APP_PREFERENCES_ITEM, "").toString()))
            }
        }
    }
}