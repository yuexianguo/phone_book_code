package com.phone.book

import com.phone.book.common.BaseApplication
import com.phone.book.manager.PhoneInfoManager

class MyApplication : BaseApplication() {

    override fun onCreate() {
        super.onCreate()
        PhoneInfoManager.instance.init(context)
    }
}