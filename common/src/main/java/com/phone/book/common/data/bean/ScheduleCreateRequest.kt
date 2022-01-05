package com.phone.book.common.data.bean

import com.phone.book.common.RequestActions

/**
 * description ：
 * author : Andy
 * email : 495311081@qq.com
 * date : 2020/12/16
 */
data class ScheduleCreateRequest(
        var name: String,
        var time: String,
        var automation: Int,
        //EXECUTE = 1, ENABLE = 2, DISABLE = 3
        var triggerAction: Int,
        var type: String,
        var enabled: Boolean = true
) {
    val action: Int = RequestActions.SCHEDULE_CREATE
}