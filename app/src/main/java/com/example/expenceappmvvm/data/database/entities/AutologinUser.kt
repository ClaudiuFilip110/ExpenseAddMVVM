package com.example.expenceappmvvm.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "AutoLoginUser")
data class AutoLoginUser(
    @PrimaryKey()
    val id: Long = 0,
    var name: String = "",
    var email: String = "",
    var password: String = ""
)