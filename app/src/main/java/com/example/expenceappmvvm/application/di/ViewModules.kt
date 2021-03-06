package com.example.expenceappmvvm.application.di

import com.example.expenceappmvvm.screens.login.LoginViewModel
import com.example.expenceappmvvm.screens.main.MainViewModel
import com.example.expenceappmvvm.screens.register.RegisterViewModel
import com.example.expenceappmvvm.screens.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val viewModelsModule: Module = module {
    viewModel { SplashViewModel() }
    viewModel { LoginViewModel() }
    viewModel { RegisterViewModel(get(), get()) }
    viewModel { MainViewModel() }
}