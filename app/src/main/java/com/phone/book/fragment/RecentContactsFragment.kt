package com.phone.book.fragment

import android.content.Context
import android.os.Bundle
import com.phone.book.R
import com.phone.book.activity.MainActivity
import com.phone.book.common.BaseFragment


const val TAG_RECENT_CONTACTS_FRAGMENT = "RecentContactsFragment"

class RecentContactsFragment : BaseFragment() {

    private var mMainActivity: MainActivity? = null

    override val layoutId: Int
        get() = R.layout.fragment_dept_list

    companion object {
        @JvmStatic
        fun newInstance() =
            RecentContactsFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }


    override fun initViews() {
        
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