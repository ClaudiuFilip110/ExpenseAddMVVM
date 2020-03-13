package com.example.expenceappmvvm.data.database.repository

import androidx.lifecycle.LiveData
import com.example.expenceappmvvm.data.database.AppDatabase
import com.example.expenceappmvvm.data.database.entities.User
import io.reactivex.Observable

class UserRepository(private val db: AppDatabase) {

    fun insertUser(user: User) {
        db.userDao().insert(user)
    }

    fun getUserByEmail(email: String): Observable<User> {
        return db.userDao().getUserByEmail(email)
    }

}