package com.example.expenceappmvvm.data.database.entities

import androidx.room.Embedded
import androidx.room.Relation

data class UserWithExpenses(
    @Embedded
    var user: User,
    @Relation(parentColumn = "id", entityColumn = "userId", entity = Expense::class)
    var expenses: List<Expense>
) {
    constructor() : this(User(), mutableListOf())
}