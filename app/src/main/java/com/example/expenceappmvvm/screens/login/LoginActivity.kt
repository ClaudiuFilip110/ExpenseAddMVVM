package com.example.expenceappmvvm.screens.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.expenceappmvvm.R
import com.example.expenceappmvvm.databinding.ActivityLoginBinding
import com.example.expenceappmvvm.domain.util.InputTypesEnum
import com.example.expenceappmvvm.screens.main.MainActivity
import com.example.expenceappmvvm.screens.register.RegisterActivity
import kotlinx.android.synthetic.main.activity_login.*
import org.koin.android.ext.android.get

class LoginActivity : AppCompatActivity() {
    private val viewModel: LoginViewModel = get()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityLoginBinding>(this, R.layout.activity_login).apply {
            viewModel = this@LoginActivity.viewModel
            lifecycleOwner = this@LoginActivity
        }
        initObservers()
        initTextChangeListeners()
    }

    private fun initTextChangeListeners() {
        viewModel.inputTextChangeListener(loginEmailInput, InputTypesEnum.EMAIL.name)
        viewModel.inputTextChangeListener(loginPasswordInput, InputTypesEnum.EMAIL.name)
    }

    private fun initObservers() {
        viewModel.invalidPassword.observe(this, Observer {
            loginPassword.isErrorEnabled = it
            if (it) {
                loginPassword.error = getString(R.string.error_email)
            }
        })

        viewModel.invalidEmail.observe(this, Observer {
            loginEmail.isErrorEnabled = it
            if (it) {
                loginEmail.error = getString(R.string.error_email)
            }
        })

        viewModel.shouldGoToMain.observe(this, Observer {
            MainActivity.start(this)
        })

        viewModel.shouldGoToRegisterViewModel.observe(this, Observer {
            RegisterActivity.start(this)
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.clear()
    }

    companion object {
        fun starLogin(activity: Activity) {
            activity.startActivity(Intent(activity, LoginActivity::class.java))
            activity.finish()
        }
    }
}