package com.example.expenceappmvvm.data.database.repository

import com.example.expenceappmvvm.data.database.AppDatabase
import com.example.expenceappmvvm.data.database.entities.Expense
import io.reactivex.Observable

class ExpensesRepository(private val db: AppDatabase) {

    fun saveExpense(expense: Expense) {
        db.expenseDao().insertExpense(expense)
    }

    fun deleteExpense(expense: Expense) {
        db.expenseDao().deleteUserExpense(expense)
    }

    fun getExpensesByUserId(userId: Long): Observable<List<Expense>> {
        return db.expenseDao().getExpensesByUserId(userId)
    }
}