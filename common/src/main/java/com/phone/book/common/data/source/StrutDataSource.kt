package com.phone.book.common.data.source

import com.google.gson.JsonObject
import com.phone.book.common.CustomTypes.AxisType
import com.phone.book.common.data.bean.*
import com.phone.book.common.data.source.BaseDataSource.OTAHeaderModule
import com.phone.book.common.lamdaFunction.bean.GetLocationResponse
import com.phone.book.common.lamdaFunction.bean.LocationBody
import com.phone.book.common.lamdaFunction.bean.SearchLocationResponse
import com.phone.book.common.realmObject.AutomationShadow
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import java.io.File
import java.io.InputStream

/**
 * description ：
 * author : Andy
 * email : 495311081@qq.com
 * date : 2020/4/23
 */
interface StrutDataSource : BaseDataSource {
    fun getFixtureList(action: Int, success: Consumer<in FixtureAddressesResponse?>, error: Consumer<in Throwable?>): Disposable

}