package com.phone.book.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.phone.book.R
import com.phone.book.activity.EditInfoContainerActivity
import com.phone.book.adapter.DeptSingleSelectAdapter
import com.phone.book.bean.PhoneDepartItem
import com.phone.book.common.BaseFragment
import com.phone.book.manager.PhoneInfoManager
import kotlinx.android.synthetic.main.fragment_select_dept.*
import java.util.HashMap


const val TAG_SELECT_DEPT_FRAGMENT = "SelectDeptFragment"

class SelectDeptFragment : BaseFragment() {

    private var mActivity: EditInfoContainerActivity? = null
    private var mAdapter: DeptSingleSelectAdapter<PhoneDepartItem>? = null
    override val layoutId: Int
        get() = R.layout.fragment_select_dept

    companion object {
        @JvmStatic
        fun newInstance() = SelectDeptFragment()
    }


    override fun initViews() {
        initToolbar()
        val phoneDepartItemList = PhoneInfoManager.instance.phoneInfo.phoneDepartItemList
        select_dept_recyclerView.layoutManager = LinearLayoutManager(context)
        mAdapter = object : DeptSingleSelectAdapter<PhoneDepartItem>(phoneDepartItemList) {
            override fun onBindSingleViewHolder(vh: SingleSelectHolder, t: PhoneDepartItem) {
                vh.apply {
                    t.let {
                        rbtItem.text = it.name

                    }
                }
            }
        }
        select_dept_recyclerView.adapter = mAdapter
    }

    
    private fun initToolbar() {
        mActivity?.hideLogo()
        setToolbarTitle("选择你需要的部门",true)
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