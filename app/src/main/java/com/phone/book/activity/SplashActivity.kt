package com.phone.book.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.phone.book.R
import com.phone.book.common.BaseActivity
import com.phone.book.fragment.home.DeptListFragment

class SplashActivity : BaseActivity() {
    private val REQUES_READ_WRITE_CODE = 0x01
    override val layoutId: Int
        get() = R.layout.activity_splash

    override fun initViews() {
        checkPermisson()
    }


    private fun checkPermisson() {
        this?.also {
            if (ContextCompat.checkSelfPermission(it, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                    it, Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(it, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE), REQUES_READ_WRITE_CODE)
            } else {
                startActivity(Intent(this, MainActivity::class.java))
            }
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (requestCode == REQUES_READ_WRITE_CODE) {
                startActivity(Intent(this, MainActivity::class.java))
            }
        } else {
            showMsgDialog("请打开APP的存储权限。",null,{dialog,_->dialog.dismiss()},null)
        }

    }
}