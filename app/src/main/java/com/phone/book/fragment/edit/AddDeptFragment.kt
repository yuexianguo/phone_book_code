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
import kotlinx.android.synthetic.main.fragment_add_dept.*


const val TAG_ADD_DEPT_FRAGMENT = "AddDeptFragment"
const val TAG_TARGET_DEPART_ITEM = "tag_target_depart_item"

class AddDeptFragment : BaseFragment() {

    private var mActivity: EditInfoContainerActivity? = null
    private var currentDept: PhoneDepartItem? = null
    override val layoutId: Int
        get() = R.layout.fragment_add_dept

    companion object {
        @JvmStatic
        fun newInstance(targetDept: PhoneDepartItem?) =
            AddDeptFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(TAG_TARGET_DEPART_ITEM, targetDept)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.also {
            val serializableExtra = it.getSerializable(TAG_TARGET_DEPART_ITEM)
            currentDept = if (serializableExtra != null) serializableExtra as PhoneDepartItem else null
        }
    }

    override fun initViews() {
        initToolbar()
        if (currentDept == null) {
            hideUI()
        }
        currentDept?.apply {
            add_dept_current_dept_value.text = name
        }

        bt_add_dept_save.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(v: View) {
                startSaveDept()
            }
        })
        bt_add_dept_cancel.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(v: View) {
                mActivity?.onBackPressed()
            }
        })
    }

    private fun startSaveDept() {
        if (add_dept_add_value.text.toString().trim().isEmpty()) {
            toastMsg("新增部门不能为空。")
            return
        }

        var isSameDept = rg_add_dept.checkedRadioButtonId == R.id.rb_add_dept_same_level

        currentDept?.also {
            if (!isSameDept){
                //次级最多到6层
                if (it.level == 6) {
                    toastMsg("由于次级部门比较多，请建同级部门。")
                    return
                }
            }
        }

        if (currentDept != null) {
            var pid = if (isSameDept) currentDept!!.pid else currentDept!!.id
            var level = if (isSameDept) currentDept!!.level else currentDept!!.level + 1
            val phoneDepartItem = PhoneDepartItem(PhoneInfoManager.instance.phoneInfo.generateDeptId(), pid, level, add_dept_add_value.text.toString().trim())
            PhoneInfoManager.instance.phoneInfo.insertDeptItem(phoneDepartItem)
            PhoneInfoManager.instance.phoneInfo.saveOrUpdate(requireContext())
            toastMsg("保存成功")
            mActivity?.onBackPressed()
        } else {
            val phoneDepartItem = PhoneDepartItem(PhoneInfoManager.instance.phoneInfo.generateDeptId(), 0, 1, add_dept_add_value.text.toString().trim())
            PhoneInfoManager.instance.phoneInfo.insertDeptItem(phoneDepartItem)
            PhoneInfoManager.instance.phoneInfo.saveOrUpdate(requireContext())
            toastMsg("保存成功")
            mActivity?.onBackPressed()
        }
        LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(Intent(PhoneIntents.ACTION_MODIFY_DEPT_SUCCESS))
    }

    private fun hideUI() {
        ll_add_dept_current.visibility = View.GONE
        rg_add_dept.visibility = View.GONE

    }

    private fun initToolbar() {
        mActivity?.hideLogo()
        setToolbarTitle("新增部门", true)
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