package com.phone.book.common.data.bean
/**
 * description :
 * author : Andy.Guo
 * email : Andy.Guo@waclightiong.com.cn
 * data : 2020/12/23
 */
data class AutoSingleStateBean(
        //fixture addr
        var addr: Int?,
        var state: SingleLightState? = SingleLightState(0,false,0)
)