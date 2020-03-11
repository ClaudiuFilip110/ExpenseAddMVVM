package com.example.expenceappmvvm.data.database.entities

import androidx.room.Entity


@Entity(tableName = "user", primaryKeys = ["id", "email"])
data class User(
    var id: Long = 0,
    var userName: String = "",
    var email: String = "",
    var password: String = ""
)