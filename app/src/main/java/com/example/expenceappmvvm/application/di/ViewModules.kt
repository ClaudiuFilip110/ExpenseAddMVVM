package com.example.expenceappmvvm.application.di

import com.example.expenceappmvvm.screens.main.MainViewModel
import com.example.expenceappmvvm.screens.register.RegisterViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val viewModelsModule: Module = module {
    viewModel { MainViewModel( get() ) }
    viewModel { RegisterViewModel( get() ) }
}