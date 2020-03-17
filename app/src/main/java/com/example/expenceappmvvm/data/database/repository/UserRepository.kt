package com.example.expenceappmvvm.data.database.repository

import com.example.expenceappmvvm.data.database.AppDatabase
import com.example.expenceappmvvm.data.database.entities.User
import com.example.expenceappmvvm.data.database.entities.UserWithExpenses
import io.reactivex.Observable
import io.reactivex.Single

class UserRepository(private val db: AppDatabase) {

    fun insertUser(user: User) {
        db.userDao().insert(user)
    }

    fun getUserByEmail(email: String): Observable<User> {
        return db.userDao().getUserByEmail(email)
    }

    fun getUserById(id: Long): Single<User> {
        return db.userDao().getUserById(id)
    }
    fun getUserWithExpenses(userId: Long): Observable<List<UserWithExpenses>> {
        return db.userDao().getUsersWithExpenses(userId)
    }
}