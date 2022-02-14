package com.phone.book.fragment

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import com.derry.serialportlibrary.T
import com.phone.book.R
import com.phone.book.activity.DialingInitActivity
import com.phone.book.common.dialog.BaseDialogFragment
import com.phone.book.common.listener.OnSingleClickListener

const val TAG_DIALING_INIT_FRAGMENT = "DialingInitFragment"

class DialingInitFragment : BaseDialogFragment() {
    private var mActivity: DialingInitActivity? = null
    private lateinit var bt_dialing_out: Button

    companion object {
        @JvmStatic
        fun newInstance(phoneNum: String) = DialingInitFragment()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)
        val dialog = AlertDialog.Builder(requireContext(), R.style.commonDialogCustom).create()
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
        dialog.setContentView(R.layout.fragment_dialing_init)
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        //for soft input could show
        dialog.window?.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        initViews(dialog)
        return dialog
    }


    fun initViews(dialog: AlertDialog) {
        Log.w(T.TAG, "DialingInitFragment initViews")

        initToolbar()
        bt_dialing_out = dialog.findViewById(R.id.bt_dialing_init_go_out)!!
        bt_dialing_out.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(v: View) {
                mActivity?.finish()
            }
        })

        initData()
    }

    private fun initData() {

    }


    private fun initToolbar() {
        mActivity?.hideLogo()
//        setToolbarTitle("拨号", true)
    }

    override fun onDestroy() {
        super.onDestroy()
        mActivity?.finish()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is DialingInitActivity) {
            mActivity = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        mActivity = null
    }
}