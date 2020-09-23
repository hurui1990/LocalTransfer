package com.ruihu.localtransfer.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentTransaction

/**
 *  Created By RuiHu At 2020/9/21 15:12
 */
class WiFiTransferFragmentPagerAdapter(var fm: FragmentManager,var fragmentList: ArrayList<Fragment>) : FragmentPagerAdapter(fm) {


    override fun getCount(): Int {
        return fragmentList.size
    }

    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getItemPosition(`object`: Any): Int {
        return super.getItemPosition(`object`)
    }

    fun setFragments(fragments: ArrayList<Fragment>) {
        if (fragmentList != null) {
            var ft: FragmentTransaction? = fm.beginTransaction()
            for (f in fragmentList) {
                ft!!.remove(f)
            }
            ft!!.commitAllowingStateLoss()
            ft = null
            fm.executePendingTransactions()
        }
        fragmentList = fragments
        notifyDataSetChanged()
    }
}