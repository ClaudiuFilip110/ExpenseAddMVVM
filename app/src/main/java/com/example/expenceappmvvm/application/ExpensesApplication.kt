package com.example.expenceappmvvm.application

import android.app.Application
import com.example.expenceappmvvm.BuildConfig
import com.example.expenceappmvvm.application.di.*
import com.facebook.stetho.Stetho
import com.jakewharton.threetenabp.AndroidThreeTen
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class ExpensesApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        app = this

        startKoin {
            androidContext(this@ExpensesApplication)
            modules(
                listOf(
                    viewModelsModule,
                    gsonModule,
                    rxModule,
                    networkModule,
                    restModule,
                    databaseModule,
                    preferencesModule,
                    dataModule
                )
            )
        }

        initTimber()
        Stetho.initializeWithDefaults(this)
        AndroidThreeTen.init(this)
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            //  Timber.plant(ReleaseTree())
        }
    }

    companion object {
        lateinit var app: ExpensesApplication
    }
}