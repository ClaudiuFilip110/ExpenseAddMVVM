package com.example.expenceappmvvm.application.di

import androidx.databinding.adapters.Converters
import com.example.expenceappmvvm.domain.util.rx.AppRxSchedulers
import com.example.expenceappmvvm.domain.util.rx.RxBus
import com.google.gson.GsonBuilder
import io.reactivex.disposables.CompositeDisposable
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.Module
import org.koin.dsl.module

val gsonModule: Module = module {
//    single {
//        val builder = GsonBuilder()
//        Converters.registerAll(builder)
//        builder.create()
//    }
}

val rxModule: Module = module {
    single { AppRxSchedulers() }
    single { RxBus() }
    factory { CompositeDisposable() }
}

