package com.phone.book.common

import androidx.lifecycle.LifecycleOwner

/**
 * description ：
 * author : Andy
 * email : 495311081@qq.com
 * date : 2020/9/15
 */
interface BaseView : LifecycleOwner {
    fun toastNetError()

    fun toastGetDataFail()

    fun toastMsg(msg: String?)

    fun toastMsg(msg: String?, isShortTime: Boolean = true)

    fun showError()

    fun showLoading(label: String?, detailLabel: String?, cancellable: Boolean)

    fun dismissLoading()

    fun setRefreshing(enable: Boolean) {}
}