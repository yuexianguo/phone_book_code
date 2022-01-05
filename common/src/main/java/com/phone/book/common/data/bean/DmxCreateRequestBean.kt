package com.phone.book.common.data.bean

import com.phone.book.common.RequestActions

/**
 * description ：
 * author : Andy
 * email : 495311081@qq.com
 * date : 2021/3/29
 */
data class DmxCreateRequestBean(
        val mapping: List<Mapping>,
        val thing: Int,
        val type: Int
) {
    val action: Int = RequestActions.DMX_CREATE
}