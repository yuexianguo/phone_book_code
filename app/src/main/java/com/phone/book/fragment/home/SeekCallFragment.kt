package com.phone.book.fragment.home

import android.content.Context
import android.os.Bundle
import com.phone.book.R
import com.phone.book.activity.MainActivity

import com.phone.book.common.BaseFragment


const val TAG_SEEK_CALL_FRAGMENT = "SeekLogFragment"

class SeekCallFragment : BaseFragment() {

    private var mMainActivity: MainActivity? = null

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