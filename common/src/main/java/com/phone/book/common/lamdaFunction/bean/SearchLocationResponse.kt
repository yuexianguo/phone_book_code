package com.phone.book.common.lamdaFunction.bean

/**
 * description ：
 * author : Andy
 * email : 495311081@qq.com
 * date : 2021/3/25
 */
data class SearchLocationResponse(
        val body: LocationBody,
        val result: Boolean
)

data class LocationBody(
        val address: String,
        val location: LocationData
)

data class LocationData(
        val lat: Double,
        val lng: Double
)