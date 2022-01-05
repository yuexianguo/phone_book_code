package com.phone.book.common.data.bean

import com.phone.book.common.realmObject.PresetInfoObject
import io.realm.RealmList

/**
 * description ：
 * author : Andy
 * email : 495311081@qq.com
 * date : 2020/8/12
 */
data class FixtureTune(
        val dimmingCurve: Int,
        val offRate: Int,
        val onRate: Int,
        val presetInfo: RealmList<PresetInfoObject>?
)