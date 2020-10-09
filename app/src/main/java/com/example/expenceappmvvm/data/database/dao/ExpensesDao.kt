package com.example.expenceappmvvm.data.database.dao

import androidx.room.*
import com.example.expenceappmvvm.data.database.converters.DateConverter
import com.example.expenceappmvvm.data.database.entities.Action
import io.reactivex.Observable
import io.reactivex.Single
import java.util.*

@TypeConverters(DateConverter::class)
@Dao
interface ExpensesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertExpense(action: Action)

    @Delete
    fun deleteUserExpense(action: Action)

    @Query("Select * from expenses where userId=:userId")
    fun getExpensesByUserId(userId: Long): Observable<List<Action>>

    @Query("SELECT * FROM expenses")
    fun getActions(): Single<List<Action>>

    @Query("SELECT SUM(amount) FROM expenses WHERE date <= :date ORDER BY date DESC")
    fun getBalanceUntilDate(date: Date): Single<Double>
}