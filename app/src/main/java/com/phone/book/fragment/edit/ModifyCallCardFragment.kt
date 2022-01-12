package com.phone.book.fragment.edit

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.InputFilter
import android.text.InputType
import android.view.View
import android.widget.EditText
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.phone.book.PhoneIntents
import com.phone.book.R
import com.phone.book.activity.EditInfoContainerActivity
import com.phone.book.bean.PhoneBookItem
import com.phone.book.bean.PhoneDepartItem
import com.phone.book.bean.TYPE_MAN
import com.phone.book.bean.TYPE_WOMAN
import com.phone.book.common.BaseFragment
import com.phone.book.common.listener.OnSingleClickListener
import com.phone.book.common.utils.LogUtil
import com.phone.book.dialog.HeadSelectDialog
import com.phone.book.dialog.TAG_HEAD_SELECT_DIALOG
import com.phone.book.manager.PhoneInfoManager
import com.phone.book.utils.InputFilterMinMax
import kotlinx.android.synthetic.main.fragment_add_call_card.*


const val TAG_MODIFY_CALL_CARD_FRAGMENT = "ModifyCallCardFragment"
const val TAG_TARGET_MODIFY_CALL_CARD_ITEM = "tag_target_modify_call_card_item"

class ModifyCallCardFragment : BaseFragment() {


    private var mActivity: EditInfoContainerActivity? = null
    private var currentPhone: PhoneBookItem? = null
    private var currentDept: PhoneDepartItem? = null
    private var isHeadMan = true
    override val layoutId: Int
        get() = R.layout.fragment_add_call_card

    companion object {
        private const val TAG_TARGET_PHONE_BEAN = "tag_target_depart_bean"
        private const val REQUEST_CODE_SELECT_DEPT = 0x01

        @JvmStatic
        fun newInstance(phoneBookItem: PhoneBookItem?) =
            ModifyCallCardFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(TAG_TARGET_PHONE_BEAN, phoneBookItem)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.also {
            val serializableExtra = it.getSerializable(TAG_TARGET_PHONE_BEAN)
            currentPhone = if (serializableExtra != null) serializableExtra as PhoneBookItem else null
            currentDept = currentPhone?.department
        }
    }


