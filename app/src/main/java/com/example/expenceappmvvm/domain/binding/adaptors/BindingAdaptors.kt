package com.example.expenceappmvvm.domain.binding.adaptors

import android.widget.EditText
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.expenceappmvvm.domain.util.Constants
import com.example.expenceappmvvm.domain.util.DateAndTimeUtils
import java.io.File

@BindingAdapter(value = ["dateFormatterAdapter", "showErrorText", "errorText"], requireAll = false)
fun dateFormatterAdapter(
    editText: EditText,
    dateMillis: Long,
    showErrorText: Boolean,
    errorText: String
) {
    editText.setText(
        DateAndTimeUtils.getDateAsString(
            dateMillis,
            true,
            Constants.APP_DATE_FORMAT,
            Constants.APP_TIME_FORMAT
        )
    )
    if (showErrorText) {
        editText.error = errorText
    } else {
        editText.error = null
    }
}

@BindingAdapter(value = ["amountErrorHandling", "errorText"], requireAll = false)
fun amountErrorHandling(editText: EditText, showErrorText: Boolean, errorText: String) {
    if (showErrorText) {
        editText.error = errorText
    } else {
        editText.error = null
    }
}

@BindingAdapter(value = ["imagePath", "imageResource"], requireAll = false)
fun loadImageUrl(imageView: ImageView, url: String?, imageResource: Int?) {
    if (url.isNullOrEmpty()) {
        Glide.with(imageView.context)
            .load(imageResource)
            .into(imageView)
    } else {
        Glide.with(imageView.context)
            .load(File(url))
            .into(imageView)
    }
}
