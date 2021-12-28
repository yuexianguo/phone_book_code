package com.phone.book.ui

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.text.TextUtils
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.phone.book.BuildConfig
import com.phone.book.R
import com.phone.book.common.BaseActivity
import com.phone.book.common.BaseApplication
import com.phone.book.common.data.source.StrutDataRepository
import com.phone.book.common.data.source.StrutDataSource
import com.phone.book.common.utils.ActivityUtils.replaceFragment
import com.phone.book.common.utils.VersionUtils
import io.reactivex.disposables.CompositeDisposable
import com.google.gson.Gson
import com.phone.book.common.BaseApplication.Companion.context
import com.phone.book.common.utils.FileSystem
import com.phone.book.file.PhoneBookInfo
import com.phone.book.utils.PhoneFileUtils
import java.io.File


class MainActivity : BaseActivity() {
    private var mIsActive = false
    private var mIsViewGoogleStore = false
    private var mStrutDataSource: StrutDataSource? = null
    private var mCompositeDisposable: CompositeDisposable? = CompositeDisposable()
    override val layoutId: Int
        get() = R.layout.activity_main
    val FILE_NAME = "testphone.txt"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            startMainFragment(false)
        }
    }

    override fun initViews() {
        checkSdcardPermission()
        if (mStrutDataSource == null) {
            mStrutDataSource = StrutDataRepository()
        }
    }

    private fun checkSdcardPermission() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                REQUEST_CODE_WRITE_EXTERNAL_STORAGE
            )
        } else {

//            FileSystem.writeString(context.filesDir, FILE_NAME, Gson().toJson(PhoneBookInfo("研发","137")))
            PhoneFileUtils.querySignImage(context,"Myphone.txt",File(filesDir,"Myphone_111.txt").absolutePath)
        }
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.fl_main_container)
        if (fragment is MainFragment) {
            showMsgDialog(
                getString(R.string.common_strut),
                getString(R.string.close_app_tips), { dialog: DialogInterface, _: Int ->
                    super@MainActivity.onBackPressed()
                    dialog.dismiss()
                    super.onBackPressed()
                }) { dialog: DialogInterface, which: Int -> dialog.dismiss() }
        } else {
            super.onBackPressed()
        }
    }

    private fun replaceFragment(
        fragment: Fragment,
        addToStack: Boolean,
        tag: String,
        allowLoss: Boolean
    ) {
        replaceFragment(this, R.id.fl_main_container, fragment, addToStack, tag, allowLoss)
    }

    private fun startMainFragment(addToStack: Boolean) {
        replaceFragment(MainFragment.newInstance(), addToStack, TAG_MAIN_FRAGMENT, true)
    }

//    fun startDeviceTypesFragment(addToStack: Boolean) {
//        replaceFragment(
//            DeviceTypesFragment.newInstance(),
//            addToStack,
//            DeviceTypesFragment::class.java.simpleName,
//            true
//        )
//    }

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
        mCompositeDisposable?.clear()
        mCompositeDisposable = null
        mStrutDataSource = null
    }

    companion object {
        private const val TAG = "MainActivity"
        const val TAG_FROM_ADD_START = "tag_from_add_start"
        const val REQUEST_CODE_WRITE_EXTERNAL_STORAGE = 0x01
    }
}