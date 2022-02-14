package com.phone.book.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import com.derry.serialportlibrary.T
import com.phone.book.R
import com.phone.book.bean.PhoneBookItem
import com.phone.book.common.BaseActivity
import com.phone.book.common.utils.LogUtil
import com.phone.book.fragment.DialingInitFragment
import com.phone.book.fragment.TAG_DIALING_INIT_FRAGMENT
import com.phone.book.fragment.TAG_DIALING_INIT_FRAGMENT


class DialingInitActivity : BaseActivity() {

    companion object {
        private const val EXTRA_KEY_TARGET_FRAGMENT = "target_fragment"
        private const val EXTRA_KEY_TARGET_PHONEITEM = "extra_key_target_phoneitem"
        private const val EXTRA_KEY_TARGET_PHONENUM = "extra_key_target_phonenum"
        private const val TAG = "DialingActivity"

        fun startDialingInitFragment(context: Context?,phoneNum: String) {
            if (context != null) {
                val intent = Intent(context, DialingInitActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK;
                intent.putExtra(EXTRA_KEY_TARGET_FRAGMENT, TAG_DIALING_INIT_FRAGMENT)
                intent.putExtra(EXTRA_KEY_TARGET_PHONENUM, phoneNum)
//                intent.putExtra(TAG_TARGET_DEPART_ITEM, targetDept)
                context.startActivity(intent)
            }
        }

    }

    override val layoutId: Int
        get() = R.layout.activity_edit_info_container

    override fun initViews() {
        LogUtil.d(T.TAG, "initViews")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            LogUtil.d(T.TAG, "onCreate")
            startTarget()
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        LogUtil.d(T.TAG, "onNewIntent")
        startTarget()
    }

    private fun showDialingPage(phoneNum: String) {
        val fragment = supportFragmentManager.findFragmentByTag(TAG_DIALING_INIT_FRAGMENT) as DialingInitFragment?
        fragment?.dismissAllowingStateLoss()
        DialingInitFragment.newInstance(phoneNum).show(supportFragmentManager, TAG_DIALING_INIT_FRAGMENT)
    }

    private fun startTarget() {
        var target = intent.getStringExtra(EXTRA_KEY_TARGET_FRAGMENT)
        val phoneNum = intent.getStringExtra(EXTRA_KEY_TARGET_PHONENUM)
        if (!TextUtils.isEmpty(target) && !phoneNum.isNullOrEmpty()) {
            showDialingPage(phoneNum)
        }
    }

}