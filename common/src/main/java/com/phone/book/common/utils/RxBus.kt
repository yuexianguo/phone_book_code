package com.phone.book.common.utils

import com.jakewharton.rxrelay2.PublishRelay
import com.jakewharton.rxrelay2.Relay
import io.reactivex.Observable

/**
 * description ：
 * author : Andy
 * email : 495311081@qq.com
 * date : 2021/1/4
 */
object RxBus {
    private var mBus: Relay<Any> = PublishRelay.create<Any>().toSerialized()

    fun post(o: Any) {
        mBus.accept(o)
    }

    fun <T> toObservable(eventType: Class<T>): Observable<T> {
        return mBus.ofType(eventType)
    }
}