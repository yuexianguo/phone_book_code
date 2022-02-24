package com.phone.book.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.phone.book.R
import com.phone.book.activity.EditInfoContainerActivity
import com.phone.book.bean.TYPE_MAN
import com.phone.book.bean.TYPE_WOMAN
import com.phone.book.common.dialog.BaseDialogFragment
import com.phone.book.common.listener.OnSingleClickListener


/**
 * description :
 * author : Andy.Guo
 * email : 495311081@qq.com
 * data : 2021/10/20
 */

const val TAG_HEAD_SELECT_DIALOG = "tag_head_select_dialog"

class HeadSelectDialog(private val listener: ((type:String) -> Unit)?) : BaseDialogFragment() {
    companion object {
        @JvmStatic
        fun newInstance(listener: ((type:String) -> Unit)?) = HeadSelectDialog(listener)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)
        val alertDialog = AlertDialog.Builder(requireContext(), R.style.commonDialogCustom).create()
        alertDialog.setCanceledOnTouchOutside(true)
        alertDialog.window?.setGravity(Gravity.BOTTOM)
        alertDialog.window?.setWindowAnimations(R.style.DeptBottomAnimaStyle)
        alertDialog.show()
        alertDialog.setContentView(R.layout.dialog_head_select)
        alertDialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        initView(alertDialog)
        return alertDialog

    }

    private fun initView(alertDialog: AlertDialog) {
        val dialog_head_tv_item_man = alertDialog.findViewById<TextView>(R.id.dialog_head_tv_item_man)
        val dialog_head_tv_item_woman = alertDialog.findViewById<TextView>(R.id.dialog_head_tv_item_woman)
        dialog_head_tv_item_man?.setOnClickListener(object :OnSingleClickListener(){
            override fun onSingleClick(v: View) {
                listener?.invoke(TYPE_MAN)
                dismiss()
            }
        })

        dialog_head_tv_item_woman?.setOnClickListener(object : OnSingleClickListener(){
            override fun onSingleClick(v: View) {
                listener?.invoke(TYPE_WOMAN)
                dismiss()
            }
        })
    }
}