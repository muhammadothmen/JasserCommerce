package com.othman.jassercommerce.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class TabsPagerAdapter (activity: FragmentActivity) : FragmentStateAdapter(activity) {
    private val mFragmentList  = ArrayList<Fragment>()
    private val mFragmentTitleList = ArrayList<String>()

    fun getTabTitle(position: Int): String{
        return mFragmentTitleList[position]
    }

    fun addFragment(fragment: Fragment, title: String) {
        mFragmentList.add(fragment)
        mFragmentTitleList.add(title)
    }

    override fun getItemCount() = 3

    override fun createFragment(position: Int): Fragment {
        return mFragmentList[position]
    }
}