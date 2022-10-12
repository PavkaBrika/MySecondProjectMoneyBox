package com.breckneck.mysecondprojectmoneybox.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update
import com.breckneck.mysecondprojectmoneybox.data.entity.Goal

@Dao
interface GoalDAO {

    @Insert
    fun insertGoal(goal: Goal)

    @Delete
    fun deleteGoal(goal: Goal)

    @Update
    fun updateGoal(goal: Goal)

}