package com.phone.book.fragment.home

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.phone.book.R
import com.phone.book.activity.MainActivity
import com.phone.book.adapter.DeptTreeAdapter
import com.phone.book.bean.DeptTree
import com.phone.book.bean.PhoneDepartItem
import com.phone.book.common.BaseFragment
import com.phone.book.common.listener.OnSingleClickListener
import com.phone.book.common.utils.LogUtil
import com.phone.book.dialog.DeptBottomDialog
import com.phone.book.manager.PhoneInfoManager
import kotlinx.android.synthetic.main.fragment_dept_list.*
import java.util.*


const val TAG_DEPT_LIST_FRAGMENT = "DeptListFragment"

class DeptListFragment : BaseFragment() {
    private val TAG_BOTTOM_SHOOT_DIALOG = "tag_bottom_shoot_dialog"
    private var mMainActivity: MainActivity? = null
    private var adapterDept: DeptTreeAdapter? = null
    private var mTreeList: ArrayList<DeptTree> = ArrayList<DeptTree>();
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
        bt_edit_input_tune.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(v: View) {
                showBottomShootDialog()
            }
        })
        initJiaData()

        updateUI()
        recyclerview_dept_list.layoutManager = LinearLayoutManager(context)
        adapterDept = DeptTreeAdapter(context, mTreeList)
        recyclerview_dept_list.adapter = adapterDept

    }

    private fun initJiaData() {
        PhoneInfoManager.instance.phoneInfo.insertDeptItem(
            PhoneDepartItem(
                PhoneInfoManager.instance.phoneInfo.generateDeptId(),
                1, 2, "研发1"
            )
        )
        PhoneInfoManager.instance.phoneInfo.insertDeptItem(
            PhoneDepartItem(
                PhoneInfoManager.instance.phoneInfo.generateDeptId(),
                1, 2, "研发2"
            )
        )
        PhoneInfoManager.instance.phoneInfo.insertDeptItem(
            PhoneDepartItem(
                PhoneInfoManager.instance.phoneInfo.generateDeptId(),
                2, 2, "销售1"
            )
        )
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        super.onResume()
        updateUI()
        adapterDept?.setNewList(mTreeList)
        adapterDept?.notifyDataSetChanged()
    }

    fun updateUI() {
        var tree = DeptTree(0, 0, "root", null, 0);

        if (PhoneInfoManager.instance.phoneInfo.phoneDepartItemList.isNotEmpty()) {
            val filterLevel1List = PhoneInfoManager.instance.phoneInfo.phoneDepartItemList.filter {
                it.level == 1
            }
            LogUtil.d("filterLevel1List = $filterLevel1List")
            if (filterLevel1List.isNotEmpty()) {
                for (phoneDepartItem in filterLevel1List) {
                    var ti = DeptTree(phoneDepartItem.id, phoneDepartItem.pid, phoneDepartItem.name, tree, 1);
                    tree.child.add(ti);
                    val filterLevel2List = PhoneInfoManager.instance.phoneInfo.phoneDepartItemList.filter { it.pid == phoneDepartItem.id }.filter { it.level == 2 }
                    if (filterLevel2List.isNotEmpty()) {
                        for (phoneDepartItem2 in filterLevel2List) {
                            var tii = DeptTree(phoneDepartItem2.id, phoneDepartItem2.pid, phoneDepartItem2.name, ti, 2);
                            ti.child.add(tii);
                        }
//                        for (j in 0 until 10) {
//                            var tii = DeptTree("node$i$j", ti, 2);
//                            ti.child.add(tii);
//                            for (m in 0 until 3){
//                                var tiii = DeptTree("node$i$j$m", tii, 3);
//                                tii.child.add(tiii)
//                            }
//                        }
                    }

                }
                mTreeList.clear()
                mTreeList.addAll(tree.child)

            }

        }
    }

    private fun showBottomShootDialog() {
        val findFragmentByTag =
            childFragmentManager.findFragmentByTag(TAG_BOTTOM_SHOOT_DIALOG) as DeptBottomDialog?
        findFragmentByTag?.dismiss()
        val beginTransaction = childFragmentManager.beginTransaction()
        val currentTree = adapterDept?.currentDept
        var phoneDepartItem:PhoneDepartItem? = null
        currentTree?.also {
                phoneDepartItem = PhoneDepartItem(it.id,it.pid,it.level,it.name)
            }
        beginTransaction.add(DeptBottomDialog.newInstance(phoneDepartItem), TAG_BOTTOM_SHOOT_DIALOG)
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