package com.example.expenceappmvvm.application.di

import com.example.expenceappmvvm.screens.actions.ActionViewModel
import com.example.expenceappmvvm.screens.login.LoginViewModel
import com.example.expenceappmvvm.screens.main.MainViewModel
import com.example.expenceappmvvm.screens.main.budget.BudgetViewModel
import com.example.expenceappmvvm.screens.main.expenses.ExpensesViewModel
import com.example.expenceappmvvm.screens.main.expenses.viewpager.ViewPagerViewModel
import com.example.expenceappmvvm.screens.register.RegisterViewModel
import com.example.expenceappmvvm.screens.splash.SplashViewModel
import com.example.expenceappmvvm.screens.test.TestViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val viewModelsModule: Module = module {
    viewModel { SplashViewModel(get(), get(), get()) }
    viewModel { LoginViewModel(get(), get(), get()) }
    viewModel { RegisterViewModel(get(), get(), get()) }
    viewModel { MainViewModel(get(), get(), get()) }
    viewModel { BudgetViewModel(get(), get(), get()) }
    viewModel { ExpensesViewModel(get(), get(), get()) }
    viewModel { ActionViewModel(get(), get(), get()) }
    viewModel { TestViewModel(get(), get(), get()) }
    viewModel { ViewPagerViewModel(get(), get(), get()) }
}