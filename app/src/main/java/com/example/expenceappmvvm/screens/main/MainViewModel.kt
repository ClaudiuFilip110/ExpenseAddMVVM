package com.example.expenceappmvvm.screens.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.expenceappmvvm.data.database.repository.UserRepository
import com.example.expenceappmvvm.data.prefs.PreferencesService
import com.example.expenceappmvvm.domain.util.SingleLiveEvent
import com.example.expenceappmvvm.domain.util.rx.AppRxSchedulers
import com.example.expenceappmvvm.domain.util.rx.disposeBy
import io.reactivex.disposables.CompositeDisposable

class MainViewModel(
    private val userRepo: UserRepository,
    private val rxSchedulers: AppRxSchedulers,
    private val preferencesService: PreferencesService,
    private val compositeDisposable: CompositeDisposable
) : ViewModel() {
    val shouldGoToAddActivity = SingleLiveEvent<Any>()
    val shouldGoToLoginActivity = SingleLiveEvent<Any>()
    val shouldGoToConvertActivity = SingleLiveEvent<Any>()
    val userName = MutableLiveData<String>()

    fun onCreate() {
        getUserBySavedId()
    }

    private fun getUserBySavedId() {
        preferencesService.getUserID()?.let {
            userRepo.getUserById(it)
                .subscribeOn(rxSchedulers.background())
                .observeOn(rxSchedulers.androidUI())
                .doOnSuccess { user ->
                    userName.value = user.userName
                }
                .subscribe({}, {
                    logOutItemClick()
                })
                .disposeBy(compositeDisposable)
        }
    }

    fun convertItemClick() {
        shouldGoToConvertActivity.call()
    }

    fun logOutItemClick() {
        preferencesService.clearUserId()
        shouldGoToLoginActivity.call()
    }

    fun addTransactionClick() {
        shouldGoToAddActivity.call()
    }

    fun onDestroy() {
        compositeDisposable.clear()
    }
}
