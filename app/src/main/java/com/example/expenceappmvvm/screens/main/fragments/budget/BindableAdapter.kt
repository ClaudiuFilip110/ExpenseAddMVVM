package com.example.expenceappmvvm.screens.main.fragments.budget

import android.graphics.Typeface
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.expenceappmvvm.R


@BindingAdapter("balanceValue", "applyTextColor", requireAll = false)
fun customColorBalanceAdapter(
    textView: TextView,
    balanceValue: String,
    applyTextColor: Boolean = false
) {
    if (applyTextColor) {
        textView.setTypeface(textView.typeface, Typeface.BOLD)
        textView.setTextColor(
            if (balanceValue.toDouble() > 0) {
                textView.resources.getColor(R.color.colorPrimary)
            } else {
                textView.resources.getColor(R.color.red)
            }
        )
    }
}

@BindingAdapter("balanceValue", "applyTintColor", requireAll = false)
fun customBackgroundAdapter(
    linearLayout: LinearLayout,
    balanceValue: String,
    applyTintColor: Boolean = false
) {
    if (applyTintColor) {
        linearLayout.backgroundTintList = if (balanceValue.toDouble() > 0) {
            linearLayout.resources.getColorStateList(R.color.green_transparent)
        } else {
            linearLayout.resources.getColorStateList(R.color.red_transparent)
        }
    }
}