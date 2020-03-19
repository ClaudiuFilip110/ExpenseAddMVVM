package com.example.expenceappmvvm.domain.binding.converters

import androidx.databinding.InverseMethod

class BindingConverters {
    @InverseMethod("toDouble")
    fun toString(value: Double): String {
        return "" + value
    }

    fun toDouble(value: String): Double {
        if (value.isNullOrEmpty()) {
            return 0.0
        }
        return value.toDouble()
    }
}