package com.example.expenceappmvvm.domain

import com.example.expenceappmvvm.R


enum class CategoryEnum {
    INCOME {
        override fun getStringValue() = "Income"
        override fun getCategoryIcon() = R.drawable.ic_hand
    },
    FOOD {
        override fun getStringValue() = "Food"
        override fun getCategoryIcon() = R.drawable.ic_groceries
    },
    CAR {
        override fun getStringValue() = "Car"
        override fun getCategoryIcon() = R.drawable.ic_car
    },
    CLOTHES {
        override fun getStringValue() = "Clothes"
        override fun getCategoryIcon() = R.drawable.ic_laundry
    },
    SAVINGS {
        override fun getStringValue() = "Savings"
        override fun getCategoryIcon() = R.drawable.ic_savings
    },
    HEALTH {
        override fun getStringValue() = "Health"
        override fun getCategoryIcon() = R.drawable.ic_doctor
    },
    BEAUTY {
        override fun getStringValue() = "Beauty"
        override fun getCategoryIcon() = R.drawable.ic_beauty
    },
    TRAVEL {
        override fun getStringValue() = "Travel"
        override fun getCategoryIcon() = R.drawable.ic_hand
    };

    abstract fun getStringValue(): String
    abstract fun getCategoryIcon(): Int
}