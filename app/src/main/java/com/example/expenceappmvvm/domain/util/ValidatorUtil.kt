package com.example.expenceappmvvm.domain.util

import java.util.regex.Pattern

object ValidatorUtil {

    fun isValidEmail(email: String?): Boolean {
        if (email == null) return false
        return Pattern.matches("^(\\S+)@([a-z0-9-]+)(\\.)([a-z]{2,4})(\\.?)([a-z]{0,4})+$", email)
    }

    fun isValidPassword(password: String?): Boolean {
        if (password == null) return false
        return Pattern.matches(
            "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[~`!@#$^&*()_+={}|:'<>,.?/%]).{6,20})",
            password
        )
    }

    fun isValidName(name: String?): Boolean {
        if (name == null) return false
        return Pattern.matches("[A-Za-z][^0-9]{3,20}+\$", name.trim())
    }
}
