package com.example.expenceappmvvm.domain.binding.models

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import com.example.expenceappmvvm.data.database.entities.Expense

class ExpensesObservableViewModel : BaseObservable() {
    @get:Bindable
    var userId: Long = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.userId)
        }

    @get:Bindable
    var amount: Double = 0.0
        set(value) {
            field = value
            notifyPropertyChanged(BR.amount)
        }

    @get:Bindable
    var expenseDate: Long = System.currentTimeMillis()
        set(value) {
            field = value
            notifyPropertyChanged(BR.expenseDate)
        }

    @get:Bindable
    var category: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.category)
        }

    @get:Bindable
    var details: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.details)
        }

    @get:Bindable
    var picturePath: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.picturePath)
        }

    @get:Bindable
    var showRemovePicture: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.showRemovePicture)
        }

    fun getExpenseEntity(): Expense {
        return Expense(
            0,
            this.userId,
            this.amount,
            this.expenseDate,
            this.category,
            this.details,
            this.picturePath
        )
    }
}


