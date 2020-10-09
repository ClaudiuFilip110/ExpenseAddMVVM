package com.example.expenceappmvvm.screens.main.expenses.viewpager

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.expenceappmvvm.data.database.entities.Action
import com.example.expenceappmvvm.data.database.repository.UserRepository
import com.example.expenceappmvvm.domain.util.Constants
import com.example.expenceappmvvm.domain.util.DateAndTimeUtils
import com.example.expenceappmvvm.domain.util.rx.AppRxSchedulers
import com.example.expenceappmvvm.domain.util.rx.disposeBy
import com.example.expenceappmvvm.screens.main.expenses.PieChart
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import org.threeten.bp.LocalDateTime
import timber.log.Timber
import java.util.*
import kotlin.collections.ArrayList

class ViewPagerViewModel(
    private val compositeDisposable: CompositeDisposable,
    private val userRepository: UserRepository,
    private val rxSchedulers: AppRxSchedulers
) : ViewModel() {
    var title: String? = String()
    var actions = MutableLiveData<List<Action>>()

    init {
        getActionsList()
    }
    fun getActionsList() {
        Observable.just(Constants.EMPTY_STRING)
            .observeOn(rxSchedulers.background())
            .flatMap { userRepository.getActions().toObservable() }
            .observeOn(rxSchedulers.androidUI())
            .subscribe( {
                actions.value = it
            }, {
            }).disposeBy(compositeDisposable)
    }

    fun adapterList(): ArrayList<Action> {
        val finalArray = ArrayList<Action>()
        if (title.isNullOrEmpty()){
            Timber.d("TITLE IS NULL")
            return ArrayList()
        }
        if (actions.value?.size == 0) {
            Timber.d("LIST IS NULL")
            return ArrayList()
        }
        when (title.toString()) {
            Constants.WEEK -> {
                weekCase(actions.value as ArrayList<Action>, finalArray)
            }
            Constants.MONTH -> {
                monthCase(actions.value as ArrayList<Action>, finalArray)
            }
            Constants.YEAR -> {
                yearCase(actions.value as ArrayList<Action>, finalArray)
            }
        }
        return finalArray
    }

    private fun yearCase(actions: ArrayList<Action>, finalArray: ArrayList<Action>) {
        for (action in actions) {
            val date = DateAndTimeUtils.convertDate(action.date)
            val now = LocalDateTime.now()
            if (date.plusYears(1).year == now.year) {
                if (date.month > now.month) {
                    finalArray.add(action)
                } else if (date.month == now.month) {
                    if (date.dayOfMonth >= now.dayOfMonth) {
                        finalArray.add(action)
                    }
                }
            } else if (date.year == now.year) {
                if (date.month < now.month) {
                    finalArray.add(action)
                } else {
                    if (date.month == now.month) {
                        if (date.dayOfMonth <= now.dayOfMonth) {
                            finalArray.add(action)
                        }
                    }
                }
            }
        }
    }

    private fun monthCase(actions: ArrayList<Action>, finalArray: ArrayList<Action>) {
        for (action in actions) {
            val date = DateAndTimeUtils.convertDate(action.date)
            val now = LocalDateTime.now()
            if (date.year == now.year) {
                if (date.plusMonths(1).month == now.month && date.dayOfMonth >= now.dayOfMonth ||
                    date.month == now.month && date.dayOfMonth <= now.dayOfMonth
                ) {
                    finalArray.add(action)
                }
            }
        }
    }

    private fun weekCase(actions: ArrayList<Action>, finalArray: ArrayList<Action>) {
        for (action in actions) {
            val date = DateAndTimeUtils.convertDate(action.date)
            val now = LocalDateTime.now()

            if (date.year == now.year) {
                if (date.month == now.month) {
                    if (date.dayOfMonth == now.dayOfMonth) {
                        if (date.dayOfMonth <= now.dayOfMonth) {
                            if (date.dayOfMonth + 7 > now.dayOfMonth) {
                                Timber.d(
                                    "now.minusDays(7).dayOfMonth is %s",
                                    now.minusDays(7).dayOfMonth
                                )
                                finalArray.add(action)
                            }
                        }
                    }
                } else if (date.plusMonths(1).month == now.month) {
                    if (date.dayOfMonth > now.minusDays(7).dayOfMonth) {
                        Timber.d("now.minusDays(7).dayOfMonth is %s", now.minusDays(7).dayOfMonth)
                        finalArray.add(action)
                    }
                }
            }
        }
    }
}