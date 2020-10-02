package com.example.expenceappmvvm.screens.login

import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.expenceappmvvm.data.database.entities.User
import com.example.expenceappmvvm.data.database.repository.UserRepository
import com.example.expenceappmvvm.domain.util.InputTypesEnum
import com.example.expenceappmvvm.domain.util.SingleLiveEvent
import com.example.expenceappmvvm.domain.util.ValidatorUtil
import com.example.expenceappmvvm.screens.register.RegisterViewModel
import com.google.android.material.textfield.TextInputEditText
import io.reactivex.disposables.CompositeDisposable
import org.koin.androidx.viewmodel.ext.android.viewModel


class LoginViewModel() : ViewModel() {

    val user = MutableLiveData<User>().apply { value = User() }
    val shouldGoToMain = SingleLiveEvent<Any>()
    val shouldGoToRegister = SingleLiveEvent<Any>()
    val emailError = MutableLiveData<Boolean>()
    val passwordError = MutableLiveData<Boolean>()

    fun userValidationIsCorrect(): Boolean {
        emailError.value = ValidatorUtil.isValidEmail(user.value?.email)
        passwordError.value = ValidatorUtil.isValidPassword(user.value?.password)
        return emailError.value!! && passwordError.value!!
    }

    fun clickOnRegister() {
        shouldGoToRegister.call()
    }
}