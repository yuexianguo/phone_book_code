package com.phone.book.fragment

import android.content.Context
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.phone.book.R
import com.phone.book.activity.MainActivity
import com.phone.book.adapter.DeptTreeAdapter
import com.phone.book.bean.Tree
import com.phone.book.common.BaseFragment
import kotlinx.android.synthetic.main.fragment_dept_list.*


const val TAG_DEPT_LIST_FRAGMENT = "DeptListFragment"

class DeptListFragment : BaseFragment() {

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
        var tree: Tree = Tree("root", null);
        for (i in 0 until 10) {
            var ti = Tree("node$i", tree);
            tree.child.add(ti);
            for (j in 0 until 10) {
                ti.child.add(Tree("node$i$j", ti));
            }
        }
        recyclerview_dept_list.layoutManager = LinearLayoutManager(context)
        var adapterDept: DeptTreeAdapter = DeptTreeAdapter(context, tree)
        recyclerview_dept_list.adapter = adapterDept

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