package com.example.expenceappmvvm.data.database.entities

import androidx.room.Embedded
import androidx.room.Relation

data class UserWithExpenses(
    @Embedded
    var user: User,
    @Relation(parentColumn = "id", entityColumn = "userId", entity = Action::class)
    var expens: List<Action>
) {
    constructor() : this(User(), mutableListOf())
}