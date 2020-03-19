package com.example.expenceappmvvm.screens.main.fragments.budget

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.expenceappmvvm.data.database.repository.UserRepository
import com.example.expenceappmvvm.data.prefs.PreferencesService
import com.example.expenceappmvvm.domain.util.rx.AppRxSchedulers
import io.reactivex.disposables.CompositeDisposable

class BudgetViewModel(
    private val userRepo: UserRepository,
    private val rxSchedulers: AppRxSchedulers,
    private val preferencesService: PreferencesService,
    private val compositeDisposable: CompositeDisposable
) : ViewModel() {

    val currentBalance = MutableLiveData<String>()
    val todayExpenses = MutableLiveData<String>()
    val weekExpenses = MutableLiveData<String>()
    val monthExpenses = MutableLiveData<String>()

    fun onViewCreated() {
        createBudgetInfo()
    }

    private fun createBudgetInfo() {
        currentBalance.value = "1080.00"
        todayExpenses.value = "-100.00"
        weekExpenses.value = "180.00"
        monthExpenses.value = "10.00"
    }

    fun onDestroy() {
        compositeDisposable.clear()
    }
}
