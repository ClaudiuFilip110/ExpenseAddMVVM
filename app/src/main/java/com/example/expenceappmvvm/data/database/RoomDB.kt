package com.example.expenceappmvvm.data.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import timber.log.Timber


class RoomDB(context: Context) {

    val appDatabase: AppDatabase

    init {
        appDatabase = Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
            .addCallback(object : RoomDatabase.Callback() {})
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    companion object {
        private val DATABASE_NAME = "mirror-database"

    }

}
