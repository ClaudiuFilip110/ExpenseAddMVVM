package com.example.expenceappmvvm.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "user")
data class User(
    @PrimaryKey
    var id: Long? = 0
)