package com.example.expenceappmvvm.screens.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.expenceappmvvm.data.database.dao.AutologinUserDao
import com.example.expenceappmvvm.data.database.entities.AutoLoginUser
import com.example.expenceappmvvm.data.database.entities.User
import com.example.expenceappmvvm.data.database.repository.UserRepository
import com.example.expenceappmvvm.domain.util.Constants
import com.example.expenceappmvvm.domain.util.SecurityUtils
import com.example.expenceappmvvm.domain.util.SingleLiveEvent
import com.example.expenceappmvvm.domain.util.ValidatorUtil
import com.example.expenceappmvvm.domain.util.rx.AppRxSchedulers
import com.example.expenceappmvvm.domain.util.rx.disposeBy
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable


class LoginViewModel(
    private val compositeDisposable: CompositeDisposable,
    private val userRepository: UserRepository,
    private val rxSchedulers: AppRxSchedulers
) : ViewModel() {

    val user = MutableLiveData<User>().apply { value = User() }
    val shouldGoToRegister = SingleLiveEvent<Any>()
    val emailError = MutableLiveData<Boolean>()
    val passwordError = MutableLiveData<Boolean>()
    val userCredentialsAreValid = MutableLiveData<Boolean>()
    val passwordsMatch = MutableLiveData<Boolean>()

    fun userValidationIsCorrect(): Boolean {
        emailError.value = ValidatorUtil.isValidEmail(user.value?.email)
        passwordError.value = ValidatorUtil.isValidPassword(user.value?.password)
        //TODO: don't use !!
        return emailError.value!! && passwordError.value!!
    }

    fun clickOnRegister() {
        shouldGoToRegister.call()
    }

    fun getUserFromDatabase(email: String) {
        Observable.just(Constants.EMPTY_STRING)
            .observeOn(rxSchedulers.background())
            .flatMap {
                userRepository.getUserByEmail(email).toObservable()
            }
            //save to autologin db
            .observeOn(rxSchedulers.androidUI())
            .doOnNext {
                checkIfPasswordsMatch(it)
                if (passwordsMatch.value == true) {
                    var autoLogin = AutoLoginUser()
                    autoLogin.email = it.email
                    autoLogin.password = it.password.toString()
                    autoLogin.name = it.userName.toString()
                    userRepository.insertAutoLogin(autoLogin)
                }
            }
            .observeOn(rxSchedulers.androidUI())
            .doOnError {
                Log.d("AutoLogin", "autoLogin failed" + it.message)
            }
            .observeOn(rxSchedulers.androidUI())
            //save to ViewModel user
            .doOnNext {
                checkIfPasswordsMatch(it)
                if (passwordsMatch.value == true) {
                    saveToLocalUser(it)
                }
            }
            .doOnError {
                userCredentialsAreValid.value = false
            }
            .subscribe({
            }, {
                userCredentialsAreValid.value = false
            })
            .disposeBy(compositeDisposable)
    }

    private fun saveUserForAutologin(it: User?) {
        if (it != null) {
            val autoLogin = (it as AutoLoginUser)
            Observable.just(Constants.EMPTY_STRING)
                .observeOn(rxSchedulers.background())
                .map { userRepository.insertAutoLogin(autoLogin) }
                .observeOn(rxSchedulers.androidUI())
                .doOnError {
                    Log.d("AutologinUser", "error before subscribe + ${it.message}")
                }
                .subscribe({
                    Log.d("AutologinUser", "insertion succesful")
                }, {
                    Log.d("AutologinUser", "insertion error -> " + it.message)
                })
                .disposeBy(compositeDisposable)
        }
    }

    private fun checkIfPasswordsMatch(it: User?) {
        if (it == null) {
            passwordsMatch.value = false
        }
        val encrypedPass = user.value?.password?.let { it1 -> SecurityUtils.encrypt(it1) }
        if (it != null) {
            passwordsMatch.value = encrypedPass == it.password
        }
    }

    private fun saveToLocalUser(it: User?) {
        if (it == null) {
            userCredentialsAreValid.value = false
            return
        }
        user.value = it
        userCredentialsAreValid.value = true
    }
}