package com.ruihu.rh_kit.util

import android.content.Context

/**
 *  Created By RuiHu At 2020/9/14 16:33
 */

fun Context.getVersionName():String{
    var packageManager = packageManager
    var packageInfo = packageManager.getPackageInfo(packageName,0)
    return packageInfo.versionName
}