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
import com.phone.book.fragment.edit.*
import com.phone.book.fragment.info.PhoneInfoFragment

import com.phone.book.fragment.info.TAG_PHONE_INFO_FRAGMENT
import com.phone.book.fragment.info.TAG_TARGET_PHONE_ITEM


class EditInfoContainerActivity : BaseActivity() {

    companion object {
        private const val EXTRA_KEY_TARGET_FRAGMENT = "target_fragment"
        private const val TAG = "EditInfoActivity"


        fun startAddCallCardFragment(context: Context?, targetDept: PhoneDepartItem?) {
            if (context != null) {
                val intent = Intent(context, EditInfoContainerActivity::class.java)
                intent.putExtra(EXTRA_KEY_TARGET_FRAGMENT, TAG_Add_CALL_CARD_FRAGMENT)
                intent.putExtra(TAG_TARGET_DEPART_ITEM, targetDept)
                context.startActivity(intent)
            }
        }

        fun startAddDeptFragment(context: Context?, targetDept: PhoneDepartItem?) {
            if (context != null) {
                val intent = Intent(context, EditInfoContainerActivity::class.java)
                intent.putExtra(EXTRA_KEY_TARGET_FRAGMENT, TAG_ADD_DEPT_FRAGMENT)
                intent.putExtra(TAG_TARGET_DEPART_ITEM, targetDept)
                context.startActivity(intent)
            }
        }

        fun startSelectDeptFragment(fragment: Fragment?, requestCode: Int) {
            if (fragment != null && fragment.context != null) {
                val intent = Intent(fragment.context, EditInfoContainerActivity::class.java)
                intent.putExtra(EXTRA_KEY_TARGET_FRAGMENT, TAG_SELECT_DEPT_FRAGMENT)
                fragment.startActivityForResult(intent, requestCode)
            }
        }

        fun startPhoneInfoPage(context: Context?, phoneItem: PhoneBookItem) {
            if (context != null) {
                val intent = Intent(context, EditInfoContainerActivity::class.java)
                intent.putExtra(EXTRA_KEY_TARGET_FRAGMENT, TAG_PHONE_INFO_FRAGMENT)
                intent.putExtra(TAG_TARGET_PHONE_ITEM, phoneItem)
                context.startActivity(intent)
            }
        }

        fun startModifyDeptFragment(context: Context?, phoneDepartItem: PhoneDepartItem?) {
            if (context != null) {
                val intent = Intent(context, EditInfoContainerActivity::class.java)
                intent.putExtra(EXTRA_KEY_TARGET_FRAGMENT, TAG_MODIFY_DEPT_FRAGMENT)
                intent.putExtra(TAG_TARGET_MODIFY_DEPART_ITEM, phoneDepartItem)
                context.startActivity(intent)
            }
        }

        fun startModifyCallCardFragment(context: Context?, phoneBookItem: PhoneBookItem) {
            if (context != null) {
                val intent = Intent(context, EditInfoContainerActivity::class.java)
                intent.putExtra(EXTRA_KEY_TARGET_FRAGMENT, TAG_MODIFY_CALL_CARD_FRAGMENT)
                intent.putExtra(TAG_TARGET_MODIFY_CALL_CARD_ITEM, phoneBookItem)
                context.startActivity(intent)
            }
        }

        fun startCopyCallCardFragment(context: Context?, phoneBookItem: PhoneBookItem) {
            if (context != null) {
                val intent = Intent(context, EditInfoContainerActivity::class.java)
                intent.putExtra(EXTRA_KEY_TARGET_FRAGMENT, TAG_COPY_CALL_CARD_FRAGMENT)
                intent.putExtra(TAG_TARGET_COPY_CALL_CARD_ITEM, phoneBookItem)
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
                TAG_Add_CALL_CARD_FRAGMENT -> {
                    val serializableExtra = intent.getSerializableExtra(TAG_TARGET_DEPART_ITEM)
                    val targetDept = if (serializableExtra != null) serializableExtra as PhoneDepartItem else null
                    fragment = AddCallCardFragment.newInstance(targetDept)
                }
                TAG_ADD_DEPT_FRAGMENT -> {
                    val serializableExtra = intent.getSerializableExtra(TAG_TARGET_DEPART_ITEM)
                    val targetDept = if (serializableExtra != null) serializableExtra as PhoneDepartItem else null
                    fragment = AddDeptFragment.newInstance(targetDept)
                }
                TAG_SELECT_DEPT_FRAGMENT -> {
                    fragment = SelectDeptFragment.newInstance()
                }
                TAG_PHONE_INFO_FRAGMENT -> {
                    val serializableExtra = intent.getSerializableExtra(TAG_TARGET_PHONE_ITEM)
                    val phoneItem = if (serializableExtra != null) serializableExtra as PhoneBookItem else null
                    fragment = PhoneInfoFragment.newInstance(phoneItem)
                }
                TAG_MODIFY_DEPT_FRAGMENT -> {
                    val serializableExtra = intent.getSerializableExtra(TAG_TARGET_MODIFY_DEPART_ITEM)
                    val targetDept = if (serializableExtra != null) serializableExtra as PhoneDepartItem else null
                    fragment = ModifyDeptFragment.newInstance(targetDept)
                }
                TAG_MODIFY_CALL_CARD_FRAGMENT -> {
                    val serializableExtra = intent.getSerializableExtra(TAG_TARGET_MODIFY_CALL_CARD_ITEM)
                    val targetPhone = if (serializableExtra != null) serializableExtra as PhoneBookItem else null
                    fragment = ModifyCallCardFragment.newInstance(targetPhone)
                }
                TAG_COPY_CALL_CARD_FRAGMENT -> {
                    val serializableExtra = intent.getSerializableExtra(TAG_TARGET_COPY_CALL_CARD_ITEM)
                    val targetPhone = if (serializableExtra != null) serializableExtra as PhoneBookItem else null
                    fragment = CopyCallCardFragment.newInstance(targetPhone)
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