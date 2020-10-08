package com.example.expenceappmvvm.screens.test

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.expenceappmvvm.data.database.repository.UserRepository
import com.example.expenceappmvvm.domain.util.rx.AppRxSchedulers
import io.reactivex.disposables.CompositeDisposable


class TestViewModel(
    private val compositeDisposable: CompositeDisposable,
    private val userRepository: UserRepository,
    private val rxSchedulers: AppRxSchedulers
) : ViewModel() {
    var userName = MutableLiveData<String>()
    var password = MutableLiveData<String>()
}