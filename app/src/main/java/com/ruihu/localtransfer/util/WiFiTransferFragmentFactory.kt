package com.ruihu.localtransfer.util

import androidx.fragment.app.Fragment
import com.ruihu.localtransfer.ui.fragment.*
import com.ruihu.rh_base.util.Constants

/**
 *  Created By RuiHu At 2020/9/21 13:58
 */
object WiFiTransferFragmentFactory {

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
        return newFragment
    }
}