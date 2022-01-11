package com.phone.book.fragment.edit

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.phone.book.PhoneIntents
import com.phone.book.R
import com.phone.book.activity.EditInfoContainerActivity
import com.phone.book.bean.PhoneDepartItem
import com.phone.book.common.BaseFragment
import com.phone.book.common.listener.OnSingleClickListener
import com.phone.book.manager.PhoneInfoManager
import kotlinx.android.synthetic.main.fragment_modify_dept.*


const val TAG_MODIFY_DEPT_FRAGMENT = "ModifyDeptFragment"
const val TAG_TARGET_MODIFY_DEPART_ITEM = "tag_target_modify_depart_item"

class ModifyDeptFragment : BaseFragment() {

    private var mActivity: EditInfoContainerActivity? = null
    private var currentDept: PhoneDepartItem? = null
    override val layoutId: Int
        get() = R.layout.fragment_modify_dept

    companion object {
        @JvmStatic
        fun newInstance(targetDept: PhoneDepartItem?) =
            ModifyDeptFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(TAG_TARGET_MODIFY_DEPART_ITEM, targetDept)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.also {
            val serializableExtra = it.getSerializable(TAG_TARGET_MODIFY_DEPART_ITEM)
            currentDept = if (serializableExtra != null) serializableExtra as PhoneDepartItem else null
        }
    }


    override fun initViews() {
        initToolbar()
        currentDept?.apply {
            modify_dept_current_dept_value.text = name
        }
        bt_modify_dept_save.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(v: View) {
                startModifyDept()
            }
        })
        bt_modify_dept_cancel.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(v: View) {
                mActivity?.onBackPressed()
            }
        })
    }

    private fun startModifyDept() {
        if (modify_dept_add_value.text.toString().trim().isEmpty()) {
            toastMsg("修改为的部门不能为空。")
            return
        }
        if (currentDept == null) {
            toastMsg("当前没选中部门，请返回选中。")
        }
        currentDept?.apply {
            for (phoneDepartItem in PhoneInfoManager.instance.phoneInfo.phoneDepartItemList) {
                if (phoneDepartItem.id == this.id){
                    phoneDepartItem.name = modify_dept_add_value.text.toString().trim()
                    PhoneInfoManager.instance.phoneInfo.saveOrUpdate(requireContext())
                    LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(Intent(PhoneIntents.ACTION_MODIFY_DEPT_SUCCESS))
                    toastMsg("保存成功")
                    mActivity?.onBackPressed()
                }
            }
        }

    }

    private fun initToolbar() {
        mActivity?.hideLogo()
        setToolbarTitle("修改部门", true)
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