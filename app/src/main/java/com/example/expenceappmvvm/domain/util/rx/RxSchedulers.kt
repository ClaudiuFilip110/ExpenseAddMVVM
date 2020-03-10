package com.example.expenceappmvvm.domain.util.rx

import io.reactivex.Scheduler

interface RxSchedulers {

    fun androidUI(): Scheduler

    fun io(): Scheduler

    fun computation(): Scheduler

    fun network(): Scheduler

    fun immediate(): Scheduler

    fun background(): Scheduler
}
