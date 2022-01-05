package com.phone.book.common.lamdaFunction.bean

/**
 * description ：
 * author : Andy
 * email : 495311081@qq.com
 * date : 2021/3/26
 */
data class GetLocationResponse(
        val result: Boolean,
        val addrInfo: AddressInfo
)

data class AddressInfo(
        val address: String,
        val clientId: String,
        val location: LocationData?,
        val imageName: String? = null,
        val locationName: String? = null,
        val locationType: String? = null,
        val timeZone: String? = null
)