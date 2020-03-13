package com.example.expenceappmvvm.screens.login

import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.expenceappmvvm.data.database.entities.User
import com.example.expenceappmvvm.data.database.repository.UserRepository
import com.example.expenceappmvvm.data.prefs.PreferencesService
import com.example.expenceappmvvm.domain.util.InputTypesEnum
import com.example.expenceappmvvm.domain.util.SecurityUtils
import com.example.expenceappmvvm.domain.util.SingleLiveEvent
import com.example.expenceappmvvm.domain.util.ValidatorUtil
import com.example.expenceappmvvm.domain.util.rx.AppRxSchedulers
import com.example.expenceappmvvm.domain.util.rx.disposeBy
import com.google.android.material.textfield.TextInputEditText
import io.reactivex.disposables.CompositeDisposable

class LoginViewModel(
    private val userRepo: UserRepository,
    private val rxSchedulers: AppRxSchedulers,
    private val preferencesService: PreferencesService,
    private val compositeDisposable: CompositeDisposable
) : ViewModel() {
    val invalidEmail = MutableLiveData<Boolean>()
    val invalidPassword = MutableLiveData<Boolean>()
    val user = MutableLiveData<User>().apply { value = User() }
    val invalidCredentialsError = MutableLiveData<Boolean>().apply { value = false }
    val shouldGoToRegisterViewModel = SingleLiveEvent<Any>()
    val shouldGoToMain = SingleLiveEvent<Any>()

    private fun isUserInputValid(): Boolean {
        user.value?.run {
            invalidEmail.value = !ValidatorUtil.isValidEmail(email)
            invalidPassword.value = !ValidatorUtil.isValidPassword(password)
        }
        return !invalidEmail.value!! && !invalidPassword.value!!
    }

    private fun performLoginOperations() {
        if (isUserInputValid()) {
            userRepo.getUserByEmail(user.value!!.email)
                .observeOn(rxSchedulers.androidUI())
                .subscribe {
                    val passwordMath =
                        it.password.equals(SecurityUtils.encrypt(user.value!!.password!!), true)
                    if (!passwordMath) {
                        invalidCredentialsError.value = true
                    } else {
                        preferencesService.setValue(PreferencesService.USER_ID, it.id)
                        shouldGoToMain.call()
                    }
                }
                .disposeBy(compositeDisposable)
        }
    }

    fun onLoginClick() {
        performLoginOperations()
    }

    fun onRegisterClick() {
        shouldGoToRegisterViewModel.call()
    }

    fun clear() {
        compositeDisposable.clear()
    }

    fun inputTextChangeListener(textInput: TextInputEditText, type: String) {
        textInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                when (type) {
                    InputTypesEnum.EMAIL.name -> invalidEmail.value = false
                    InputTypesEnum.PASSWORD.name -> invalidPassword.value = false
                    else -> {
                    }
                }
                invalidCredentialsError.value = false
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
    }
}