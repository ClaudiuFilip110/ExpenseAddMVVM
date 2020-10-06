package com.example.expenceappmvvm.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.expenceappmvvm.data.database.entities.AutoLoginUser
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface AutologinUserDao {
    @Query("SELECT * FROM AutoLoginUser WHERE id = 0")
    fun getUser(): Single<AutoLoginUser>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(AutoLoginUser: AutoLoginUser)

    @Query("DELETE from AutoLoginUser")
    fun deleteUser()
}