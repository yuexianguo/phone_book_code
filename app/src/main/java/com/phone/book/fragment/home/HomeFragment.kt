package com.phone.book.fragment.home

import android.Manifest
import android.content.*
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentTransaction
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.android.material.tabs.TabLayout
import com.phone.book.R
import com.phone.book.activity.MainActivity
import com.phone.book.common.BaseFragment
import com.phone.book.manager.PhoneInfoManager
import kotlinx.android.synthetic.main.fragment_home.*


const val TAG_HOME_FRAGMENT = "HomeFragment"

class HomeFragment : BaseFragment() {

    private var mMainActivity: MainActivity? = null
    private var mTabTitles: Array<String>? = null

    private var mDeptListFragment: DeptListFragment? = null
    private var mRecentContactsFragment: RecentContactsFragment? = null
    private var mHunFamilyFragment: HunFamilyFragment? = null
    private var mSeekLogFragment: SeekLogFragment? = null
    private var mCurrentIndex = 0

    private var handler: Handler = Handler(Looper.getMainLooper())


    companion object {
        private val REQUES_READ_WRITE_CODE = 0x01
        private const val TAG = "HomeFragment"

        private const val KEY_CURRENT_SELECTED_POSITION = "key_current_selected_position"

        @JvmStatic
        fun newInstance() = HomeFragment()
    }

    override val layoutId: Int
        get() = R.layout.fragment_home


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mCurrentIndex = 0
        savedInstanceState?.let {
            mCurrentIndex = it.getInt(KEY_CURRENT_SELECTED_POSITION)
            mDeptListFragment = childFragmentManager.findFragmentByTag(TAG_DEPT_LIST_FRAGMENT) as DeptListFragment?
            mRecentContactsFragment = childFragmentManager.findFragmentByTag(TAG_RECENT_CONTACTS_FRAGMENT) as RecentContactsFragment?
            mHunFamilyFragment = childFragmentManager.findFragmentByTag(TAG_HUN_FAMILY_FRAGMENT) as HunFamilyFragment?
            mSeekLogFragment = childFragmentManager.findFragmentByTag(TAG_SEEK_CALL_FRAGMENT) as SeekLogFragment?
        }

        mTabTitles = arrayOf("部门列表", "最近联系人", "百家姓", "查找")
    }

    override fun initViews() {
        mTabTitles?.forEach {
            tab_home.addTab(tab_home.newTab().setText(it))
        }
        tab_home.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {
                selectedIndex(p0?.position ?: 0)
            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {
            }

            override fun onTabSelected(p0: TabLayout.Tab?) {
                selectedIndex(p0?.position ?: 0)
            }
        })

        tab_home.getTabAt(mCurrentIndex)?.select()
        selectedIndex(mCurrentIndex)
        mMainActivity?.hideAppbarToolbar()

//        PhoneFileUtils.copyPrivateToDocuments(BaseApplication.context, "Myphone.txt", PhoneBookInfo("研发部","13111111").toString());
        checkPermisson()
    }

    private fun checkPermisson() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            this.requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE), REQUES_READ_WRITE_CODE)
        } else {
            PhoneInfoManager.instance.updatePhoneInfo()
            handler.postDelayed(Runnable {
                mDeptListFragment?.refreshInit()
            }, 1000L)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUES_READ_WRITE_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mMainActivity?.apply {
                    PhoneInfoManager.instance.updatePhoneInfo()
                    handler.postDelayed(Runnable {
                        mDeptListFragment?.refreshInit()
                    }, 1000L)
                }
            } else {
                showMsgDialog("请打开APP的存储权限。", null, { dialog, _ -> dialog.dismiss() }, null)
            }
        }

    }


    override fun onResume() {
        super.onResume()

    }

    override fun lazyFetchData() {
    }


    fun selectedIndex(index: Int) {
        mCurrentIndex = index
        val transaction: FragmentTransaction = childFragmentManager.beginTransaction()
        when (index) {
            0 -> {
                if (mDeptListFragment == null) {
                    mDeptListFragment = DeptListFragment.newInstance()
                    transaction.add(R.id.fl_home_container, mDeptListFragment!!, TAG_DEPT_LIST_FRAGMENT)
                }
                transaction.show(mDeptListFragment!!)
                mRecentContactsFragment?.also { transaction.hide(it) }
                mHunFamilyFragment?.also { transaction.hide(it) }
                mSeekLogFragment?.also { transaction.hide(it) }
            }
            1 -> {
                if (mRecentContactsFragment == null) {
                    mRecentContactsFragment = RecentContactsFragment.newInstance()
                    transaction.add(R.id.fl_home_container, mRecentContactsFragment!!, TAG_RECENT_CONTACTS_FRAGMENT)
                }
                transaction.show(mRecentContactsFragment!!)
                mDeptListFragment?.also { transaction.hide(it) }
                mHunFamilyFragment?.also { transaction.hide(it) }
                mSeekLogFragment?.also { transaction.hide(it) }
            }
            2 -> {
                if (mHunFamilyFragment == null) {
                    mHunFamilyFragment = HunFamilyFragment.newInstance()
                    transaction.add(R.id.fl_home_container, mHunFamilyFragment!!, TAG_HUN_FAMILY_FRAGMENT)
                }
                transaction.show(mHunFamilyFragment!!)
                mDeptListFragment?.also { transaction.hide(it) }
                mRecentContactsFragment?.also { transaction.hide(it) }
                mSeekLogFragment?.also { transaction.hide(it) }
            }
            3 -> {
                if (mSeekLogFragment == null) {
                    mSeekLogFragment = SeekLogFragment.newInstance()
                    transaction.add(R.id.fl_home_container, mSeekLogFragment!!, TAG_SEEK_CALL_FRAGMENT)
                }
                transaction.show(mSeekLogFragment!!)
                mDeptListFragment?.also { transaction.hide(it) }
                mRecentContactsFragment?.also { transaction.hide(it) }
                mHunFamilyFragment?.also { transaction.hide(it) }
            }
        }
        transaction.commit()
    }


    override fun onPause() {
        super.onPause()
        mCurrentIndex = tab_home.selectedTabPosition
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_CURRENT_SELECTED_POSITION, mCurrentIndex)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainActivity) {
            mMainActivity = context
        }
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDetach() {
        super.onDetach()
        mMainActivity = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
    }

}
