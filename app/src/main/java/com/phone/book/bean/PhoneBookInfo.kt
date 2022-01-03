package com.phone.book.bean

import android.content.Context
import com.google.gson.Gson
import com.phone.book.utils.FileSystem
import com.phone.book.utils.PhoneFileUtils
import com.phone.book.utils.PhoneFileUtils.FILE_NAME
import java.io.File

class PhoneBookInfo {
    var phoneList: ArrayList<PhoneBookItem> = arrayListOf()

    companion object {
        fun createNewMesh(): PhoneBookInfo {
            return PhoneBookInfo()
        }
    }

    fun saveOrUpdate(context: Context) {
        FileSystem.writeString(context.filesDir, FILE_NAME, Gson().toJson(this))
        PhoneFileUtils.copyPrivateToDocuments(context, File(context.filesDir, FILE_NAME).absolutePath)
    }

    fun insertPhoneItem(phoneBookItem:PhoneBookItem){
        phoneList.add(phoneBookItem)
    }

    fun getPhoneId(): Long {
        return if (phoneList.size == 0) 1 else (phoneList.size +1).toLong()
    }
}

class PhoneBookItem(
    var id: Long,
    var name: String,
    var department: String,
    var work: String,
    var extension1: String,
    var phone1: String,
    var call1: String,
    var extension2: String,
    var phone2: String,
    var call2: String,
    var home_phone: String,
    var fax: String,
    var area_code: String,
    var email: String,
    var sex: String,//"男士" "女士"
    var remarks: String
)