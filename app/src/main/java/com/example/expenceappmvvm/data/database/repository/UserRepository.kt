package com.example.expenceappmvvm.data.database.repository

import com.example.expenceappmvvm.data.database.AppDatabase
import com.example.expenceappmvvm.data.database.entities.AutoLoginUser
import com.example.expenceappmvvm.data.database.entities.User
import com.example.expenceappmvvm.data.database.entities.UserWithExpenses
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single

class UserRepository(private val db: AppDatabase) {

    //Users
    //#region
    fun insertUser(user: User) {
        db.userDao().insert(user)
    }

    fun deleteUsers() {
        db.userDao().deleteUsers()
    }

    fun getUserByEmail(email: String): Single<User> {
        return db.userDao().getUserByEmail(email)
    }

    fun getUserById(id: Long): Single<User> {
        return db.userDao().getUserById(id)
    }
    fun getUserWithExpenses(userId: Long): Observable<List<UserWithExpenses>> {
        return db.userDao().getUsersWithExpenses(userId)
    }
    //#endregion
    //AutoLogin
    //#region
    fun insertAutoLogin(user: AutoLoginUser) {
        db.autologinDao().insertUser(user)
    }

    fun deleteAutoLoginUser() {
        db.autologinDao().deleteUser()
    }

    fun getAutoLogin(): Single<AutoLoginUser> {
        return db.autologinDao().getUser()
    }
    //#endregion
}