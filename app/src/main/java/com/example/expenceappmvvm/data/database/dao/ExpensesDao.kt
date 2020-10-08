package com.example.expenceappmvvm.data.database.dao

import androidx.room.*
import com.example.expenceappmvvm.data.database.converters.DateConverter
import com.example.expenceappmvvm.data.database.entities.Action
import io.reactivex.Observable

@TypeConverters(DateConverter::class)
@Dao
interface ExpensesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertExpense(action: Action)

    @Delete
    fun deleteUserExpense(action: Action)

    @Query("Select * from expenses where userId=:userId")
    fun getExpensesByUserId(userId: Long): Observable<List<Action>>

}