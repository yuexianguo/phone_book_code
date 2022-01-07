package com.phone.book.dialog

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.phone.book.PhoneIntents
import com.phone.book.R
import com.phone.book.activity.EditInfoContainerActivity
import com.phone.book.bean.PhoneDepartItem
import com.phone.book.common.BaseApplication
import com.phone.book.common.dialog.BaseDialogFragment
import com.phone.book.common.listener.OnSingleClickListener
import com.phone.book.manager.PhoneInfoManager


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
        val dept_bottom_dialog_delete = alertDialog.findViewById<Button>(R.id.delete_dept)
        var fragment = this
        add_dept?.setOnClickListener(object :OnSingleClickListener(){
            override fun onSingleClick(v: View) {
                dismiss()
                EditInfoContainerActivity.startEditDeptFragment(fragment.context,phoneDepartItem)
            }
        })

        add_call_card?.setOnClickListener(object :OnSingleClickListener(){
            override fun onSingleClick(v: View) {
                dismiss()
                EditInfoContainerActivity.startEditCallCardFragment(fragment.context,phoneDepartItem)
            }
        })

        dept_bottom_dialog_cancel?.setOnClickListener(object : OnSingleClickListener(){
            override fun onSingleClick(v: View) {
                dismiss()
            }
        })

        dept_bottom_dialog_delete?.setOnClickListener(object : OnSingleClickListener(){
            override fun onSingleClick(v: View) {
                phoneDepartItem?.also {
                    it.id?.apply {
                        showMsgDialog("你确定删除部门：${it.name}",null,{dialog,_->
                            PhoneInfoManager.instance.phoneInfo.deleteDept(this)
                            PhoneInfoManager.instance.phoneInfo.saveOrUpdate(BaseApplication.context)
                            LocalBroadcastManager.getInstance(BaseApplication.context).sendBroadcast(Intent(PhoneIntents.ACTION_MODIFY_DEPT_SUCCESS))
                            showLoading(null,null,false)
                            Handler().postDelayed(Runnable {
                                dismissLoading()
                                LocalBroadcastManager.getInstance(BaseApplication.context).sendBroadcast(Intent(PhoneIntents.ACTION_MODIFY_CALL_CARD_SUCCESS))
                                dialog.dismiss()
                            },1000L)

                        },{dialog,_->dialog.dismiss()},true)
                    }

                }

                dismiss()
            }
        })
    }
}