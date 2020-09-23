package com.ruihu.localtransfer.ui.fragment

import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.ruihu.localtransfer.R
import com.ruihu.rh_base.ui.BaseFragment
import kotlinx.android.synthetic.main.fragment_photo_layout.*

/**
 *  Created By RuiHu At 2020/9/18 11:02
 */
class PhotoFragment : BaseFragment() {

    override fun initView(): View? {
        return View.inflate(context, R.layout.fragment_photo_layout, null)
    }

    override fun initData() {
        super.initData()
        photoView.layoutManager = GridLayoutManager(activity, 3)

    }

}