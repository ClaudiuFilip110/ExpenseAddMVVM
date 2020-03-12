package com.example.expenceappmvvm.screens.main

import android.os.Bundle
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.expenceappmvvm.domain.util.SingleLiveEvent
import io.reactivex.disposables.CompositeDisposable

class MainViewModel(private val compositeDisposable: CompositeDisposable) :ViewModel() {
    var userNAme="xvdf"

    val backBtn = MutableLiveData<Any>() //SingleLiveEvent

    fun onCreate(arguments: Bundle?) {
      //  initViewModel(arguments, filtersApplied)
    }

    fun onBackBtnClicked(view: View) {
        backBtn.value // backBtn.call()
    }

    fun onDestroy() {
        compositeDisposable.clear()
    }
}
