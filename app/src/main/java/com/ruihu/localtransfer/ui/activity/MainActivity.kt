package com.ruihu.localtransfer.ui.activity

import android.graphics.BitmapFactory
import android.view.KeyEvent
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.gson.Gson
import com.ruihu.localtransfer.R
import com.ruihu.localtransfer.adapter.WiFiTransferFragmentPagerAdapter
import com.ruihu.localtransfer.bean.User
import com.ruihu.localtransfer.util.Constants
import com.ruihu.localtransfer.util.SharePreferenceDelegate
import com.ruihu.localtransfer.util.WiFiTransferFragmentFactory
import com.ruihu.rh_base.ui.BaseActivity
import com.ruihu.rh_kit.extend.fromJson
import com.ruihu.rh_kit.extend.toOvalBitmap
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.cameraManager
import org.jetbrains.anko.imageResource

class MainActivity : BaseActivity(), View.OnClickListener {

    private var userSp by SharePreferenceDelegate("user", "")
    private val imageViews by lazy {
        arrayListOf(
            R.id.indicator_camera,
            R.id.indicator_picture,
            R.id.indicator_video,
            R.id.indicator_audio,
            R.id.indicator_history
        )
    }
    private lateinit var fragmentList : ArrayList<Fragment>
    private var photoFragment: Fragment? = null
    private var pictureFragment: Fragment? = null
    private var videoFragment: Fragment? = null
    private var musicFragment: Fragment? = null
    private var historyFragment: Fragment? = null
    private var wifiTransferPagerAdapter : WiFiTransferFragmentPagerAdapter? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initView() {
        super.initView()
        user_info_layout.setOnClickListener(this)
        indicator_camera.setOnClickListener(this)
        indicator_picture.setOnClickListener(this)
        indicator_video.setOnClickListener(this)
        indicator_audio.setOnClickListener(this)
        indicator_history.setOnClickListener(this)
        initViewPager()
    }

    override fun initData() {
        super.initData()
        initUserInfo()
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.user_info_layout -> {
                startActivityAndFinish<CreateOrModifyUserActivity>()
            }
            R.id.indicator_camera -> {
                selectPage(0)
                viewPager.currentItem = 0
            }
            R.id.indicator_picture -> {
                selectPage(1)
                viewPager.currentItem = 1
            }
            R.id.indicator_video -> {
                selectPage(2)
                viewPager.currentItem = 2
            }
            R.id.indicator_audio -> {
                selectPage(3)
                viewPager.currentItem = 3
            }
            R.id.indicator_history -> {
                selectPage(4)
                viewPager.currentItem = 4
            }
        }
    }

    private fun initUserInfo(){
        var user = Gson().fromJson<User>(userSp)
        with(user){
            if (position in 0..8){
                main_avater_img.imageResource = com.ruihu.rh_base.util.TransferShare.userInfoIcons[position]
            }else if (position == 9){
                var bitmap = BitmapFactory.decodeFile(avater)
                bitmap = bitmap.toOvalBitmap()
                main_avater_img.setImageBitmap(bitmap)
            }
            main_nick_name.text = name
        }
    }

    private fun initViewPager(){
        fragmentList = ArrayList()
        photoFragment = WiFiTransferFragmentFactory.newInstance(Constants.WIFI_TRANSFER_PHOTO_PAGE_LAYOUT)
        pictureFragment = WiFiTransferFragmentFactory.newInstance(Constants.WIFI_TRANSFER_PICTURE_PAGE_LAYOUT)
        musicFragment = WiFiTransferFragmentFactory.newInstance(Constants.WIFI_TRANSFER_MUSIC_PAGE_LAYOUT)
        videoFragment = WiFiTransferFragmentFactory.newInstance(Constants.WIFI_TRANSFER_VIDEO_PAGE_LAYOUT)
        historyFragment = WiFiTransferFragmentFactory.newInstance(Constants.WIFI_TRANSFER_HISTORY_PAGE_LAYOUT)
        fragmentList.apply {
            add(photoFragment!!)
            add(pictureFragment!!)
            add(musicFragment!!)
            add(videoFragment!!)
            add(historyFragment!!)
        }
        wifiTransferPagerAdapter = WiFiTransferFragmentPagerAdapter(supportFragmentManager,fragmentList)
        viewPager.adapter = wifiTransferPagerAdapter
        viewPager.currentItem = 0
        viewPager.setOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                selectPage(position)
            }

            override fun onPageScrollStateChanged(state: Int) {

            }

        })
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            exitBy2Click()
        }
        return false
    }

    private fun selectPage(position : Int){
        when(position){
            0 -> {
                indicator_camera.imageResource = R.mipmap.indicator_camera_white
                indicator_picture.imageResource = R.mipmap.indicator_picture
                indicator_audio.imageResource = R.mipmap.indicator_audio
                indicator_video.imageResource = R.mipmap.indicator_video
                indicator_history.imageResource = R.mipmap.indicator_history
            }
            1 -> {
                indicator_camera.imageResource = R.mipmap.indicator_camera
                indicator_picture.imageResource = R.mipmap.indicator_picture_white
                indicator_audio.imageResource = R.mipmap.indicator_audio
                indicator_video.imageResource = R.mipmap.indicator_video
                indicator_history.imageResource = R.mipmap.indicator_history
            }
            2 -> {
                indicator_camera.imageResource = R.mipmap.indicator_camera
                indicator_picture.imageResource = R.mipmap.indicator_picture
                indicator_audio.imageResource = R.mipmap.indicator_audio
                indicator_video.imageResource = R.mipmap.indicator_video_white
                indicator_history.imageResource = R.mipmap.indicator_history
            }
            3 -> {
                indicator_camera.imageResource = R.mipmap.indicator_camera
                indicator_picture.imageResource = R.mipmap.indicator_picture
                indicator_audio.imageResource = R.mipmap.indicator_audio_white
                indicator_video.imageResource = R.mipmap.indicator_video
                indicator_history.imageResource = R.mipmap.indicator_history
            }
            4 -> {
                indicator_camera.imageResource = R.mipmap.indicator_camera
                indicator_picture.imageResource = R.mipmap.indicator_picture
                indicator_audio.imageResource = R.mipmap.indicator_audio
                indicator_video.imageResource = R.mipmap.indicator_video
                indicator_history.imageResource = R.mipmap.indicator_history_white
            }
        }
    }
}