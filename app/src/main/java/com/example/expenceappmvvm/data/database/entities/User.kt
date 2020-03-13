package com.example.expenceappmvvm.data.database.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "user", indices = [Index(value = ["email", "userName"], unique = true)])
data class User(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var userName: String? = "",
    var email: String = "",
    var password: String? = ""

) {
    override fun toString(): String {
        return "User(id=$id, userName='$userName', email='$email', password='$password')"
    }
}