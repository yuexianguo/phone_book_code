package com.phone.book.fragment

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.derry.serialportlibrary.T
import com.phone.book.R
import com.phone.book.activity.DialingActivity
import com.phone.book.common.dialog.BaseDialogFragment
import com.phone.book.common.listener.OnSingleClickListener
import kotlinx.android.synthetic.main.fragment_dialing.*

private const val ARG_PARAM1 = "param1"

const val TAG_DIALING_FRAGMENT = "tag_dialing_fragment"
class DialingFragment : BaseDialogFragment() {
    private var mActivity: DialingActivity? = null
    private var param1: String? = null
    private lateinit var tv_dialing_dept_name: TextView
    private lateinit var tv_dialing_person_name: TextView
    private lateinit var tv_dialing_work: TextView
    private lateinit var et_dialing_phone_number: TextView
    private lateinit var tv_dialing_address_desc: TextView
    private lateinit var tv_dialing_remark_desc: TextView
    private lateinit var bt_dialing_time_hours: Button
    private lateinit var bt_dialing_time_minutes: Button
    private lateinit var bt_dialing_time_seconds: Button
    private lateinit var bt_dialing_record_hours: Button
    private lateinit var bt_dialing_record_minutes: Button
    private lateinit var bt_dialing_record_seconds: Button
    private lateinit var bt_dialing_out: Button

    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
            DialingFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)
        val dialog = AlertDialog.Builder(requireContext(), R.style.commonDialogCustom).create()
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
        dialog.setContentView(R.layout.fragment_dialing)
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        //for soft input could show
        dialog.window?.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        initViews(dialog)
        return dialog
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            Log.w(T.TAG, "DialingFragment param1=$param1")
        }
    }


    private fun clearDataForInit() {
        tv_dialing_dept_name.text = ""
        tv_dialing_person_name.text = ""
        tv_dialing_work.text = ""
        et_dialing_phone_number.setText("")
        tv_dialing_address_desc.text = ""
        tv_dialing_remark_desc.text = ""
        bt_dialing_time_hours.text = ""
        bt_dialing_time_minutes.text = ""
        bt_dialing_time_seconds.text = ""
        bt_dialing_record_hours.text = ""
        bt_dialing_record_minutes.text = ""
        bt_dialing_record_seconds.text = ""
    }

    fun initViews(dialog: AlertDialog) {
        tv_dialing_dept_name = dialog.findViewById(R.id.tv_dialing_dept_name)!!
        tv_dialing_person_name = dialog.findViewById(R.id.tv_dialing_person_name)!!
        tv_dialing_work = dialog.findViewById(R.id.tv_dialing_work)!!
        et_dialing_phone_number = dialog.findViewById(R.id.et_dialing_phone_number)!!
        tv_dialing_address_desc = dialog.findViewById(R.id.tv_dialing_address_desc)!!
        tv_dialing_remark_desc = dialog.findViewById(R.id.tv_dialing_remark_desc)!!
        bt_dialing_time_hours = dialog.findViewById(R.id.bt_dialing_time_hours)!!
        bt_dialing_time_minutes = dialog.findViewById(R.id.bt_dialing_time_minutes)!!
        bt_dialing_time_seconds = dialog.findViewById(R.id.bt_dialing_time_seconds)!!
        bt_dialing_record_hours = dialog.findViewById(R.id.bt_dialing_record_hours)!!
        bt_dialing_record_minutes = dialog.findViewById(R.id.bt_dialing_record_minutes)!!
        bt_dialing_record_seconds = dialog.findViewById(R.id.bt_dialing_record_seconds)!!
        bt_dialing_out = dialog.findViewById(R.id.bt_dialing_out)!!


        Log.w(T.TAG, "DialingFragment initViews")
        initToolbar()
        initData()
        bt_dialing_out.setOnClickListener(object :OnSingleClickListener(){
            override fun onSingleClick(v: View) {
                mActivity?.onBackPressed()
            }
        })
    }

    private fun initData() {
        clearDataForInit()

    }

    private fun initToolbar() {
        mActivity?.hideLogo()
//        setToolbarTitle("拨号", true)
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