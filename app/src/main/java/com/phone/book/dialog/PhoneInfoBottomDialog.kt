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
import com.phone.book.bean.PhoneBookItem
import com.phone.book.common.dialog.BaseDialogFragment
import com.phone.book.common.listener.OnSingleClickListener


/**
 * description :
 * author : Andy.Guo
 * email : Andy.Guo@waclightiong.com.cn
 * data : 2022/1/12
 */
const val TAG_PHONE_INFO_BOTTOM = "tag_phone_info_bottom"
class PhoneInfoBottomDialog : BaseDialogFragment() {
    private var mPhoneBookItem: PhoneBookItem?=null
    companion object {
        @JvmStatic
        fun newInstance(phoneBookItem: PhoneBookItem?) = PhoneInfoBottomDialog().apply {
            arguments = Bundle().apply {
                putSerializable(TAG_PHONE_INFO_BOTTOM,phoneBookItem)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.also {
            val serializableExtra = it.getSerializable(TAG_PHONE_INFO_BOTTOM)
            mPhoneBookItem =  if (serializableExtra != null)serializableExtra as PhoneBookItem else null
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)

        val alertDialog = AlertDialog.Builder(requireContext(), R.style.commonDialogCustom).create()
        alertDialog.setCanceledOnTouchOutside(true)
        alertDialog.window?.setGravity(Gravity.BOTTOM)
        alertDialog.window?.setWindowAnimations(R.style.DeptBottomAnimaStyle)
        alertDialog.show()
        alertDialog.setContentView(R.layout.phone_info_bottom_dialog)
        alertDialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        initView(alertDialog)
        return alertDialog

    }

    private fun initView(alertDialog: AlertDialog) {
        val bt_phone_bottom_add_card = alertDialog.findViewById<Button>(R.id.bt_phone_bottom_add_card)
        val bt_phone_bottom_modify_card = alertDialog.findViewById<Button>(R.id.bt_phone_bottom_modify_card)
        val bt_phone_bottom_copy_card = alertDialog.findViewById<Button>(R.id.bt_phone_bottom_copy_card)
        val bt_phone_bottom_delete_card = alertDialog.findViewById<Button>(R.id.bt_phone_bottom_delete_card)
        val bt_phone_bottom_cancel = alertDialog.findViewById<Button>(R.id.bt_phone_bottom_cancel)
        var fragment = this
        bt_phone_bottom_add_card?.setOnClickListener(object :OnSingleClickListener(){
            override fun onSingleClick(v: View) {
                mPhoneBookItem?.apply {
                    dismiss()
                    activity?.finish()
                    EditInfoContainerActivity.startAddCallCardFragment(fragment.context,department)
                }
            }
        })

        bt_phone_bottom_modify_card?.setOnClickListener(object :OnSingleClickListener(){
            override fun onSingleClick(v: View) {
                mPhoneBookItem?.apply {
                    dismiss()
                    activity?.finish()
                    EditInfoContainerActivity.startModifyCallCardFragment(fragment.context,this)
                }

            }
        })

        bt_phone_bottom_copy_card?.setOnClickListener(object :OnSingleClickListener(){
            override fun onSingleClick(v: View) {
                mPhoneBookItem?.apply {
                    dismiss()
                    activity?.finish()
                    EditInfoContainerActivity.startCopyCallCardFragment(fragment.context,this)
                }
            }
        })

        bt_phone_bottom_delete_card?.setOnClickListener(object : OnSingleClickListener(){
            override fun onSingleClick(v: View) {
                mPhoneBookItem?.also {
                    it.id?.apply {
                        showMsgDialog("你确定删除名片：${it.name}",null,{dialog,_->
//                            PhoneInfoManager.instance.phoneInfo.deleteDept(this)
//                            PhoneInfoManager.instance.phoneInfo.saveOrUpdate(BaseApplication.context)
//                            LocalBroadcastManager.getInstance(BaseApplication.context).sendBroadcast(Intent(PhoneIntents.ACTION_DELETE_DEPT_SUCCESS))

                            dialog.dismiss()
                        },{dialog,_->dialog.dismiss()},true)
                    }

                }

                dismiss()
            }
        })

        bt_phone_bottom_cancel?.setOnClickListener(object : OnSingleClickListener(){
            override fun onSingleClick(v: View) {
                dismiss()
            }
        })
    }
}