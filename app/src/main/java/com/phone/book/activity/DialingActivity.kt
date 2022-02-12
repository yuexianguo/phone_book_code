package com.phone.book.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import com.phone.book.R
import com.phone.book.bean.PhoneBookItem
import com.phone.book.bean.PhoneDepartItem
import com.phone.book.common.BaseActivity
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

        fun open(){

        }

    }

    override val layoutId: Int
        get() = R.layout.activity_edit_info_container

    override fun initViews() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            startTarget()
        }
    }

    private fun startTarget() {
        var target = intent.getStringExtra(EXTRA_KEY_TARGET_FRAGMENT)
        if (!TextUtils.isEmpty(target)) {
            var fragment: Fragment? = null
            when (target) {
                TAG_DIALING_FRAGMENT -> {
//                    val serializableExtra = intent.getSerializableExtra(TAG_TARGET_DEPART_ITEM)
//                    val targetDept = if (serializableExtra != null) serializableExtra as PhoneDepartItem else null
                    fragment = DialingFragment.newInstance("")
                }

                else -> {
                }
            }
            if (fragment != null) {
                val transaction = supportFragmentManager.beginTransaction()
                transaction.add(R.id.fl_fragment_edit_info_container, fragment)
                transaction.commit()
            }
        }
    }


}