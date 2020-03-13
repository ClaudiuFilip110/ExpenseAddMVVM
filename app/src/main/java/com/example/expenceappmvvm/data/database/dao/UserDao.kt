package com.example.expenceappmvvm.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.expenceappmvvm.data.database.entities.User
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User)

    @Query("Select * from user where email=:email")
    fun getUserByEmail(email: String): Observable<User>

    @Query("Select * from user where id=:userId")
    fun getUserById(userId: Long): Single<User>
}