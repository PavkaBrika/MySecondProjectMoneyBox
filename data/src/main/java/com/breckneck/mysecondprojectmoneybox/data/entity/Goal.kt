package com.breckneck.mysecondprojectmoneybox.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Goal(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var cost: Double,
    var money: Double,
    var item: String
    )