package com.ruihu.localtransfer.util

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.ruihu.localtransfer.ui.fragment.*

/**
 *  Created By RuiHu At 2020/9/21 13:58
 */
object WiFiTransferFragmentFactory {

    // private static Object mutex = new Object();
    fun newInstance(string: String): Fragment? {
        var newFragment: Fragment? = null
        when (string) {
            Constants.WIFI_TRANSFER_PHOTO_PAGE_LAYOUT -> {
                newFragment = PhotoFragment()
            }
            Constants.WIFI_TRANSFER_PICTURE_PAGE_LAYOUT -> {
                newFragment = PictureFragment()
            }
            Constants.WIFI_TRANSFER_MUSIC_PAGE_LAYOUT -> {
                newFragment = MusicFragment()
            }
            Constants.WIFI_TRANSFER_VIDEO_PAGE_LAYOUT -> {
                newFragment = VideoFragment()
            }
            Constants.WIFI_TRANSFER_HISTORY_PAGE_LAYOUT -> {
                newFragment = HistoryFragment()
            }
        }
        val bundle = Bundle()
        bundle.putString("param", string)
        newFragment!!.arguments = bundle
        return newFragment
    }
}