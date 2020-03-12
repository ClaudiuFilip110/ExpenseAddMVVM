package com.example.expenceappmvvm.screens.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.expenceappmvvm.data.database.entities.User
import com.example.expenceappmvvm.domain.util.SingleLiveEvent
import com.example.expenceappmvvm.domain.util.ValidatorUtil
import io.reactivex.disposables.CompositeDisposable

class RegisterViewModel(private val compositeDisposable: CompositeDisposable) : ViewModel() {

    val user = MutableLiveData<User>().apply { value = User() }
    val shouldGoToMainPage = SingleLiveEvent<Any>()
    val shouldGoToLogin = SingleLiveEvent<Any>()

    val userNameError = MutableLiveData<Boolean>()
    val emailError = MutableLiveData<Boolean>()
    val passwordError = MutableLiveData<Boolean>()

    fun onCreate() {

    }

    fun onRegisterClick() {
        if (checkUserRegisterValidation()) {
            shouldGoToMainPage.call()
        }
    }

    private fun checkUserRegisterValidation(): Boolean {
        userNameError.value = !(ValidatorUtil.isValidName(user.value?.userName))
        emailError.value = !(ValidatorUtil.isValidEmail(user.value?.email))
        passwordError.value = !(ValidatorUtil.isValidPassword(user.value?.password))

        return !userNameError.value!! && !emailError.value!! && !passwordError.value!!
    }

    fun goToLoginClick() {
        shouldGoToLogin.call()
    }

    fun onDestroy() {
        compositeDisposable.clear()
    }
}