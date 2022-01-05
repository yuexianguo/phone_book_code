package com.phone.book.common.data.bean

/**
 * description ：
 * author : Andy
 * email : 495311081@qq.com
 * date : 2020/7/23
 */
data class GroupReadResponse(
        val action: Int,
        val result: String,
        val name: String,
        //fixture address list in a group
        val fixture: List<Int>?
)