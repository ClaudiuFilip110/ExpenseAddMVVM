package com.example.expenceappmvvm.domain.util

import java.math.BigInteger
import java.security.MessageDigest

object SecurityUtils {

    private const val ALGORITHM = "MD5"

    fun encrypt(password: String): String {
        var hashedPass = ""
        val salt = "ExpenseManager"
        var actualPassword = password + salt;
        val messageDigest = MessageDigest.getInstance(ALGORITHM)
        messageDigest.update(actualPassword.toByteArray(), 0, actualPassword.length)
        actualPassword = BigInteger(1, messageDigest.digest()).toString()
        while (actualPassword.length < 32) {
            actualPassword += 0
        }
        hashedPass = actualPassword

        return hashedPass
    }
}