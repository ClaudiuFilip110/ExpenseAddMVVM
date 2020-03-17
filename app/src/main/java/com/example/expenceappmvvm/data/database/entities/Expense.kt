package com.example.expenceappmvvm.data.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "expenses",
    foreignKeys = [ForeignKey(
        entity = User::class,
        parentColumns = ["id"],
        childColumns = ["userId"]
    )]
)
data class Expense(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var userId: Long = 0,
    var amount: Double = 0.0,
    var expenseDate: Long = 0,
    var category: String = "",
    var details: String = "",
    var picturePath: String = ""
)