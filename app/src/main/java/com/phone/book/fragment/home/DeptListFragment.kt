package com.phone.book.fragment.home

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.phone.book.R
import com.phone.book.activity.MainActivity
import com.phone.book.adapter.DeptTreeAdapter
import com.phone.book.bean.DeptTree
import com.phone.book.common.BaseFragment
import com.phone.book.common.listener.OnSingleClickListener
import com.phone.book.dialog.DeptBottomDialog
import kotlinx.android.synthetic.main.fragment_dept_list.*


const val TAG_DEPT_LIST_FRAGMENT = "DeptListFragment"

class DeptListFragment : BaseFragment() {
    private val TAG_BOTTOM_SHOOT_DIALOG = "tag_bottom_shoot_dialog"
    private var mMainActivity: MainActivity? = null

    override val layoutId: Int
        get() = R.layout.fragment_dept_list

    companion object {
        @JvmStatic
        fun newInstance() =
            DeptListFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }


    override fun initViews() {

        bt_edit_input_tune.setOnClickListener(object :OnSingleClickListener(){
            override fun onSingleClick(v: View) {
                showBottomShootDialog()
            }
        })

        var deptTree: DeptTree = DeptTree("root", null);
        for (i in 0 until 10) {
            var ti = DeptTree("node$i", deptTree);
            deptTree.child.add(ti);
            for (j in 0 until 10) {
                ti.child.add(DeptTree("node$i$j", ti));
            }
        }
        recyclerview_dept_list.layoutManager = LinearLayoutManager(context)
        var adapterDept: DeptTreeAdapter = DeptTreeAdapter(context, deptTree)
        recyclerview_dept_list.adapter = adapterDept

    }

    private fun showBottomShootDialog() {
        val findFragmentByTag =
            childFragmentManager.findFragmentByTag(TAG_BOTTOM_SHOOT_DIALOG) as DeptBottomDialog?
        findFragmentByTag?.dismiss()
        val beginTransaction = childFragmentManager.beginTransaction()
        beginTransaction.add(DeptBottomDialog.newInstance(), TAG_BOTTOM_SHOOT_DIALOG)
        beginTransaction.commitAllowingStateLoss()
    }

    override fun lazyFetchData() {
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainActivity) {
            mMainActivity = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        mMainActivity = null
    }

}