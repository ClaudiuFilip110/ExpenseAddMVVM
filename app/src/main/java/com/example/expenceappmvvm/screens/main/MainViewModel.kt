package com.example.expenceappmvvm.screens.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.expenceappmvvm.data.database.repository.UserRepository
import com.example.expenceappmvvm.domain.util.Constants
import com.example.expenceappmvvm.domain.util.SingleLiveEvent
import com.example.expenceappmvvm.domain.util.rx.AppRxSchedulers
import com.example.expenceappmvvm.domain.util.rx.disposeBy
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable

class MainViewModel(
    private val compositeDisposable: CompositeDisposable,
    private val userRepository: UserRepository,
    private val rxSchedulers: AppRxSchedulers
) : ViewModel() {
    val toolbarText = MutableLiveData<String>()
    val bottomNav = MutableLiveData<Boolean>()
    val addAction = SingleLiveEvent<Boolean>()
    val logOut = SingleLiveEvent<Boolean>()

    fun clickAddAction() {
        addAction.call()
    }

    fun clickOnLogin() {
        logOut.call()
    }

    fun deleteAutoLoginUser() {
        Observable.just(Constants.EMPTY_STRING)
            .observeOn(rxSchedulers.background())
            .map { userRepository.deleteAutoLoginUser() }
            .doOnError { Log.e("autologin delete ", it.message) }
            .subscribe()
            .disposeBy(compositeDisposable)
    }
}