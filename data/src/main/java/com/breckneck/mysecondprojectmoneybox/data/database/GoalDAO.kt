package com.breckneck.mysecondprojectmoneybox.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
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

    @Query("SELECT * FROM goal WHERE id = :id")
    fun getGoalById(id: Int): Goal

    @Query("SELECT EXISTS(SELECT * FROM goal WHERE id = :id)")
    fun checkGoal(id: Int): Int

    @Query("UPDATE goal SET money = :money WHERE id = :id")
    fun changeMoney(id: Int, money: Double)

    @Query("DELETE FROM goal WHERE id = :id")
    fun resetGoal(id: Int)

    @Query("SELECT * FROM goal")
    fun getAllGoals(): List<Goal>

    @Query("SELECT id FROM goal ORDER BY id DESC LIMIT 1")
    fun getLastGoalId(): Int
}