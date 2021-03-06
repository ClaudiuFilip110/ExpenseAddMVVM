package com.example.expenceappmvvm.data.database


import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.expenceappmvvm.data.database.dao.ExpensesDao
import com.example.expenceappmvvm.data.database.dao.UserDao
import com.example.expenceappmvvm.data.database.entities.Expense
import com.example.expenceappmvvm.data.database.entities.User


@Database(entities = [User::class, Expense::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun expenseDao(): ExpensesDao
}