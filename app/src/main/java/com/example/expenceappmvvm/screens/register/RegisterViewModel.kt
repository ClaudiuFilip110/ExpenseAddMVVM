package com.example.expenceappmvvm.screens.register

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.expenceappmvvm.data.database.entities.User
import com.example.expenceappmvvm.data.database.repository.UserRepository
import com.example.expenceappmvvm.domain.util.*
import com.example.expenceappmvvm.domain.util.rx.AppRxSchedulers
import com.example.expenceappmvvm.domain.util.rx.disposeBy
import com.google.android.material.textfield.TextInputEditText
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable

class RegisterViewModel(
    private val compositeDisposable: CompositeDisposable,
    private val userRepository: UserRepository,
    private val rxSchedulers: AppRxSchedulers
) : ViewModel() {

    val user = MutableLiveData<User>().apply { value = User() }
    val userNameError = MutableLiveData<Boolean>()
    val emailError = MutableLiveData<Boolean>()
    val passwordError = MutableLiveData<Boolean>()
    val shouldGoToLogin = SingleLiveEvent<Boolean>()

    fun checkUserRegisterValidation(): Boolean {
        userNameError.value = ValidatorUtil.isValidName(user.value?.userName)
        emailError.value = ValidatorUtil.isValidEmail(user.value?.email)
        passwordError.value = ValidatorUtil.isValidPassword(user.value?.password)
        return userNameError.value == true && emailError.value == true
                && passwordError.value == true
    }

    fun deleteUsersFromDatabase() {
        userRepository.deleteUsers()
    }

    fun addUserToDatabase() {
        val userToInsert = user.value
        if (userToInsert != null) {
            userToInsert.password?.let { userToInsert.password = SecurityUtils.encrypt(it) }

            Observable.just(Constants.EMPTY_STRING)
                .observeOn(rxSchedulers.background())
                .map {
                    userRepository.insertUser(userToInsert)
                }
                .observeOn(rxSchedulers.androidUI())
                .doOnError {
                    Log.d("userRepository","Could not insert the user in the db")
                }
                .subscribe()
                .disposeBy(compositeDisposable)
        }
    }

    fun goToLoginClick() {
        shouldGoToLogin.call()
    }

    fun onDestroy() {
        compositeDisposable.clear()
    }
}