package com.phone.book.common.data.bean

/**
 * description ：
 * author : Andy
 * email : 495311081@qq.com
 * date : 2021/3/29
 */
data class DmxReadResponse(
        val addr: Int,
        val mapping: List<Mapping>,
        val thing: Int,
        val type: Int
) : BaseResponse()

data class Mapping(
        val channel: Int,
        val `field`: String,
        val input: String
)