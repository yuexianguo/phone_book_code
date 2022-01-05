package com.phone.book.common

import androidx.lifecycle.LifecycleObserver

/**
 * description ：
 * author : Andy
 * email : 495311081@qq.com
 * date : 2020/9/15
 */
interface CBasePresenter<V> : LifecycleObserver {

    fun start()

    fun cancel()

    fun attach(v: V?)

    fun detach()

    fun destroy()
}