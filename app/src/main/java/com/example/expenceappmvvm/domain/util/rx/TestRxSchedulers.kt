package com.example.expenceappmvvm.domain.util.rx

import com.example.expenceappmvvm.domain.util.rx.RxSchedulers
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

class TestRxSchedulers: RxSchedulers {
    override fun androidUI(): Scheduler {
        return Schedulers.trampoline()
    }

    override fun io(): Scheduler {
        return Schedulers.trampoline()
    }

    override fun computation(): Scheduler {
        return Schedulers.trampoline()
    }

    override fun network(): Scheduler {
        return Schedulers.trampoline()
    }

    override fun immediate(): Scheduler {
        return Schedulers.trampoline()
    }

    override fun background(): Scheduler {
        return Schedulers.trampoline()
    }

}
