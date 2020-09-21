package com.ruihu.localtransfer.adapter

import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter

/**
 *  Created By RuiHu At 2020/9/14 17:42
 */
class WelcomePageAdapter constructor(var viewList: List<View>) : PagerAdapter() {


    override fun getCount() = viewList.size

    override fun isViewFromObject(view: View, `object`: Any) = view == (`object` as View)

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        container.addView(viewList[position],0)
        return viewList[position]
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}