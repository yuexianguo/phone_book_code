package com.phone.book.common.listener

import android.view.View
import com.phone.book.common.SINGLE_CLICK_EVENT_TIME

/**
 * author : Andy
 * email : 495311081@qq.com
 * date : 2020/4/21
 */
abstract class OnSingleClickListener : View.OnClickListener {
    private var lastClickTime: Long = 0
    override fun onClick(v: View) {
        val currentClickTime = System.currentTimeMillis()
        if (currentClickTime - lastClickTime >= SINGLE_CLICK_EVENT_TIME) {
            onSingleClick(v)
        }
        lastClickTime = currentClickTime
    }

    abstract fun onSingleClick(v: View)
}