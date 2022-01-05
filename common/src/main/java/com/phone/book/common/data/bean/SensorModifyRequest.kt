package com.phone.book.common.data.bean

import com.phone.book.common.RequestActions

/**
 * description ：
 * author : Andy
 * email : 495311081@qq.com
 * date : 2020/12/10
 */
data class SensorModifyRequest(
        val action: Int = RequestActions.SENSOR_MODIFY,
        val addr: Int,
        val name: String?,
        val targetLvl: Int?,
        val changeRate: Int?,
        val occState: Int?,
        val unoccScene: Int?,
        val disScene: Int?,
        val occToUnoccDelay: Int?,
        val UnoccToOccDelay: Int?
)