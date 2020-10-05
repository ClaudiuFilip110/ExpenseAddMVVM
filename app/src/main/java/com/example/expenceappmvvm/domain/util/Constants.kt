package com.example.expenceappmvvm.domain.util


object Constants {
    const val EMPTY_STRING = ""
    const val EMAIL = "Email"
    const val PASSWORD = "Password"
    const val BALANCE_DEFAULT_AMOUNT = 0.0
    const val APP_DATE_FORMAT = "yyyy-MM-dd"
    const val APP_TIME_FORMAT = "HH:mm"
    const val APP_MONTH_FORMAT = "MMM"
    const val TAKE_PHOTO = "Take photo"
    const val GALLERY = "Gallery"
    const val DATA = "data"
    const val NONE = "None"
    const val REQUEST_TAKE_PHOTO = 201
    const val REQUEST_CHOOSE_PHOTO = 202
    val options = arrayOf<CharSequence>(
        TAKE_PHOTO,
        GALLERY,
        NONE
    )
}