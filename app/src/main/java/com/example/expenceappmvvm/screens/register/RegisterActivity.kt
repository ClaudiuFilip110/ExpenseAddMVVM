package com.example.expenceappmvvm.screens.register

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.expenceappmvvm.R
import com.example.expenceappmvvm.databinding.ActivityRegisterBinding
import com.example.expenceappmvvm.screens.main.MainActivity
import kotlinx.android.synthetic.main.activity_register.*
import org.koin.android.ext.android.get

class RegisterActivity : AppCompatActivity() {


    private val viewModel: RegisterViewModel = get()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        DataBindingUtil.setContentView<ActivityRegisterBinding>(this, R.layout.activity_register)
            .apply {
                lifecycleOwner = this@RegisterActivity
                registerViewModel = viewModel
            }

        viewModel.onCreate()
        observeShouldGoToMainPage()
        observeShouldGoToLogin()
        observeUsernameError()
        observeEmailError()
        observePasswordError()
    }

    private fun observeShouldGoToMainPage() {
        viewModel.shouldGoToMainPage.observe(this, Observer {
            MainActivity.start(this)
        })
    }

    private fun observeShouldGoToLogin() {
        viewModel.shouldGoToLogin.observe(this, Observer {
            //            LoginActivity.start(this)
        })
    }

    private fun observeUsernameError() {
        viewModel.userNameError.observe(this, Observer {
            textLayoutName.isErrorEnabled = it
            if (it)
                textLayoutName.error = getString(R.string.error_username)
        })
    }

    private fun observeEmailError() {
        viewModel.emailError.observe(this, Observer {
            textLayoutEmail.isErrorEnabled = it
            if (it)
                textLayoutEmail.error = getString(R.string.error_email)

        })
    }

    private fun observePasswordError() {
        viewModel.passwordError.observe(this, Observer {
            textLayoutPassword.isErrorEnabled = it
            if (it)
                textLayoutPassword.error = getString(R.string.error_password)
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.onDestroy()
    }
}
