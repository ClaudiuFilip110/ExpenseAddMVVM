package com.example.expenceappmvvm.screens.main.budget

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.expenceappmvvm.data.database.repository.UserRepository
import com.example.expenceappmvvm.domain.util.rx.AppRxSchedulers
import io.reactivex.disposables.CompositeDisposable

class BudgetViewModel(
    private val compositeDisposable: CompositeDisposable,
    private val userRepository: UserRepository,
    private val rxSchedulers: AppRxSchedulers
) : ViewModel() {
    val currentBalance = MutableLiveData<Double>().apply { value = 1080.00 }
    val todayExpenses = MutableLiveData<Double>().apply { value = 108.00 }
    val weekExpenses = MutableLiveData<Double>().apply { value = 10.00 }
    val monthExpenses = MutableLiveData<Double>().apply { value = 1.00 }
}