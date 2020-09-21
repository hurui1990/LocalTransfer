package com.ruihu.rh_kit.extend

import android.widget.TextView

/**
 *  Created By RuiHu At 2020/9/14 16:15
 */

fun TextView.bold(isBold:Boolean) = this.apply { paint.isFakeBoldText = isBold }