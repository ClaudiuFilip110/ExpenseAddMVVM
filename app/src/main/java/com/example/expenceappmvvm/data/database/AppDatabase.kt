package com.example.expenceappmvvm.data.database


import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.expenceappmvvm.data.database.dao.AutologinUserDao
import com.example.expenceappmvvm.data.database.dao.ExpensesDao
import com.example.expenceappmvvm.data.database.dao.UserDao
import com.example.expenceappmvvm.data.database.entities.AutoLoginUser
import com.example.expenceappmvvm.data.database.entities.Action
import com.example.expenceappmvvm.data.database.entities.User


@Database(entities = [User::class, Action::class, AutoLoginUser::class], version = 4)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun expenseDao(): ExpensesDao
    abstract fun autologinDao(): AutologinUserDao
}