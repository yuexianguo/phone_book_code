package com.phone.book.common.data.source


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
