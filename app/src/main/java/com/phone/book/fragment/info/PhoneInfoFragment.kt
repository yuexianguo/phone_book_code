package com.phone.book.fragment.info

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import com.phone.book.R
import com.phone.book.activity.EditInfoContainerActivity
import com.phone.book.bean.PhoneBookItem
import com.phone.book.bean.TYPE_MAN
import com.phone.book.common.BaseFragment
import com.phone.book.common.adapter.CustomBaseAdapter
import com.phone.book.manager.PhoneInfoManager
import kotlinx.android.synthetic.main.fragment_phone_info.*



const val TAG_PHONE_INFO_FRAGMENT = "PhoneInfoFragment"
const val TAG_TARGET_PHONE_ITEM = "TAG_TARGET_PHONE_ITEM"

class PhoneInfoFragment : BaseFragment() {

    private var mEditInfoContainerActivity: EditInfoContainerActivity? = null
    private var phoneItem: PhoneBookItem? = null
    private var phoneInfoAdapter: PhoneInfoAdapter? = null

    private var itemsValueList: ArrayList<String> = arrayListOf()

    private var itemsNameList: ArrayList<String> = arrayListOf("分机 ：", "分机 ：", "手机 ：", "手机 ：", "电话 ：", "电话 ：", "宅电 ：", "传真 ：", "邮件 ：", "备注 ：")


    override val layoutId: Int
        get() = R.layout.fragment_phone_info

    companion object {
        @JvmStatic
        fun newInstance(phoneItem: PhoneBookItem?) =
            PhoneInfoFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(TAG_TARGET_PHONE_ITEM, phoneItem)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.also {
            val serializableExtra = it.getSerializable(TAG_TARGET_PHONE_ITEM)
            phoneItem = if (serializableExtra != null) serializableExtra as PhoneBookItem else null
            phoneItem?.also { phoneItem ->
                itemsValueList.clear()
                itemsValueList.addAll(
                    arrayListOf(
                        phoneItem.extension1,
                        phoneItem.extension2,
                        phoneItem.phone1,
                        phoneItem.phone2,phoneItem.call1,phoneItem.call2,
                        phoneItem.home_phone,
                        phoneItem.fax,
                        phoneItem.email,
                        phoneItem.remarks
                    )
                )
            }
        }
    }


    override fun initViews() {
        initToolbar()
        phone_info_recyclerView.layoutManager = GridLayoutManager(context, 2)
        phoneInfoAdapter = PhoneInfoAdapter(R.layout.adapter_item_phone_info, itemsNameList)
        phone_info_recyclerView.adapter = phoneInfoAdapter

        phoneItem?.also {
            if (it.sex == TYPE_MAN) {
                iv_phone_info_head.setImageDrawable(resources.getDrawable(R.mipmap.icon_man))
            } else {
                iv_phone_info_head.setImageDrawable(resources.getDrawable(R.mipmap.icon_woman))
            }
            val department = it.department
            var deptName = department.name
            var deptpid = department.pid
            while ( deptpid!= 0L) {
                val phoneDepartItem = PhoneInfoManager.instance.phoneInfo.getPhoneDepartItem(deptpid)
                deptName = if (phoneDepartItem!=null) phoneDepartItem.name +"/"+deptName else deptName
                deptpid = if (phoneDepartItem != null) phoneDepartItem.pid else 0L
            }
            phone_info_dept_name.text = deptName
            phone_info_person_name.text = it.name
            phone_info_work.text = it.work
        }

        phoneInfoAdapter?.notifyDataSetChanged()

    }
    private fun initToolbar() {
        mEditInfoContainerActivity?.hideLogo()
        setToolbarTitle("名片信息", true)
    }
    override fun lazyFetchData() {
    }

    inner class PhoneInfoAdapter(layoutId: Int, list: ArrayList<String>) : CustomBaseAdapter<String, PhoneInfoAdapter.PhoneInfoHolder>(layoutId, list) {
        override fun onCreateCustomViewHolder(view: View): PhoneInfoHolder {
            return PhoneInfoHolder(view)
        }

        override fun onBindCustomViewHolder(holder: PhoneInfoHolder, value: String) {
            holder.tvName.text = value
            val desc = itemsValueList[holder.adapterPosition]
            holder.tvDesc.text = if (desc.isEmpty()) "" else desc
        }

        inner class PhoneInfoHolder(itemView: View) : CustomBaseHolder(itemView) {
            val tvName: TextView = itemView.findViewById(R.id.phone_info_item_title)
            val tvDesc: TextView = itemView.findViewById(R.id.phone_info_item_desc)

        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is EditInfoContainerActivity) {
            mEditInfoContainerActivity = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        mEditInfoContainerActivity = null
    }

}