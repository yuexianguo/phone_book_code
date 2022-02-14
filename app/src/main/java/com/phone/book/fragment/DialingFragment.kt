package com.phone.book.fragment

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.os.Handler
import android.os.Looper
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
import com.phone.book.bean.DIAL_TYPE_DIALED_CALL
import com.phone.book.bean.PhoneBookItem
import com.phone.book.bean.PhoneHistoryItem
import com.phone.book.common.dialog.BaseDialogFragment
import com.phone.book.common.listener.OnSingleClickListener
import com.phone.book.manager.PhoneInfoManager
import com.phone.book.utils.DialTimeUtils
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

    private var mHandler: Handler = Handler(Looper.getMainLooper())
    private var mDialSeconds: Int = 0
    private var mDialMinutes: Int = 0
    private var mDialHours: Int = 0
    private var mStartDialTime: String = ""

    companion object {
        @JvmStatic
        fun newInstance(phoneNum: String?, phoneItem: PhoneBookItem?) =
            DialingFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, phoneNum)
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
        bt_dialing_out.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(v: View) {
                mActivity?.onBackPressed()
            }
        })
    }

    private fun initData() {
//        clearDataForInit()
        mStartDialTime = DialTimeUtils.getCurrentTimeByFormat()
        mHandler.removeCallbacks(mDialTimeRunnable)
        mHandler.postDelayed(mDialTimeRunnable, 1000L)
    }

    private val mDialTimeRunnable: Runnable
        get() = Runnable {
            mDialSeconds++
            if (mDialSeconds >= 60) {
                mDialSeconds = 0
                mDialMinutes++
            }
            if (mDialMinutes >= 60) {
                mDialSeconds = 0
                mDialMinutes = 0
                mDialHours++
            }
            bt_dialing_time_seconds.text = String.format("%02d", mDialSeconds)
            bt_dialing_time_minutes.text = String.format("%02d", mDialMinutes)
            bt_dialing_time_hours.text = String.format("%02d", mDialHours)
            mHandler.postDelayed(mDialTimeRunnable, 1000L)
        }

    private fun initToolbar() {
        mActivity?.hideLogo()
//        setToolbarTitle("拨号", true)
    }

    override fun onDestroy() {
        super.onDestroy()
//        PhoneInfoManager.instance.phoneInfo.insertPhoneHistoryItem(PhoneHistoryItem(PhoneInfoManager.instance.phoneInfo.generatePhoneHistoryItemId(),
//        "", DIAL_TYPE_DIALED_CALL,))
        PhoneInfoManager.instance.phoneInfo.saveOrUpdate(requireContext())
        mHandler.removeCallbacks(mDialTimeRunnable)
        mActivity?.finish()
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