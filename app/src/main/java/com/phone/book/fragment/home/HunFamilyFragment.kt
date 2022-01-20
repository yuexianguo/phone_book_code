package com.phone.book.fragment.home

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.phone.book.R
import com.phone.book.activity.MainActivity
import com.phone.book.common.BaseFragment
import com.phone.book.common.adapter.CustomBaseAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.phone.book.bean.LetterNameBean
import com.phone.book.manager.PhoneInfoManager
import kotlinx.android.synthetic.main.fragment_hun_family.*

const val TAG_HUN_FAMILY_FRAGMENT = "HunFamilyFragment"

class HunFamilyFragment : BaseFragment() {

    private var mMainActivity: MainActivity? = null
    private var mList: ArrayList<LetterNameBean> = arrayListOf()
    private var mLetterNameAdapter: LetterNameAdapter? = null

    override val layoutId: Int
        get() = R.layout.fragment_hun_family

    companion object {
        @JvmStatic
        fun newInstance() =
            HunFamilyFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }


    override fun initViews() {
        recyclerview_letter_hun_family_list.layoutManager = LinearLayoutManager(context)
        mList.clear()
        mList.addAll(PhoneInfoManager.instance.phoneInfo.getAllLetterNameList())
        mLetterNameAdapter = LetterNameAdapter(R.layout.adapter_item_letter_name, mList)
        recyclerview_letter_hun_family_list.adapter = mLetterNameAdapter
    }

    inner class LetterNameAdapter(layoutId: Int, list: ArrayList<LetterNameBean>) : CustomBaseAdapter<LetterNameBean, LetterNameAdapter.LetterNameHolder>(layoutId, list) {
        override fun onCreateCustomViewHolder(view: View): LetterNameHolder {
            return LetterNameHolder(view)
        }

        override fun onBindCustomViewHolder(holder: LetterNameHolder, letterNameBean: LetterNameBean) {
            holder.letter.text = letterNameBean.letter
            holder.lastName.text = letterNameBean.lastName
        }

        inner class LetterNameHolder(itemView: View) : CustomBaseHolder(itemView) {
            var letter: TextView = itemView.findViewById<TextView>(R.id.tv_hun_letter)
            var lastName: TextView = itemView.findViewById<TextView>(R.id.tv_hun_letter_last_name)
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