    override fun initViews() {
        initToolbar()

        edit_info_extension1.edit_info_box_value?.inputType = InputType.TYPE_CLASS_NUMBER
        edit_info_extension1.edit_info_box_value?.maxEms = 4
        edit_info_extension1.edit_info_box_value?.filters = arrayOf<InputFilter>(InputFilterMinMax("0", "9999"))
        edit_info_extension2.edit_info_box_value?.inputType = InputType.TYPE_CLASS_NUMBER
        edit_info_extension2.edit_info_box_value?.maxEms = 4
        edit_info_extension2.edit_info_box_value?.filters = arrayOf<InputFilter>(InputFilterMinMax("0", "9999"))
        disAbleDeptEdit(edit_info_dept.edit_info_box_value)

        initViewUI()

        var fragment = this
        edit_info_dept.edit_info_box_value?.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(v: View) {
                EditInfoContainerActivity.startSelectDeptFragment(fragment, REQUEST_CODE_SELECT_DEPT)
            }
        })

        edit_info_save.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(v: View) {
                startSaveInfo()
            }
        })

        edit_info_cancel.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(v: View) {
                mActivity?.onBackPressed()
            }
        })

        ll_head_layout.setOnClickListener(object : OnSingleClickListener() {
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

    private fun initViewUI() {
        currentPhone?.apply {
            edit_info_name.desc = name
            edit_info_work.desc = work
            edit_info_dept.desc = department.name
            edit_info_extension1.desc = extension1
            edit_info_phone1.desc = phone1
            edit_info_call1.desc = call1
            edit_info_extension2.desc = extension2
            edit_info_phone2.desc = phone2
            edit_info_call2.desc = call2
            edit_info_home_phone.desc = home_phone
            edit_info_fax.desc = fax
            edit_info_area_code.desc = area_code
            edit_info_email.desc = email
            edit_info_remarks.desc = remarks
            if (sex.equals(TYPE_MAN)){
                iv_head_icon.setImageDrawable(resources.getDrawable(R.mipmap.icon_man))
                edit_info_head_text.text = TYPE_MAN
            } else {
                iv_head_icon.setImageDrawable(resources.getDrawable(R.mipmap.icon_woman))
                edit_info_head_text.text = TYPE_WOMAN
            }

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CODE_SELECT_DEPT) {
                data?.apply {
                    val serializableExtra = getSerializableExtra(EXTRA_SELECT_DEPT)
                    val targetDept = if (serializableExtra != null) serializableExtra as PhoneDepartItem else null
                    currentDept = targetDept
                    edit_info_dept?.desc = currentDept?.name ?: ""
                }
            }
        }
    }

    private fun disAbleDeptEdit(deptEdit: EditText?) {
        deptEdit?.apply {
            isFocusable = false
            isFocusableInTouchMode = false
            isLongClickable = false
            inputType = InputType.TYPE_NULL

        }
    }

    private fun startSaveInfo() {
        if (edit_info_name.desc.isEmpty()) {
            toastMsg("姓名不能为空。")
            return
        }
        if (edit_info_dept.desc.isEmpty()) {
            toastMsg("部门不能为空。")
            return
        }
        if (currentDept != null && currentPhone != null) {
            val phoneList = PhoneInfoManager.instance.phoneInfo.phoneList
            var targetPhone :PhoneBookItem? = null
            for (phoneItem in phoneList) {
                if (phoneItem.id ==  currentPhone!!.id) {
                    targetPhone = phoneItem
                }
            }
            targetPhone?.apply {
                targetPhone.name = edit_info_name.desc.toString().trim()
                targetPhone.department = currentDept as PhoneDepartItem
                targetPhone.work = edit_info_work.desc
                targetPhone.extension1 = edit_info_extension1.desc
                targetPhone.phone1 = edit_info_phone1.desc
                targetPhone.call1 = edit_info_call1.desc
                targetPhone.extension2 = edit_info_extension2.desc
                targetPhone.phone2 = edit_info_phone2.desc
                targetPhone.call2 = edit_info_call2.desc
                targetPhone.home_phone = edit_info_home_phone.desc
                targetPhone.fax = edit_info_fax.desc
                targetPhone.area_code = edit_info_area_code.desc
                targetPhone.email = edit_info_email.desc
                targetPhone.sex = edit_info_head_text.text?.toString() ?: ""
                targetPhone.remarks = edit_info_remarks.desc

                PhoneInfoManager.instance.phoneInfo.saveOrUpdate(requireContext())
                toastMsg("保存成功")
                mActivity?.onBackPressed()
            }

//            PhoneInfoManager.instance.phoneInfo.insertPhoneItem(phoneBookItem)

        } else {
            toastMsg("至少需要选择一个部门")
            return
        }
        LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(Intent(PhoneIntents.ACTION_MODIFY_CALL_CARD_SUCCESS))

    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun changeHead(headType: String) {
        context?.apply {
            if (headType == TYPE_WOMAN) {
                iv_head_icon.setImageDrawable(getDrawable(R.mipmap.icon_woman))
                edit_info_head_text.text = TYPE_WOMAN
                isHeadMan = false
            } else {
                iv_head_icon.setImageDrawable(getDrawable(R.mipmap.icon_man))
                edit_info_head_text.text = TYPE_MAN
                isHeadMan = true
            }
        }
    }

    private fun initToolbar() {
        mActivity?.hideLogo()
        setToolbarTitle("编辑内部名片", true)
    }

    override fun lazyFetchData() {
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is EditInfoContainerActivity) {
            mActivity = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        mActivity = null
    }

}