package com.phone.book.common.data.bean

/**
 * description ：
 * author : Andy
 * email : 495311081@qq.com
 * date : 2020/12/16
 */
data class ScheduleReadResponse(
        val action: Int,
        val result: String,
        val schedules: List<ScheduleDetail>?
)


data class ScheduleDetail(
        //schedule address
        val addr: Int,
        val name: String,
        val time: String,
        val automation: Int,
        val type: String,
        val enabled: Boolean,
        val triggerAction: Int
)