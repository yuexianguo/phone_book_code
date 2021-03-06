package com.phone.book.common.utils

import android.content.pm.PackageManager
import android.content.res.Configuration
import android.os.Build
import com.phone.book.common.BaseApplication.Companion.context

/**
 * description ：
 * author : Andy
 * email : 495311081@qq.com
 * date : 2020/5/18
 */
object AppUtils {

    @JvmStatic
    val isTablet: Boolean
        get() {
            return isNotLargePhone && context.resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK >= Configuration.SCREENLAYOUT_SIZE_LARGE
        }

    private val isNotLargePhone: Boolean
        get() {
            val model = Build.MODEL
            val brand = Build.BRAND
            if (model.length >= 5) {
                val substring = model.substring(0, 5)
                return !"Pixel".equals(substring, ignoreCase = true) || !"google".equals(brand, ignoreCase = true)
            }
            return true
        }

    @JvmStatic
    fun getAppVersionName(): String? {
        var verName = ""
        try {
            verName = context.packageManager.getPackageInfo(context.packageName, 0).versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return verName
    }
}