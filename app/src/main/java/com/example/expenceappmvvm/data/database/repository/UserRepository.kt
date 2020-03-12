package com.example.expenceappmvvm.data.database.repository

import com.example.expenceappmvvm.data.database.AppDatabase
import com.example.expenceappmvvm.data.database.entities.User

class UserRepository(private val db: AppDatabase) {

    fun insertUser(user: User) {
        db.userDao().insert(user)
    }

}