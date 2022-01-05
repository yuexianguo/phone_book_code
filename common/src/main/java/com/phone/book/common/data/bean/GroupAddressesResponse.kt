package com.phone.book.common.data.bean

/**
 * description ：
 * author : Andy
 * email : 495311081@qq.com
 * date : 2020/7/23
 */
data class GroupAddressesResponse(
        val action: Int,
        val result: String,
        //group address list
        val addr: List<Int>?
)

