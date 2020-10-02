package com.example.expenceappmvvm.screens.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.expenceappmvvm.R
import com.example.expenceappmvvm.databinding.ActivityLoginBinding
import com.example.expenceappmvvm.domain.util.InputTypesEnum
import com.example.expenceappmvvm.domain.util.ValidatorUtil
import com.example.expenceappmvvm.domain.util.extensions.toast
import com.example.expenceappmvvm.screens.main.MainActivity
import com.example.expenceappmvvm.screens.register.RegisterActivity
import com.example.expenceappmvvm.screens.splash.SplashActivity
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.coroutines.MainScope
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {
    private val viewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        DataBindingUtil.setContentView<ActivityLoginBinding>(this, R.layout.activity_login)
            .apply {
                lifecycleOwner = this@LoginActivity
                loginViewModel = viewModel
            }
        initObservers()
        initClickOnLogin()
    }

    private fun initClickOnLogin() {
        login_button.setOnClickListener {
            viewModel.user.value?.email = login_email_text.text.toString()
            viewModel.user.value?.password = login_password_text.text.toString()
            if (viewModel.userValidationIsCorrect()) {
                viewModel.shouldGoToMain.call()
            }
        }
    }

    private fun initObservers() {
        viewModel.emailError.observe(this, Observer {
            if (!it)
                login_email_layout.error = getString(R.string.error_email)
            else
                login_email_layout.isErrorEnabled = false
        })

        viewModel.passwordError.observe(this, Observer {
            if (!it)
                login_password_layout.error = getString(R.string.error_password)
            else
                login_password_layout.isErrorEnabled = false
        })

        viewModel.shouldGoToMain.observe(this, Observer {
            MainActivity.start(this)
        })

        viewModel.shouldGoToRegister.observe(this, Observer {
            RegisterActivity.start(this)
        })
    }

    companion object {
        fun start(activity: AppCompatActivity) {
            activity.startActivity(Intent(activity, LoginActivity::class.java))
        }
    }
}