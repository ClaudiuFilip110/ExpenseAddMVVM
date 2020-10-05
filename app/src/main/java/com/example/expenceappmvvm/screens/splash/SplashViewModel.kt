package com.example.expenceappmvvm.screens.splash

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.expenceappmvvm.data.database.entities.AutoLoginUser
import com.example.expenceappmvvm.data.database.repository.UserRepository
import com.example.expenceappmvvm.data.prefs.PreferencesService
import com.example.expenceappmvvm.domain.util.Constants
import com.example.expenceappmvvm.domain.util.rx.AppRxSchedulers
import com.example.expenceappmvvm.domain.util.rx.disposeBy
import com.example.expenceappmvvm.screens.login.LoginActivity
import com.example.expenceappmvvm.screens.main.MainActivity
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import java.util.*
import kotlin.concurrent.timerTask

class SplashViewModel(
    private val compositeDisposable: CompositeDisposable,
    private val userRepository: UserRepository,
    private val rxSchedulers: AppRxSchedulers
) : ViewModel() {
    val autoLoginUser = MutableLiveData<AutoLoginUser>()
    val userExists = MutableLiveData<Boolean>()

    fun startMain(splashActivity: SplashActivity) {
        val timer = Timer()
        timer.schedule(timerTask { MainActivity.start(splashActivity) }, DELAY)
    }

    fun startLogin(splashActivity: SplashActivity) {
        val timer = Timer()
        timer.schedule(timerTask { LoginActivity.start(splashActivity) }, DELAY)
    }

    fun initAutoLoginUser() {
        Observable.just(Constants.EMPTY_STRING)
            .observeOn(rxSchedulers.background())
            .flatMap { userRepository.getAutoLogin().toObservable() }
            .observeOn(rxSchedulers.androidUI())
            .subscribe({
                autoLoginUser.value = it
                userExists.value = true
            }, {
                userExists.value = false
            })
            .disposeBy(compositeDisposable)
    }

    fun deleteUser() {
        userRepository.deleteAutoLoginUser()
    }

    companion object {
        const val DELAY = 2000L
    }
}