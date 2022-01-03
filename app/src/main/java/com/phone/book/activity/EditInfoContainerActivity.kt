package com.phone.book.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import com.phone.book.R
import com.phone.book.common.BaseActivity
import com.phone.book.fragment.EditCallCardFragment
import com.phone.book.fragment.TAG_EDIT_CALL_CARD_FRAGMENT
import com.phone.book.fragment.home.HomeFragment
import com.phone.book.fragment.home.TAG_HOME_FRAGMENT


class EditInfoContainerActivity : BaseActivity() {

    companion object {
        private const val EXTRA_KEY_TARGET_FRAGMENT = "target_fragment"
        private const val TAG = "EditInfoActivity"
        fun startEditCallCardFragment(context: Context?) {
            if (context != null) {
                val intent = Intent(context, EditInfoContainerActivity::class.java)
                intent.putExtra(EXTRA_KEY_TARGET_FRAGMENT, TAG_EDIT_CALL_CARD_FRAGMENT)
                context.startActivity(intent)
            }
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
                TAG_EDIT_CALL_CARD_FRAGMENT -> {
                    fragment = EditCallCardFragment.newInstance()
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