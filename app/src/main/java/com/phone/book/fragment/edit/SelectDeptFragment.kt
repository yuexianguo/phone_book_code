package com.phone.book.fragment.edit

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.phone.book.R
import com.phone.book.activity.EditInfoContainerActivity
import com.phone.book.adapter.DeptTreeAdapter
import com.phone.book.bean.DeptTree
import com.phone.book.common.BaseFragment
import com.phone.book.common.listener.OnSingleClickListener
import com.phone.book.common.utils.LogUtil
import com.phone.book.manager.PhoneInfoManager
import kotlinx.android.synthetic.main.fragment_select_dept.*
import java.util.ArrayList


const val TAG_SELECT_DEPT_FRAGMENT = "SelectDeptFragment"
const val EXTRA_SELECT_DEPT = "extra_select_dept"

class SelectDeptFragment : BaseFragment() {

    private var mActivity: EditInfoContainerActivity? = null
    private var mAdapter: DeptTreeAdapter? = null
    private var mTreeList: ArrayList<DeptTree> = ArrayList<DeptTree>();

    override val layoutId: Int
        get() = R.layout.fragment_select_dept

    companion object {
        @JvmStatic
        fun newInstance() = SelectDeptFragment()
    }


    override fun initViews() {
        initToolbar()
        select_dept_recyclerView.layoutManager = LinearLayoutManager(context)
        mAdapter = DeptTreeAdapter(context, mTreeList)
        select_dept_recyclerView.adapter = mAdapter


        select_dept_save.setOnClickListener(object :OnSingleClickListener(){
            override fun onSingleClick(v: View) {
                if (mAdapter?.selectPhoneDepartItem == null) {
                    toastMsg("至少需要选择一个部门")
                    return
                }
                mActivity?.setResult(Activity.RESULT_OK, Intent().apply { putExtra(EXTRA_SELECT_DEPT,mAdapter?.selectPhoneDepartItem) })
                mActivity?.onBackPressed()
            }
        })
        select_dept_cancel.setOnClickListener(object :OnSingleClickListener(){
            override fun onSingleClick(v: View) {
                mActivity?.onBackPressed()
            }
        })
    }

    override fun onResume() {
        super.onResume()
        updateDeptListUI()
    }
    
    private fun initToolbar() {
        mActivity?.hideLogo()
        setToolbarTitle("请选择部门",true)
    }

    fun updateDeptListUI() {
        var tree = DeptTree(0, 0, "root", null, 0, false);

        if (PhoneInfoManager.instance.phoneInfo.phoneDepartItemList.isNotEmpty()) {
            val filterLevel1List = PhoneInfoManager.instance.phoneInfo.phoneDepartItemList.filter { it.level == 1 }
            LogUtil.d("filterLevel1List = $filterLevel1List")
            if (filterLevel1List.isNotEmpty()) {
                for (phoneDepartItem in filterLevel1List) {
                    var ti = DeptTree(phoneDepartItem.id, phoneDepartItem.pid, phoneDepartItem.name, tree, 1, false);
                    tree.child.add(ti);
                    val filterLevel2List = PhoneInfoManager.instance.phoneInfo.phoneDepartItemList.filter { it.pid == phoneDepartItem.id }.filter { it.level == 2 }
                    if (filterLevel2List.isNotEmpty()) {
                        for (phoneDepartItem2 in filterLevel2List) {
                            var tii = DeptTree(phoneDepartItem2.id, phoneDepartItem2.pid, phoneDepartItem2.name, ti, 2, false);
                            ti.child.add(tii);
                            val filterLevel3List = PhoneInfoManager.instance.phoneInfo.phoneDepartItemList.filter { it.pid == phoneDepartItem2.id }.filter { it.level == 3 }
                            if (filterLevel3List.isNotEmpty()) {
                                for (phoneDepartItem3 in filterLevel3List) {
                                    var ti3 = DeptTree(phoneDepartItem3.id, phoneDepartItem3.pid, phoneDepartItem3.name, tii, 3, false);
                                    tii.child.add(ti3);

                                    val filterLevel4List = PhoneInfoManager.instance.phoneInfo.phoneDepartItemList.filter { it.pid == phoneDepartItem3.id }.filter { it.level == 4 }
                                    if (filterLevel4List.isNotEmpty()) {
                                        for (phoneDepartItem4 in filterLevel4List) {
                                            var ti4 = DeptTree(phoneDepartItem4.id, phoneDepartItem4.pid, phoneDepartItem4.name, ti3, 4, false);
                                            ti3.child.add(ti4);

                                            val filterLevel5List = PhoneInfoManager.instance.phoneInfo.phoneDepartItemList.filter { it.pid == phoneDepartItem4.id }.filter { it.level == 5 }
                                            if (filterLevel5List.isNotEmpty()) {
                                                for (phoneDepartItem5 in filterLevel5List) {
                                                    var ti5 = DeptTree(phoneDepartItem5.id, phoneDepartItem5.pid, phoneDepartItem5.name, ti4, 5, false);
                                                    ti4.child.add(ti5);

                                                    val filterLevel6List = PhoneInfoManager.instance.phoneInfo.phoneDepartItemList.filter { it.pid == phoneDepartItem5.id }.filter { it.level == 6 }
                                                    if (filterLevel6List.isNotEmpty()) {
                                                        for (phoneDepartItem6 in filterLevel6List) {
                                                            var ti6 = DeptTree(phoneDepartItem6.id, phoneDepartItem6.pid, phoneDepartItem6.name, ti5, 6, false);
                                                            ti5.child.add(ti6);
                                                        }
                                                    }

                                                }
                                            }

                                        }
                                    }

                                }
                            }
                        }
                    }

                }
                mTreeList.clear()
                mTreeList.addAll(tree.child)
                mAdapter?.setNewList(mTreeList)
                mAdapter?.notifyDataSetChanged()
            }

        }
        if (mTreeList.isEmpty()) {
            showMsgDialog("当前没部门可选，请新增部门。",null, { dialog, _ -> dialog.dismiss()
                             EditInfoContainerActivity.startAddDeptFragment(requireContext(),null)
                                                 },null)
        }
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