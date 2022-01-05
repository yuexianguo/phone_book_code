package com.phone.book.common.data.bean

/**
 * description ：
 * author : Andy
 * email : 495311081@qq.com
 * date : 2021/3/29
 */
data class DmxCreateResponseBean(
        val type: Int,
        val thing: Int,
        val mapping: List<Mapping>,
) : BaseResponse()