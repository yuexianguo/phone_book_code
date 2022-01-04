package com.phone.book.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import com.phone.book.R
import com.phone.book.activity.EditInfoContainerActivity
import com.phone.book.bean.PhoneDepartItem
import com.phone.book.common.dialog.BaseDialogFragment
import com.phone.book.common.listener.OnSingleClickListener
import com.phone.book.fragment.TAG_TARGET_DEPART_ITEM


/**
 * description :
 * author : Andy.Guo
 * email : Andy.Guo@waclightiong.com.cn
 * data : 2021/10/20
 */
const val TAG_PHONE_DEPAR = "tag_phone_depar"
class DeptBottomDialog : BaseDialogFragment() {
    private var phoneDepartItem: PhoneDepartItem?=null
    companion object {
        @JvmStatic
        fun newInstance(phoneDepartItem: PhoneDepartItem?) = DeptBottomDialog().apply {
            arguments = Bundle().apply {
                putSerializable(TAG_PHONE_DEPAR,phoneDepartItem)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.also {
            val serializableExtra = it.getSerializable(TAG_PHONE_DEPAR)
            phoneDepartItem =  if (serializableExtra != null)serializableExtra as PhoneDepartItem else null
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)

        val alertDialog = AlertDialog.Builder(requireContext(), R.style.commonDialogCustom).create()
        alertDialog.setCanceledOnTouchOutside(true)
        alertDialog.window?.setGravity(Gravity.BOTTOM)
        alertDialog.window?.setWindowAnimations(R.style.DeptBottomAnimaStyle)
        alertDialog.show()
        alertDialog.setContentView(R.layout.dept_bottom_dialog)
        alertDialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        initView(alertDialog)
        return alertDialog

    }

    private fun initView(alertDialog: AlertDialog) {
        val add_dept = alertDialog.findViewById<Button>(R.id.add_dept)
        val add_call_card = alertDialog.findViewById<Button>(R.id.add_call_card)
        val dept_bottom_dialog_cancel = alertDialog.findViewById<Button>(R.id.dept_bottom_dialog_cancel)
        var fragment = this
        add_dept?.setOnClickListener(object :OnSingleClickListener(){
            override fun onSingleClick(v: View) {
                dismiss()
                EditInfoContainerActivity.startEditDeptFragment(fragment.requireContext(),phoneDepartItem)
            }
        })

        add_call_card?.setOnClickListener(object :OnSingleClickListener(){
            override fun onSingleClick(v: View) {
                dismiss()
                EditInfoContainerActivity.startEditCallCardFragment(fragment.requireContext(),phoneDepartItem)
            }
        })

        dept_bottom_dialog_cancel?.setOnClickListener(object : OnSingleClickListener(){
            override fun onSingleClick(v: View) {
                dismiss()
            }
        })
    }
}