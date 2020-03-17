package com.example.expenceappmvvm.data.database.dao

import androidx.room.*
import com.example.expenceappmvvm.data.database.entities.Expense
import io.reactivex.Observable

@Dao
interface ExpensesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertExpense(expense: Expense)

    @Delete
    fun deleteUserExpense(expense: Expense)

    @Query("Select * from expenses where userId=:userId")
    fun getExpensesByUserId(userId: Long): Observable<List<Expense>>
}