package com.phone.book.fragment.info

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.phone.book.R
import com.phone.book.activity.EditInfoContainerActivity
import com.phone.book.bean.PhoneBookItem
import com.phone.book.common.BaseFragment
import com.phone.book.common.adapter.CustomBaseAdapter
import kotlinx.android.synthetic.main.fragment_phone_info.*


const val TAG_PHONE_INFO_FRAGMENT = "PhoneInfoFragment"
const val TAG_TARGET_PHONE_ITEM = "TAG_TARGET_PHONE_ITEM"
class PhoneInfoFragment : BaseFragment() {

    private var mEditInfoContainerActivity: EditInfoContainerActivity? = null
    private var phoneItem: PhoneBookItem? = null
    private var phoneList: ArrayList<PhoneBookItem>? = arrayListOf()
    private var phoneInfoAdapter: PhoneInfoAdapter?= null
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
        }
    }


    override fun initViews() {
        phone_info_recyclerView.layoutManager = GridLayoutManager(context,2)
        phoneInfoAdapter = PhoneInfoAdapter(R.layout.adapter_item_phone_info, phoneList)
        phone_info_recyclerView.adapter = phoneInfoAdapter
    }

    override fun lazyFetchData() {
    }

    inner class PhoneInfoAdapter(layoutId: Int, list: ArrayList<PhoneBookItem>?) : CustomBaseAdapter<PhoneBookItem, PhoneInfoAdapter.PhoneInfoHolder>(layoutId, list) {
        override fun onCreateCustomViewHolder(view: View): PhoneInfoHolder {
            return PhoneInfoHolder(view)
        }

        override fun onBindCustomViewHolder(vh: PhoneInfoHolder, t: PhoneBookItem?) {

        }

        inner class PhoneInfoHolder(itemView: View):CustomBaseHolder(itemView){

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