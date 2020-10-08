package com.example.expenceappmvvm.domain.bindings

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.BindingMethod
import androidx.databinding.BindingMethods
import com.bumptech.glide.Glide
import com.google.android.material.textfield.TextInputEditText

@BindingAdapter(value = ["imageResource"])
fun setImageUrl(imageView: ImageView, imageResource: Int?) {
    Glide.with(imageView.context)
        .load(imageResource)
        .into(imageView)
}

