package com.example.expenceappmvvm.screens.main.expenses

import androidx.lifecycle.ViewModel
import com.example.expenceappmvvm.data.database.repository.UserRepository
import com.example.expenceappmvvm.domain.util.rx.AppRxSchedulers
import io.reactivex.disposables.CompositeDisposable

class ExpensesViewModel(
    private val compositeDisposable: CompositeDisposable,
    private val userRepository: UserRepository,
    private val rxSchedulers: AppRxSchedulers
) : ViewModel() {

}