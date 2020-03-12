package com.example.expenceappmvvm.domain.util

import java.util.regex.Pattern

object ValidatorUtil {

    fun isValidEmail(email: String?): Boolean {
        if (email == null) return false
        return Pattern.matches("^(\\S+)@([a-z0-9-]+)(\\.)([a-z]{2,4})(\\.?)([a-z]{0,4})+$", email)
    }

    fun isValidPassword(password: String?): Boolean {
        if (password == null) return false
        return Pattern.matches("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[~`!@#$^&*()_+={}|:'<>,.?/%]).{6,20})", password)
    }

    fun isValidUsername(username: String?): Boolean {
        if (username == null) return false
        return Pattern.matches("^[a-z0-9_-]{3,15}\$", username)
    }

    fun isValidFullName(name: String?): Boolean {
        if (name == null) return false
        if (!name.trim().contains(" ")) return false
        return Pattern.matches("^[\\p{L} '-]{3,30}+\$", name.trim())
    }

    fun isValidName(name: String?): Boolean {
        if (name == null) return false
        return Pattern.matches("[A-Za-z][^0-9]{3,20}+\$", name.trim())
    }

    fun isValidPhoneNumber(phoneNumber: String?): Boolean {
        if (phoneNumber == null) return false
        return Pattern.matches("^[+]?[0-9]{10,13}\$", phoneNumber.trim())
    }

    fun isValidCountry(countryName: String?, countriesList: MutableList<String>): Boolean {
        if (countryName == null || countriesList.size == 0) return false
        return countriesList.find { it == countryName.trim()} != null
    }

    fun isValidState(stateName: String?): Boolean {
        if (stateName == null) return false
        return Pattern.matches("^[\\p{L} '-]{2,30}+\$", stateName.trim())
    }

    fun isValidActivationCode(activationCode: String?): Boolean {
        if (activationCode == null) return false
        return Pattern.matches("^[0-9]{1,15}\$", activationCode)
    }

}