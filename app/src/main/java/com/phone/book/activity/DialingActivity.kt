package com.phone.book.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.WindowManager
import com.derry.serialportlibrary.T
import com.phone.book.R
import com.phone.book.bean.PhoneBookItem
import com.phone.book.common.BaseActivity
import com.phone.book.common.utils.LogUtil
import com.phone.book.fragment.DialingFragment
import com.phone.book.fragment.TAG_DIALING_FRAGMENT
import java.io.Serializable


class DialingActivity : BaseActivity() {

    companion object {
        private const val EXTRA_KEY_TARGET_FRAGMENT = "target_fragment"
        private const val EXTRA_KEY_TARGET_PHONEITEM = "extra_key_target_phoneitem"
        private const val TAG = "DialingActivity"

        fun startDialingFragment(context: Context?, phoneItem: PhoneBookItem?) {
            if (context != null) {
                val intent = Intent(context, DialingActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK;
                intent.putExtra(EXTRA_KEY_TARGET_FRAGMENT, TAG_DIALING_FRAGMENT)
                intent.putExtra(EXTRA_KEY_TARGET_PHONEITEM, phoneItem)
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
//        if (savedInstanceState == null) {
        LogUtil.d(T.TAG, "onCreate")
        startTarget()
//        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        LogUtil.d(T.TAG, "onNewIntent")
    }

    private fun showDialingPage(phoneItem: PhoneBookItem?) {
        val fragment = supportFragmentManager.findFragmentByTag(TAG_DIALING_FRAGMENT) as DialingFragment?
        fragment?.dismissAllowingStateLoss()
        DialingFragment.newInstance(phoneItem).show(supportFragmentManager, TAG_DIALING_FRAGMENT)
    }

    private fun startTarget() {
        var target = intent.getStringExtra(EXTRA_KEY_TARGET_FRAGMENT)
        val phoneItem = if (intent.getSerializableExtra(EXTRA_KEY_TARGET_PHONEITEM) != null) intent.getSerializableExtra(EXTRA_KEY_TARGET_PHONEITEM) as PhoneBookItem else null
        if (!TextUtils.isEmpty(target)) {
            showDialingPage(phoneItem)
        }
    }

}