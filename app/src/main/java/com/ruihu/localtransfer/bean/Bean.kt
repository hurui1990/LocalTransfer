package com.ruihu.localtransfer.bean

import android.graphics.Bitmap
import android.net.Uri

/**
 *  Created By RuiHu At 2020/9/15 14:54
 */
data class User(
    var name: String,
    var position: Int,
    var avater: String = ""
)

data class PhotoInfo(
    val type: String,
    val path: String,
    val name: String,
    val size: String,
    val thumbPath: String = "",
    val duration: String = "",
    val title : String = "",
    val singer : String = "",
    val album : String = ""
)