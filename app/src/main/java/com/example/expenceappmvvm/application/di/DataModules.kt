package com.example.expenceappmvvm.application.di

import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

val databaseModule: Module = module {
   // single { RoomDB(androidContext()).appDatabase }

}

val preferencesModule: Module = module {
   // single { PreferenceManager.getDefaultSharedPreferences(androidApplication()) }
}

val dataModule: Module = module {
   // single { ExercicesRepository() }
}
