package com.phone.book.fragment.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.phone.book.R
import com.phone.book.activity.MainActivity
import com.phone.book.activity.TestAwakeActivity
import com.phone.book.bean.PhoneBookItem

import com.phone.book.common.BaseFragment
import com.phone.book.common.listener.OnSingleClickListener
import com.phone.book.manager.PhoneInfoManager
import kotlinx.android.synthetic.main.fragment_seek_call.*


const val TAG_SEEK_CALL_FRAGMENT = "SeekLogFragment"

class SeekCallFragment : BaseFragment() {

    private var mMainActivity: MainActivity? = null
    private var deptPhoneListAdapter: DeptPhoneListAdapter? = null
    private var mPhoneList: ArrayList<PhoneBookItem> = ArrayList<PhoneBookItem>();
    override val layoutId: Int
        get() = R.layout.fragment_seek_call

    companion object {
        @JvmStatic
        fun newInstance() =
            SeekCallFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }


    override fun initViews() {
        eif_seek_call_simple_find.edit_info_box_value?.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
        recyclerview_seek_call_list.layoutManager = GridLayoutManager(context, 2)
        deptPhoneListAdapter = DeptPhoneListAdapter(mPhoneList)
        recyclerview_seek_call_list.adapter = deptPhoneListAdapter

        bt_seek_call_find1.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(v: View) {
                if (eif_seek_call_simple_find.desc.isEmpty()) {
                    toastMsg("请输入查找内容")
                    return
                }
                val foundBySimpleNameList = PhoneInfoManager.instance.phoneInfo.foundPhoneBySimpleNameOrNum(eif_seek_call_simple_find.desc)
                mPhoneList.clear()
                mPhoneList.addAll(foundBySimpleNameList)
                deptPhoneListAdapter?.setList(foundBySimpleNameList)
                deptPhoneListAdapter?.notifyDataSetChanged()
            }
        })

        bt_seek_call_find2.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(v: View) {
                startBt2Find()
            }
        })

        bt_test_tune.setOnClickListener(object :OnSingleClickListener(){
            override fun onSingleClick(v: View) {
                activity?.startActivity(Intent(activity,TestAwakeActivity::class.java))
            }
        })
    }

    private fun startBt2Find(){
        if (eif_seek_call_key1_find.desc.isEmpty() && eif_seek_call_key2_find.desc.isEmpty()) {
            toastMsg("请至少输入一项查找内容")
            return
        }
        if (eif_seek_call_key1_find.desc.isNotEmpty() && eif_seek_call_key2_find.desc.isNotEmpty()) {
            //desc1 和 desc2同时满足
            val foundPhoneByTwoString = PhoneInfoManager.instance.phoneInfo.foundPhoneByTwoString(eif_seek_call_key1_find.desc, eif_seek_call_key2_find.desc)
            mPhoneList.clear()
            mPhoneList.addAll(foundPhoneByTwoString)
            deptPhoneListAdapter?.setList(foundPhoneByTwoString)
        } else if (eif_seek_call_key1_find.desc.isEmpty() && eif_seek_call_key2_find.desc.isNotEmpty()) {
            val foundPhoneBySimpleNameOrNum = PhoneInfoManager.instance.phoneInfo.foundPhoneBySimpleNameOrNum(eif_seek_call_key2_find.desc)
            mPhoneList.clear()
            mPhoneList.addAll(foundPhoneBySimpleNameOrNum)
            deptPhoneListAdapter?.setList(foundPhoneBySimpleNameOrNum)
        } else if (eif_seek_call_key1_find.desc.isNotEmpty() && eif_seek_call_key2_find.desc.isEmpty()) {
            val foundPhoneBySimpleNameOrNum = PhoneInfoManager.instance.phoneInfo.foundPhoneBySimpleNameOrNum(eif_seek_call_key1_find.desc)
            mPhoneList.clear()
            mPhoneList.addAll(foundPhoneBySimpleNameOrNum)
            deptPhoneListAdapter?.setList(foundPhoneBySimpleNameOrNum)
        }
        deptPhoneListAdapter?.notifyDataSetChanged()
    }

    class DeptPhoneListAdapter : RecyclerView.Adapter<DeptPhoneListAdapter.ViewHolder> {
        private var mList: ArrayList<PhoneBookItem> = arrayListOf()
        private var listener: OnItemClickListener? = null

        constructor(list: List<PhoneBookItem>) {
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
            holder.tvNumber.text = if (phoneBookItem.extension1.isNotEmpty()) phoneBookItem.extension1 else if (phoneBookItem.extension2.isNotEmpty()) phoneBookItem.extension2 else ""
            holder.itemView.setOnClickListener(object : OnSingleClickListener() {
                override fun onSingleClick(v: View) {
                    listener?.onItemClick(phoneBookItem)
                }

            })
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

        interface OnItemClickListener {
            fun onItemClick(phoneItem: PhoneBookItem)
        }

        fun setOnItemClickListener(clickListener: OnItemClickListener) {
            this.listener = clickListener
        }

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