package com.phone.book.common.data.bean

/**
 * description ：
 * author : Andy
 * email : 495311081@qq.com
 * date : 2020/7/23
 */
data class FixtureReadResponse(
        val action: Int,
        val result: String,
        val name: String,
        val type: Int,
        val detail: FixtureDetail?,
        val state: FixtureState,
        val tune: FixtureTune?
)