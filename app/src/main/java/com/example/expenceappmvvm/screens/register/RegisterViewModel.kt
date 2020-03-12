package com.example.expenceappmvvm.screens.register

import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.expenceappmvvm.data.database.entities.User
import com.example.expenceappmvvm.data.database.repository.UserRepository
import com.example.expenceappmvvm.domain.util.InputTypesEnum
import com.example.expenceappmvvm.domain.util.SecurityUtils
import com.example.expenceappmvvm.domain.util.SingleLiveEvent
import com.example.expenceappmvvm.domain.util.ValidatorUtil
import com.google.android.material.textfield.TextInputEditText
import io.reactivex.disposables.CompositeDisposable

class RegisterViewModel(
    private val compositeDisposable: CompositeDisposable,
    private val userRepository: UserRepository
) : ViewModel() {

    val user = MutableLiveData<User>().apply { value = User() }
    val shouldGoToLogin = SingleLiveEvent<Any>()
    val userNameError = MutableLiveData<Boolean>()
    val emailError = MutableLiveData<Boolean>()
    val passwordError = MutableLiveData<Boolean>()

    private fun checkUserRegisterValidation(): Boolean {
        userNameError.value = !(ValidatorUtil.isValidName(user.value?.userName))
        emailError.value = !(ValidatorUtil.isValidEmail(user.value?.email))
        passwordError.value = !(ValidatorUtil.isValidPassword(user.value?.password))

        return !userNameError.value!! && !emailError.value!! && !passwordError.value!!
    }

    private fun addUserToDatabase() {
        user.value?.let {
            it.password = SecurityUtils.encrypt(it.password!!)
            userRepository.insertUser(it)
        }
    }

    fun onRegisterClick() {
        if (checkUserRegisterValidation()) {
            addUserToDatabase()
            goToLoginClick()
        }
    }

    fun inputTextChangeListener(
        textInput: TextInputEditText,
        type: String
    ) {
        textInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                when (type) {
                    InputTypesEnum.NAME.name -> userNameError.value = false
                    InputTypesEnum.EMAIL.name -> emailError.value = false
                    InputTypesEnum.PASSWORD.name -> passwordError.value = false
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    fun goToLoginClick() {
        shouldGoToLogin.call()
    }

    fun onDestroy() {
        compositeDisposable.clear()
    }
}