package com.ruihu.localtransfer.`interface`

import android.view.View

/**
 *  Created By RuiHu At 2020/9/15 13:32
 */
interface OnAdapterClickListener {

    fun onClick(view:View, position:Int){}

    fun onLongClick(view:View, position:Int){}
}