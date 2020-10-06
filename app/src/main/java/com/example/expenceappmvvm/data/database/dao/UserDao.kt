package com.example.expenceappmvvm.data.database.dao

import androidx.room.*
import com.example.expenceappmvvm.data.database.entities.User
import com.example.expenceappmvvm.data.database.entities.UserWithExpenses
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User)

    @Query("Select * from user where email=:email")
    fun getUserByEmail(email: String): Single<User>

    @Query("Select * from user where id=:userId")
    fun getUserById(userId: Long): Single<User>

    @Transaction
    @Query("Select * from user where id=:userId")
    fun getUsersWithExpenses(userId: Long): Observable<List<UserWithExpenses>>

    @Query("DELETE from user")
    fun deleteUsers()
}