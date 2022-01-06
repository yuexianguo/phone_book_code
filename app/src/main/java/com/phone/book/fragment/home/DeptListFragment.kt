package com.phone.book.fragment.home

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.phone.book.PhoneIntents
import com.phone.book.R
import com.phone.book.activity.MainActivity
import com.phone.book.adapter.DeptTreeAdapter
import com.phone.book.bean.DeptTree
import com.phone.book.bean.PhoneBookItem
import com.phone.book.bean.PhoneDepartItem
import com.phone.book.common.BaseFragment
import com.phone.book.common.adapter.CustomBaseAdapter2
import com.phone.book.common.listener.OnSingleClickListener
import com.phone.book.common.utils.LogUtil
import com.phone.book.dialog.DeptBottomDialog
import com.phone.book.manager.PhoneInfoManager
import kotlinx.android.synthetic.main.fragment_dept_list.*
import java.util.*
import kotlin.collections.ArrayList


const val TAG_DEPT_LIST_FRAGMENT = "DeptListFragment"

class DeptListFragment : BaseFragment() {
    private val TAG_BOTTOM_SHOOT_DIALOG = "tag_bottom_shoot_dialog"
    private var mMainActivity: MainActivity? = null
    private var adapterDept: DeptTreeAdapter? = null
    private var deptPhoneListAdapter: DeptPhoneListAdapter? = null
    private var mTreeList: ArrayList<DeptTree> = ArrayList<DeptTree>();
    private var mDeptPhoneList: ArrayList<PhoneBookItem> = ArrayList<PhoneBookItem>();
    private var isDeptUpdate: Boolean = false
    private var handler = Handler(Looper.getMainLooper())
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

    private var mReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            intent?.let {
                LogUtil.d("DeptListFragment, mReceiver action = ${it.action}")
                when (it.action) {
                    PhoneIntents.ACTION_MODIFY_DEPT_SUCCESS -> {
                        isDeptUpdate = true
                        updateDeptListUI()
                    }
                    PhoneIntents.ACTION_MODIFY_CALL_CARD_SUCCESS -> {
                        val currentDept = adapterDept?.currentDept
                        mMainActivity?.apply {
                            currentDept?.apply {
                                val filter = PhoneInfoManager.instance.phoneInfo.phoneList.filter { it.department.id == this.id }
                                mDeptPhoneList.clear()
                                mDeptPhoneList.addAll(filter)
                                deptPhoneListAdapter?.setList(filter)
                                handler.post(Runnable {
                                    deptPhoneListAdapter?.notifyDataSetChanged()
                                })
                            }

                        }

                    }
                    else -> {
                    }
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context?.let {
            val intentFilter = IntentFilter()
            intentFilter.addAction(PhoneIntents.ACTION_MODIFY_DEPT_SUCCESS)
            intentFilter.addAction(PhoneIntents.ACTION_MODIFY_CALL_CARD_SUCCESS)
            LocalBroadcastManager.getInstance(it).registerReceiver(mReceiver, intentFilter)
        }
    }


    override fun initViews() {
        bt_edit_input_tune.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(v: View) {
                showBottomShootDialog()
            }
        })
        recyclerview_dept_list.layoutManager = LinearLayoutManager(context)
        adapterDept = DeptTreeAdapter(context, mTreeList)
        recyclerview_dept_list.adapter = adapterDept

        recyclerview_dept_list_detail.layoutManager = GridLayoutManager(context,2)
        deptPhoneListAdapter = DeptPhoneListAdapter(mDeptPhoneList)
        recyclerview_dept_list_detail.adapter = deptPhoneListAdapter

        adapterDept?.setDeptOnItemSelectListener(object : DeptTreeAdapter.OnDeptItemSelectListener{
            override fun onItemSelect(phoneDepartItem: PhoneDepartItem?) {
                phoneDepartItem?.apply {
                    val filter = PhoneInfoManager.instance.phoneInfo.phoneList.filter { it.department.id == this.id }
                    mDeptPhoneList.clear()
                    mDeptPhoneList.addAll(filter)
                    deptPhoneListAdapter?.setList(filter)
                    handler.post(Runnable {
                        deptPhoneListAdapter?.notifyDataSetChanged()
                    })

                }

            }

        })

        updateDeptListUI()

    }


    class DeptPhoneListAdapter : RecyclerView.Adapter<DeptPhoneListAdapter.ViewHolder> {
        private var mList:ArrayList<PhoneBookItem> = arrayListOf()

        constructor(list: List<PhoneBookItem>){
            mList.clear()
            mList.addAll(list)
        }
        override fun getItemCount(): Int {
           return mList.size
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeptPhoneListAdapter.ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_dept_phone_item, parent, false)
            return DeptPhoneListAdapter.ViewHolder(view)
        }

        override fun onBindViewHolder(holder: DeptPhoneListAdapter.ViewHolder, position: Int) {
            var phoneBookItem = mList[position]
            holder.tvName.text = phoneBookItem.name
            holder.tvNumber.text = if (phoneBookItem.extension1.isNotEmpty()) phoneBookItem.extension1 else if (phoneBookItem.extension2.isNotEmpty())phoneBookItem.extension2 else ""
        }

        fun setList(mDeptPhoneList: List<PhoneBookItem>) {
            mList.clear()
            mList.addAll(mDeptPhoneList)
        }

        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val tvName: TextView
            val tvNumber: TextView

            init {
                tvName = itemView.findViewById(R.id.adapter_dept_phone_item_name)
                tvNumber = itemView.findViewById(R.id.adapter_dept_phone_item_number)
            }
        }




    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        super.onResume()
        if (isDeptUpdate) {
            adapterDept?.notifyDataSetChanged()
            isDeptUpdate = false
        }
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
                adapterDept?.setNewList(mTreeList)
                adapterDept?.notifyDataSetChanged()
            }

        }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {

        }
    }

    private fun showBottomShootDialog() {
        val findFragmentByTag =
            childFragmentManager.findFragmentByTag(TAG_BOTTOM_SHOOT_DIALOG) as DeptBottomDialog?
        findFragmentByTag?.dismiss()
        val beginTransaction = childFragmentManager.beginTransaction()
        val currentTree = adapterDept?.currentDept
        var phoneDepartItem: PhoneDepartItem? = null
        currentTree?.also {
            phoneDepartItem = PhoneDepartItem(it.id, it.pid, it.level, it.name)
        }
        adapterDept?.setOriginDeptPosition()
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

    override fun onDestroy() {
        super.onDestroy()
        context?.let {
            LocalBroadcastManager.getInstance(it).unregisterReceiver(mReceiver)
        }
        handler.removeCallbacksAndMessages(null)
    }

}