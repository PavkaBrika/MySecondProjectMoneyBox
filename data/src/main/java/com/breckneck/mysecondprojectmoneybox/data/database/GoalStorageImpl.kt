package com.breckneck.mysecondprojectmoneybox.data.database

import android.content.Context
import androidx.room.Room
import com.breckneck.mysecondprojectmoneybox.data.entity.Goal
import com.breckneck.mysecondprojectmoneybox.data.storage.GoalStorage

class GoalStorageImpl(context: Context): GoalStorage {

    val db = Room.databaseBuilder(context, GoalDatabase::class.java, "GoalDatabase").build()

    override fun createGoal(goal: Goal) {
        db.appDao().insertGoal(goal = goal)
    }

    override fun getGoal(id: Int): Goal {
        return db.appDao().getGoalById(id = id)
    }


}