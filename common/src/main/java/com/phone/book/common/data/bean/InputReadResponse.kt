package com.phone.book.common.data.bean

/**
 * description ：
 * author : Andy
 * email : 495311081@qq.com
 * date : 2020/8/19
 */
data class InputReadResponse(
        val action: Int,
        val result: String,
        val config: ArrayList<InputConfig>
)