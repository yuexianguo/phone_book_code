package com.phone.book.activity

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.phone.book.R
import com.phone.book.common.BaseActivity
import com.phone.book.common.utils.ActivityUtils.replaceFragment
import com.phone.book.common.utils.PrefUtils
import com.phone.book.fragment.home.HomeFragment
import com.phone.book.fragment.home.TAG_HOME_FRAGMENT
import com.phone.book.jobservice.Helpers
import com.phone.book.manager.PhoneInfoManager


class MainActivity : BaseActivity() {
    private var mIsActive = false
    companion object {
        private const val TAG = "MainActivity"
    }

    override val layoutId: Int
        get() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            startHomeFragment(false)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun initViews() {
        findViewById<View>(android.R.id.list)
        android.R.layout.simple_selectable_list_item
        PrefUtils.writeLong("startServiceTime", 0L)
        Helpers.schedule(this)
    }



    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.fl_main_container)
        if (fragment is HomeFragment) {
            showMsgDialog(
                getString(R.string.call_log),
                getString(R.string.close_app_tips), { dialog: DialogInterface, _: Int ->
                    super@MainActivity.onBackPressed()
                    dialog.dismiss()
                    super.onBackPressed()
                }) { dialog: DialogInterface, _: Int -> dialog.dismiss() }
        } else {
            super.onBackPressed()
        }
    }

    private fun replacesFragment(
        fragment: Fragment,
        addToStack: Boolean,
        tag: String,
        allowLoss: Boolean
    ) {
        replaceFragment(this, R.id.fl_main_container, fragment, addToStack, tag, allowLoss)
    }

    private fun startHomeFragment(addToStack: Boolean) {
        replacesFragment(HomeFragment.newInstance(), addToStack, TAG_HOME_FRAGMENT, true)
    }

    override fun onResume() {
        super.onResume()
        mIsActive = true

    }

    override fun onStop() {
        super.onStop()
        mIsActive = false
    }

    override fun onDestroy() {
        super.onDestroy()
        dismissLoading()
    }


}