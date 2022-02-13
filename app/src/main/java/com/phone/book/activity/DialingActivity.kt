package com.phone.book.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import com.derry.serialportlibrary.T
import com.phone.book.R
import com.phone.book.bean.PhoneBookItem
import com.phone.book.bean.PhoneDepartItem
import com.phone.book.common.BaseActivity
import com.phone.book.common.utils.LogUtil
import com.phone.book.fragment.DialingFragment
import com.phone.book.fragment.TAG_DIALING_FRAGMENT
import com.phone.book.fragment.info.PhoneInfoFragment

import com.phone.book.fragment.info.TAG_PHONE_INFO_FRAGMENT
import com.phone.book.fragment.info.TAG_TARGET_PHONE_ITEM


class DialingActivity : BaseActivity() {

    companion object {
        private const val EXTRA_KEY_TARGET_FRAGMENT = "target_fragment"
        private const val TAG = "DialingActivity"

        fun startDialingFragment(context: Context?, phoneNum: String?) {
            if (context != null) {
                val intent = Intent(context, DialingActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK;
                intent.putExtra(EXTRA_KEY_TARGET_FRAGMENT, TAG_DIALING_FRAGMENT)
//                intent.putExtra(TAG_TARGET_DEPART_ITEM, targetDept)
                context.startActivity(intent)
            }
        }

    }

    override val layoutId: Int
        get() = R.layout.activity_edit_info_container

    override fun initViews() {
        LogUtil.d(T.TAG,"initViews")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            LogUtil.d(T.TAG,"onCreate")
            startTarget()
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        LogUtil.d(T.TAG,"onNewIntent")
        showDialingPage()
    }

    private fun showDialingPage() {
        val fragment = supportFragmentManager.findFragmentByTag(TAG_DIALING_FRAGMENT) as DialingFragment?
        fragment?.dismissAllowingStateLoss()
        DialingFragment.newInstance("").show(supportFragmentManager, TAG_DIALING_FRAGMENT)
    }

    private fun startTarget() {
        var target = intent.getStringExtra(EXTRA_KEY_TARGET_FRAGMENT)
        if (!TextUtils.isEmpty(target)) {
            showDialingPage()
        }
    }


}