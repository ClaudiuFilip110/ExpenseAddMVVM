package com.example.expenceappmvvm.application

import android.app.Application
import com.example.expenceappmvvm.BuildConfig
import com.example.expenceappmvvm.application.di.*
import com.example.expenceappmvvm.data.database.services.UserService
import com.example.expenceappmvvm.data.prefs.PreferencesService
import com.example.expenceappmvvm.domain.util.rx.AppRxSchedulers
import com.example.expenceappmvvm.domain.util.rx.RxBus
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.get
import org.koin.core.context.startKoin
import timber.log.Timber

class ExpensesApplication : Application() {

    private val rxBus: RxBus by lazy { get().koin.get<RxBus>() }
    private val prefs: PreferencesService by lazy { get().koin.get<PreferencesService>() }
    private val usersService: UserService by lazy { get().koin.get<UserService>() }
    private val schedulers: AppRxSchedulers by lazy { get().koin.get<AppRxSchedulers>() }

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
        val systemHandler = Thread.getDefaultUncaughtExceptionHandler()
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