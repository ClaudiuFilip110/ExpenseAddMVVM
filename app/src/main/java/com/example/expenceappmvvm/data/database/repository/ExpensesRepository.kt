package com.example.expenceappmvvm.data.database.repository

import com.example.expenceappmvvm.data.database.AppDatabase
import com.example.expenceappmvvm.data.database.entities.Action
import io.reactivex.Observable

class ExpensesRepository(private val db: AppDatabase) {

    fun saveExpense(action: Action) {
        db.expenseDao().insertExpense(action)
    }

    fun deleteExpense(action: Action) {
        db.expenseDao().deleteUserExpense(action)
    }

    fun getExpensesByUserId(userId: Long): Observable<List<Action>> {
        return db.expenseDao().getExpensesByUserId(userId)
    }
}