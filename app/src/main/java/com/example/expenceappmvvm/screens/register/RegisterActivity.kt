package com.example.expenceappmvvm.screens.register

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.expenceappmvvm.R
import com.example.expenceappmvvm.databinding.ActivityRegisterBinding
import com.example.expenceappmvvm.domain.util.InputTypesEnum
import kotlinx.android.synthetic.main.activity_register.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterActivity : AppCompatActivity() {

    private val viewModel: RegisterViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        DataBindingUtil.setContentView<ActivityRegisterBinding>(this, R.layout.activity_register)
            .apply {
                lifecycleOwner = this@RegisterActivity
                registerViewModel = viewModel
            }

        initTextInputListeners()
        initObservers()
    }

    private fun initTextInputListeners() {
        viewModel.inputTextChangeListener(textInputName, InputTypesEnum.NAME.name)
        viewModel.inputTextChangeListener(textInputEmail, InputTypesEnum.EMAIL.name)
        viewModel.inputTextChangeListener(textInputPassword, InputTypesEnum.PASSWORD.name)
    }

    private fun initObservers() {
        viewModel.userNameError.observe(this, Observer {
            textLayoutName.isErrorEnabled = it
            if (it) textLayoutName.error = getString(R.string.error_username)
        })

        viewModel.emailError.observe(this, Observer {
            textLayoutEmail.isErrorEnabled = it
            if (it) textLayoutEmail.error = getString(R.string.error_email)

        })

        viewModel.passwordError.observe(this, Observer {
            textLayoutPassword.isErrorEnabled = it
            if (it) textLayoutPassword.error = getString(R.string.error_password)
        })

        viewModel.shouldGoToLogin.observe(this, Observer {
//            LoginActivity.start(this)
        })
    }


    override fun onDestroy() {
        super.onDestroy()
        viewModel.onDestroy()
    }

    companion object {
        fun start(activity: Activity) {
            activity.startActivity(Intent(activity, RegisterActivity::class.java))
        }
    }
}