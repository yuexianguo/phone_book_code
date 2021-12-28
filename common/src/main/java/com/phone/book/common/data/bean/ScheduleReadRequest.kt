package com.phone.book.common.data.bean

import com.phone.book.common.RequestActions

/**
 * description ：
 * author : Derik.Wu
 * email : Derik.Wu@waclighting.com.cn
 * date : 2020/12/16
 */
data class ScheduleReadRequest(
        //four ways to Read Schedule, select one only.
        var addrs: List<Int>? = null,
        var time: String? = null,
        var startAddr: Int = 0
) {
    val action: Int = RequestActions.SCHEDULE_READ
}