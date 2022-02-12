package com.phone.book.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.derry.serialportlibrary.T
import com.phone.book.R
import com.phone.book.activity.DialingActivity
import com.phone.book.common.BaseFragment
import com.phone.book.common.utils.LogUtil
import com.phone.book.jobservice.BackJobService

private const val ARG_PARAM1 = "param1"

const val TAG_DIALING_FRAGMENT = "tag_dialing_fragment"
class DialingFragment : BaseFragment() {
    private var mActivity: DialingActivity? = null
    private var param1: String? = null
    override val layoutId: Int
        get() = R.layout.fragment_dialing

    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
            DialingFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }

    override fun lazyFetchData() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
    }

    override fun initViews() {
        Log.w(T.TAG, "DialingFragment initViews")
        initToolbar()
    }

    private fun initToolbar() {
        mActivity?.hideLogo()
        setToolbarTitle("拨号", true)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is DialingActivity) {
            mActivity = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        mActivity = null
    }
}