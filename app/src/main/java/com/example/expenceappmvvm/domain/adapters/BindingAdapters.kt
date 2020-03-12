package com.example.expenceappmvvm.domain.adapters

import android.text.Editable
import android.text.TextWatcher
import androidx.databinding.BindingAdapter
import com.example.expenceappmvvm.screens.login.LoginViewModel
import com.google.android.material.textfield.TextInputEditText

@BindingAdapter("android:inputTextValidation", "android:inputValidationType")
fun inputTextValidation(
    textInput: TextInputEditText,
    viewModel: LoginViewModel,
    type: String
) {
    textInput.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            type
            viewModel.showError.value = false
            s.toString()
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            s.toString()
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            s.toString()
        }
    })
}

