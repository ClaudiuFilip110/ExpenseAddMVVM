package com.example.expenceappmvvm.application.di

import com.example.expenceappmvvm.data.database.RoomDB
import com.example.expenceappmvvm.data.database.repository.ExpensesRepository
import com.example.expenceappmvvm.data.database.repository.UserRepository
import com.example.expenceappmvvm.data.prefs.PreferencesService
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

val databaseModule: Module = module {
    single { RoomDB(androidContext()).appDatabase }
}

val preferencesModule: Module = module {
    single { PreferencesService(androidContext()) }
}

val dataModule: Module = module {
    single { UserRepository(get()) }
    single { ExpensesRepository(get()) }
}
