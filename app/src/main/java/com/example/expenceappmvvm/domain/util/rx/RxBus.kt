package com.example.expenceappmvvm.domain.util.rx

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import java.util.*

class RxBus {

    private val bus = PublishSubject.create<Any>().toSerialized()

    fun send(o: Any) {
        bus.onNext(o)
    }

    fun toObserverable(): Observable<Any> {
        return bus
    }
}