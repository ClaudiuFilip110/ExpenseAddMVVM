package com.example.expenceappmvvm.data.database


import android.bluetooth.BluetoothClass
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.expenceappmvvm.data.database.converters.EquipmentsConverter
import com.example.expenceappmvvm.data.database.dao.UserDao
import com.example.expenceappmvvm.data.database.entities.User


@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}