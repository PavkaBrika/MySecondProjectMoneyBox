package com.breckneck.mysecondprojectmoneybox.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.breckneck.mysecondprojectmoneybox.data.entity.Goal

@Database (entities = [Goal::class], version = 1)
abstract class GoalDatabase: RoomDatabase() {
    abstract fun appDao(): GoalDAO
}