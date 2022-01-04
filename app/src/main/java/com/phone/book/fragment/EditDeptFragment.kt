package com.phone.book.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import com.phone.book.R
import com.phone.book.activity.EditInfoContainerActivity
import com.phone.book.bean.PhoneDepartItem
import com.phone.book.common.BaseFragment
import com.phone.book.common.listener.OnSingleClickListener
import com.phone.book.manager.PhoneInfoManager
import kotlinx.android.synthetic.main.fragment_edit_dept.*


const val TAG_EDIT_DEPT_FRAGMENT = "EditDeptFragment"
const val TAG_TARGET_DEPART_ITEM = "tag_target_depart_item"
class EditDeptFragment : BaseFragment() {

    private var activity: EditInfoContainerActivity? = null
    private var currentDept: PhoneDepartItem?= null
    override val layoutId: Int
        get() = R.layout.fragment_edit_dept

    companion object {
        @JvmStatic
        fun newInstance(targetDept: PhoneDepartItem?) =
            EditDeptFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(TAG_TARGET_DEPART_ITEM,targetDept)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.also {
            val serializableExtra = it.getSerializable(TAG_TARGET_DEPART_ITEM)
            currentDept =  if (serializableExtra != null)serializableExtra as PhoneDepartItem else null
        }
    }

    override fun initViews() {
        initToolbar()
        if (currentDept == null) hideUI()

        edit_dept_save.setOnClickListener(object :OnSingleClickListener(){
            override fun onSingleClick(v: View) {
                startSaveDept()
            }

        })
    }

    private fun startSaveDept() {
        if (edit_dept_add_value.text.isEmpty()) toastMsg("新增部门不能为空。")
        if (currentDept == null){
            val phoneDepartItem = PhoneDepartItem(PhoneInfoManager.instance.phoneInfo.generateDeptId(), 0, 1, edit_dept_add_value.text.toString().trim())
            PhoneInfoManager.instance.phoneInfo.insertDeptItem(phoneDepartItem)
            PhoneInfoManager.instance.phoneInfo.saveOrUpdate(requireContext())
            toastMsg("保存成功")
        }else{

        }
    }

    private fun hideUI() {
        ll_edit_dept_current.visibility = View.GONE
        rg_edit_dept.visibility = View.GONE

    }

    private fun initToolbar() {
        activity?.hideLogo()
        setToolbarTitle("编辑部门",true)
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