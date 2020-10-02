package com.example.expenceappmvvm.domain.util

import android.util.Patterns
import java.util.regex.Pattern
import kotlin.coroutines.coroutineContext

object ValidatorUtil {

    fun isValidEmail(email: String?): Boolean {
        if (email == null) return false
        return Pattern.matches("^(\\S+)@([a-z0-9-]+)(\\.)([a-z]{2,4})(\\.?)([a-z]{0,4})+$", email)
    }

    fun isValidPassword(password: String?): Boolean {
        if (password == null) return false
        return Pattern.matches(
            "^(?=.*[0-9])(?=.*[a-z])(?=\\S+\$).{4,}\$",
            password
        )
    }

    fun isValidName(name: String?): Boolean {
        if (name == null) return false
        return Pattern.matches("[A-Za-z][^0-9]{3,20}+\$", name.trim())
    }
}
