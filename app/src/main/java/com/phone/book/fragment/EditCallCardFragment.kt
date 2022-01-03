package com.phone.book.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.View
import com.phone.book.R
import com.phone.book.activity.EditInfoContainerActivity
import com.phone.book.bean.PhoneBookInfo
import com.phone.book.bean.PhoneBookItem
import com.phone.book.common.BaseFragment
import com.phone.book.common.listener.OnSingleClickListener
import com.phone.book.common.utils.LogUtil
import com.phone.book.dialog.HeadSelectDialog
import com.phone.book.dialog.TAG_HEAD_SELECT_DIALOG
import com.phone.book.dialog.TYPE_MAN
import com.phone.book.dialog.TYPE_WOMAN
import com.phone.book.manager.PhoneInfoManager
import kotlinx.android.synthetic.main.fragment_edit_call_card.*
import kotlinx.android.synthetic.main.fragment_edit_call_card.view.*


const val TAG_EDIT_CALL_CARD_FRAGMENT = "EditCallCardFragment"

class EditCallCardFragment : BaseFragment() {

    private var activity: EditInfoContainerActivity? = null
    private var isHeadMan = true
    override val layoutId: Int
        get() = R.layout.fragment_edit_call_card

    companion object {
        @JvmStatic
        fun newInstance() =
            EditCallCardFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }


    override fun initViews() {
        initToolbar()

        edit_info_save.setOnClickListener(object :OnSingleClickListener(){
            override fun onSingleClick(v: View) {
                startSaveInfo()
            }
        })

        ll_head_layout.setOnClickListener(object :OnSingleClickListener(){
            override fun onSingleClick(v: View) {
                var fragment = childFragmentManager.findFragmentByTag(TAG_HEAD_SELECT_DIALOG) as? HeadSelectDialog
                fragment?.dismiss()
                fragment = HeadSelectDialog.newInstance() { headType ->
                    LogUtil.d("headType =$headType")
                    changeHead(headType)
                }
                val beginTransaction = childFragmentManager.beginTransaction()
                beginTransaction.add(fragment, TAG_HEAD_SELECT_DIALOG)
                beginTransaction.commitAllowingStateLoss()
            }
        })
    }

    private fun startSaveInfo() {
        if (edit_info_name.desc.isEmpty()) toastMsg("姓名不能为空。")
        if (edit_info_dept.desc.isEmpty()) toastMsg("部门不能为空。")
        val phoneBookItem = PhoneBookItem(PhoneInfoManager.instance.phoneInfo.getPhoneId(),
            edit_info_name.desc.toString().trim(), edit_info_dept.desc, edit_info_work.desc, edit_info_extension1.desc,
            edit_info_phone1.desc, edit_info_call1.desc, edit_info_extension2.desc, edit_info_phone2.desc, edit_info_call2.desc,
            edit_info_home_phone.desc, edit_info_fax.desc, edit_info_area_code.desc, edit_info_email.desc, edit_info_head_text.text?.toString() ?: "", edit_info_remarks.desc
        )
        LogUtil.d("edit_info_name.desc.toString().trim() =${edit_info_name.desc.toString().trim()}")
        PhoneInfoManager.instance.phoneInfo.insertPhoneItem(phoneBookItem)
        PhoneInfoManager.instance.phoneInfo.saveOrUpdate(requireContext())
        toastMsg("保存成功")
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun changeHead(headType: String) {
        context?.apply {
            if (headType == TYPE_WOMAN) {
                iv_head_icon.setImageDrawable(getDrawable(R.mipmap.icon_woman))
                edit_info_head_text.text = TYPE_WOMAN
                isHeadMan = false
            }else{
                iv_head_icon.setImageDrawable(getDrawable(R.mipmap.icon_man))
                edit_info_head_text.text = TYPE_MAN
                isHeadMan = true
            }
        }
    }

    private fun initToolbar() {
        activity?.hideLogo()
        setToolbarTitle("编辑内部名片",true)
    }

    override fun lazyFetchData() {
    }
    

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is EditInfoContainerActivity) {
            activity = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        activity = null
    }

}