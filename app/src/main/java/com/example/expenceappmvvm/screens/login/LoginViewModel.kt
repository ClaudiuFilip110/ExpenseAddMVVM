package com.example.expenceappmvvm.screens.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.expenceappmvvm.data.database.entities.User
import com.example.expenceappmvvm.domain.util.SingleLiveEvent
import com.example.expenceappmvvm.domain.util.ValidatorUtil
import io.reactivex.disposables.CompositeDisposable

class LoginViewModel(private val compositeDisposable: CompositeDisposable) : ViewModel() {
    val user = MutableLiveData<User>().apply { value = User() }
    val showError = MutableLiveData<Boolean>().apply { value = false }
    val email = "Email"
    val password = "Password"
    val goToMainActivity = SingleLiveEvent<Any>()

    private fun isUserValid(): Boolean {
        var isUserValid = false
        user.value?.run {
            isUserValid =
                ValidatorUtil.isValidEmail(email) && ValidatorUtil.isValidPassword(password)

        }

        return isUserValid
    }

    fun onLoginClick() {
        if (isUserValid()) {
            //TODO: Implement password match and navigation to main screen logic
            return
        }
        showError.value = true
    }

    fun clear() {
        compositeDisposable.clear()
    }
}