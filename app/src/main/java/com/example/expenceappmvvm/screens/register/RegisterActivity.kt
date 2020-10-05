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
import com.example.expenceappmvvm.domain.util.extensions.toast
import com.example.expenceappmvvm.screens.login.LoginActivity
import kotlinx.android.synthetic.main.activity_login.*
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
        initLoginListener()
        initObservers()
    }

    private fun initLoginListener() {
        registerButton.setOnClickListener {
            viewModel.user.value?.email = textInputEmail.text.toString()
            viewModel.user.value?.userName = textInputName.text.toString()
            viewModel.user.value?.password = textInputPassword.text.toString()

            //user this line of code when you want to clean the database users
//            viewModel.deleteUsersFromDatabase()
            if (viewModel.checkUserRegisterValidation()) {
                viewModel.addUserToDatabase()
                viewModel.goToLoginClick()
            }
        }
    }

    private fun initObservers() {
        viewModel.shouldGoToLogin.observe(this, Observer {
            LoginActivity.start(this)
        })

        viewModel.userNameError.observe(this, Observer {
            if (!it)
                textInputName.error = getString(R.string.error_username)
            else {
                textInputName.error = null
                textLayoutName.isErrorEnabled = false
            }
        })

        viewModel.emailError.observe(this, Observer {
            if (!it)
                textInputEmail.error = getString(R.string.error_email)
            else {
                textInputEmail.error = null
                textLayoutEmail.isErrorEnabled = false
            }
        })

        viewModel.passwordError.observe(this, Observer {
            if (!it)
                textInputPassword.error = getString(R.string.error_password)
            else {
                textInputPassword.error = null
                textLayoutPassword.isErrorEnabled = false
            }
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