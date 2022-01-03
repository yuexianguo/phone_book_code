package com.phone.book.manager

import android.content.Context
import com.google.gson.Gson
import com.phone.book.bean.PhoneBookInfo
import com.phone.book.utils.FileSystem
import com.phone.book.utils.PhoneFileUtils
import java.io.File

class PhoneInfoManager {
    private constructor()
    private lateinit var context: Context
    lateinit var phoneInfo: PhoneBookInfo
    companion object {
        val instance: PhoneInfoManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            PhoneInfoManager()
        }
    }
    fun init(context: Context) {
        this.context = context.applicationContext
        initPhoneInfo()
    }

    private fun initPhoneInfo() {
        val configObj = FileSystem.readString(File(context.filesDir, PhoneFileUtils.FILE_NAME))
        if (configObj == null || configObj.isEmpty()) {
            phoneInfo = PhoneBookInfo.createNewMesh()
            phoneInfo.saveOrUpdate(context)
        } else {
            phoneInfo = Gson().fromJson(configObj, PhoneBookInfo::class.java)
        }
    }
}