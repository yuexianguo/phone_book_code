package com.phone.book.common.data.source

import androidx.core.util.Consumer
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.phone.book.common.RequestActions
import com.phone.book.common.data.bean.*
import com.phone.book.common.exception.MqttNullResponseException
import com.phone.book.common.utils.LogUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

/**
 * description ：Use Flow
 * author : Andy
 * email : 495311081@qq.com
 * date : 2021/2/3
 */
class CStrutDataRepository : CStrutDataSource {

//    override fun getScheduleList(scope: CoroutineScope?, success: Consumer<in ScheduleListResponse>, fail: Consumer<in Throwable?>) {
////        val params = JsonObject()
////        params.addProperty("action", RequestActions.SCHEDULE_LIST)
////        LogUtil.d(BaseDataSource.TAG, "getScheduleList: request json=$params")
////
////        RequestRouter.getScheduleFlow(params) { flow ->
////            scope?.launch(context = Dispatchers.Main) {
////                flow.map { jsonObject ->
////                    LogUtil.d(BaseDataSource.TAG, "getScheduleList: response json=$jsonObject")
////                    val scheduleListResponse: ScheduleListResponse? = Gson().fromJson(jsonObject.toString(), ScheduleListResponse::class.java)
////                    scheduleListResponse
////                            ?: throw MqttNullResponseException("Schedule Mqtt Response is null")
////                }.onEach {
////                    ScheduleDbUtils.deleteAllSchedules(false)
////                }.catch { e ->
////                    fail.accept(e)
////                }.collect {
////                    success.accept(it)
////                }
////            }
////        }
//    }


}
