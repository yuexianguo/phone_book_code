package com.phone.book.common.data.bean

/**
 * description ：
 * author : Derik.Wu
 * email : Derik.Wu@waclighting.com.cn
 * date : 2020/7/23
 */
data class GroupModifyResponse(
        val action: Int,
        val result: String,
        //group address
        val addr: Int
)