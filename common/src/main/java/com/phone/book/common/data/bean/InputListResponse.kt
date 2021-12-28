package com.phone.book.common.data.bean

/**
 * description ：
 * author : Derik.Wu
 * email : Derik.Wu@waclighting.com.cn
 * date : 2020/8/19
 */
data class InputListResponse(
        val action: Int,
        val result: String,
        val input: List<InputListItemBean>?
)