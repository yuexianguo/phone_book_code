package com.phone.book.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.view.WindowManager
import com.phone.book.R
import com.phone.book.common.BaseActivity
import com.phone.book.common.listener.OnSingleClickListener
import com.phone.book.common.utils.PrefUtils
import com.phone.book.common.utils.PrefUtils.readLong
import com.phone.book.common.utils.PrefUtils.writeLong
import kotlinx.android.synthetic.main.activity_test_awake.*

class TestAwakeActivity : BaseActivity() {
    override val layoutId: Int
        get() = R.layout.activity_test_awake

    override fun initViews() {
        initToolbar()
        window.addFlags(
            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON //保持屏幕常亮
                    or WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED //在锁屏情况下也可以显示屏幕
                    or WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
        ) //启动Activity的时候点亮屏幕

    }



    private fun initToolbar() {
        hideLogo()
        setToolbarTitle("Test Awake", true)
        et_info_awake_time.edit_info_box_value?.inputType = InputType.TYPE_CLASS_NUMBER
        bt_awake_time_save.setOnClickListener(object :OnSingleClickListener(){
            override fun onSingleClick(v: View) {
                if (et_info_awake_time.desc.isEmpty()) {
                    toastMsg("输入不能为空。")
                    return
                }
                writeLong("awakeTime",et_info_awake_time.desc.toLong())
                writeLong("startServiceTime",System.currentTimeMillis())
            }
        })
    }

}