package com.phone.book.fragment.home

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.GridLayoutManager
import com.phone.book.R
import com.phone.book.activity.MainActivity
import com.phone.book.common.BaseFragment
import com.phone.book.common.adapter.CustomBaseAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.phone.book.GridItemDivider
import com.phone.book.activity.EditInfoContainerActivity
import com.phone.book.adapter.SingleSelectAdapter2
import com.phone.book.bean.HunLetterBean
import com.phone.book.bean.PhoneBookItem
import com.phone.book.common.listener.OnSingleClickListener
import com.phone.book.manager.PhoneInfoManager
import com.phone.book.utils.PinyinUtils
import kotlinx.android.synthetic.main.fragment_hun_family.*

const val TAG_HUN_FAMILY_FRAGMENT = "HunFamilyFragment"

class HunFamilyFragment : BaseFragment() {

    private var mMainActivity: MainActivity? = null
    private var mList: ArrayList<HunLetterBean> = arrayListOf()
    private var mLetterNameAdapter: LetterNameAdapter? = null
    private var mLetterNameMap: HashMap<String, LetterNameInnerAdapter> = hashMapOf()
    private var mPhoneList: ArrayList<PhoneBookItem> = arrayListOf()
    private var mPhoneListAdapter: PhoneListAdapter? = null

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

        recyclerview_letter_hun_family_detail.layoutManager = GridLayoutManager(context, 2)
        mPhoneListAdapter = PhoneListAdapter(R.layout.adapter_dept_phone_item, mPhoneList)
        recyclerview_letter_hun_family_detail.adapter = mPhoneListAdapter
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            mList.clear()
            mList.addAll(PhoneInfoManager.instance.phoneInfo.getAllLetterNameList())
            mLetterNameAdapter?.notifyDataSetChanged()
            mPhoneList.clear()
            mPhoneListAdapter?.notifyDataSetChanged()
        }
    }

    inner class LetterNameAdapter(layoutId: Int, list: ArrayList<HunLetterBean>) : CustomBaseAdapter<HunLetterBean, LetterNameAdapter.LetterNameHolder>(layoutId, list) {
        override fun onCreateCustomViewHolder(view: View): LetterNameHolder {
            return LetterNameHolder(view)
        }

        override fun onBindCustomViewHolder(holder: LetterNameHolder, hunLetterBean: HunLetterBean) {
            val lastNameList = if (hunLetterBean.lastNameList?.isNullOrEmpty() == true) ArrayList<String>() else hunLetterBean.lastNameList
            lastNameList?.let {
                holder.letter.text = hunLetterBean.letter
                var adapter = LetterNameInnerAdapter(R.layout.adapter_item_hun_letter_adapter_last_name, it)

                mLetterNameMap[hunLetterBean.letter] = adapter

                adapter?.setStateDrawables(
                    AppCompatResources.getDrawable(requireContext(), R.drawable.shape_last_name_select),
                    AppCompatResources.getDrawable(requireContext(), R.drawable.shape_last_name_unselect)
                )
                val gridItemDivider = GridItemDivider(resources.getDimensionPixelSize(R.dimen.x5), Color.TRANSPARENT)
                gridItemDivider.setTopEnable(false)
                gridItemDivider.setBottomEnable(false)
                gridItemDivider.setLeftEnable(false)
                gridItemDivider.setRightEnable(false)
                holder.nameRecyclerView.layoutManager = GridLayoutManager(context, 8)
                holder.nameRecyclerView.addItemDecoration(gridItemDivider)
                holder.nameRecyclerView.adapter = adapter
                adapter.setOnItemSelectedListener(object : SingleSelectAdapter2.OnItemSelectedListener<String> {
                    override fun onItemSelected(view: View?, lastName: String, position: Int) {
                        val pingYin = PinyinUtils.getPingYin(lastName)
                        if (pingYin.isNotEmpty()) {
                            for (mutableEntry in mLetterNameMap) {
                                if (!mutableEntry.key.equals(hunLetterBean.letter, ignoreCase = true)) {
                                    //refresh select bg of other adapter
                                    mutableEntry.value.selectItem(-1)
                                }
                            }
                        }
                        adapter.selectItem(position)

                        val phoneListByFirstLetter = PhoneInfoManager.instance.phoneInfo.getPhoneListByLastName(lastName)
                        mPhoneList.clear()
                        mPhoneList.addAll(phoneListByFirstLetter)
                        mPhoneListAdapter?.notifyDataSetChanged()
                    }
                })
            }
        }

        inner class LetterNameHolder(itemView: View) : CustomBaseHolder(itemView) {
            var letter: TextView = itemView.findViewById<TextView>(R.id.tv_hun_letter)
            var nameRecyclerView = itemView.findViewById<RecyclerView>(R.id.adapter_item_letter_name_recyclerview)
        }
    }

    inner class LetterNameInnerAdapter(layoutId: Int, list: ArrayList<String>) : SingleSelectAdapter2<String, LetterNameInnerAdapter.LetterNameInnerHolder>(layoutId, list) {
        override fun onCreateCustomViewHolder(view: View): LetterNameInnerHolder {
            return LetterNameInnerHolder(view)
        }

        override fun onBindCustomViewHolder(holder: LetterNameInnerHolder, name: String) {
            holder.name.text = name
        }

        inner class LetterNameInnerHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var name: TextView = itemView.findViewById<TextView>(R.id.adapter_tv_hun_item_inner_name)
        }
    }

    inner class PhoneListAdapter(layoutId: Int, list: ArrayList<PhoneBookItem>) : CustomBaseAdapter<PhoneBookItem, PhoneListAdapter.PhoneListHolder>(layoutId, list) {
        override fun onCreateCustomViewHolder(view: View): PhoneListHolder {
            return PhoneListHolder(view)
        }

        override fun onBindCustomViewHolder(holder: PhoneListHolder, phoneBookItem: PhoneBookItem) {
            holder.tvName.text = phoneBookItem.name
            holder.tvNumber.text = if (phoneBookItem.extension1.isNotEmpty()) phoneBookItem.extension1 else if (phoneBookItem.extension2.isNotEmpty()) phoneBookItem.extension2 else ""
            holder.itemView.setOnClickListener(object : OnSingleClickListener() {
                override fun onSingleClick(v: View) {
                    EditInfoContainerActivity.startPhoneInfoPage(mMainActivity, phoneBookItem)
                }

            })
        }

        inner class PhoneListHolder(itemView: View) : CustomBaseHolder(itemView) {
           var tvName = itemView.findViewById<TextView>(R.id.adapter_dept_phone_item_name)
           var tvNumber = itemView.findViewById<TextView>(R.id.adapter_dept_phone_item_number)
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