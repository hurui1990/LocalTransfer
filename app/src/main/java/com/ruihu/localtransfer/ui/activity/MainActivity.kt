package com.ruihu.localtransfer.ui.activity

import android.view.KeyEvent
import android.view.View
import android.view.animation.AnimationUtils
import androidx.annotation.IdRes
import com.google.gson.Gson
import com.ruihu.localtransfer.R
import com.ruihu.localtransfer.bean.User
import com.ruihu.localtransfer.util.SharePreferenceDelegate
import com.ruihu.rh_base.ui.BaseActivity
import com.ruihu.rh_kit.extend.fromJson
import com.ruihu.rh_kit.extend.toOvalBitmap
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.imageResource

class MainActivity : BaseActivity(), View.OnClickListener {

    private var userSp by SharePreferenceDelegate("user","")
    private val imageViews by lazy { arrayListOf(indicator_camera,indicator_picture,indicator_video,indicator_audio,indicator_history) }

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
                indicator_camera.imageResource = R.mipmap.indicator_camera_white
                indicator_picture.imageResource = R.mipmap.indicator_picture
                indicator_audio.imageResource = R.mipmap.indicator_audio
                indicator_video.imageResource = R.mipmap.indicator_video
                indicator_history.imageResource = R.mipmap.indicator_history
            }
            R.id.indicator_picture -> {
                indicator_camera.imageResource = R.mipmap.indicator_camera
                indicator_picture.imageResource = R.mipmap.indicator_picture_white
                indicator_audio.imageResource = R.mipmap.indicator_audio
                indicator_video.imageResource = R.mipmap.indicator_video
                indicator_history.imageResource = R.mipmap.indicator_history
            }
            R.id.indicator_video -> {
                indicator_camera.imageResource = R.mipmap.indicator_camera
                indicator_picture.imageResource = R.mipmap.indicator_picture
                indicator_audio.imageResource = R.mipmap.indicator_audio
                indicator_video.imageResource = R.mipmap.indicator_video_white
                indicator_history.imageResource = R.mipmap.indicator_history
            }
            R.id.indicator_audio -> {
                indicator_camera.imageResource = R.mipmap.indicator_camera
                indicator_picture.imageResource = R.mipmap.indicator_picture
                indicator_audio.imageResource = R.mipmap.indicator_audio_white
                indicator_video.imageResource = R.mipmap.indicator_video
                indicator_history.imageResource = R.mipmap.indicator_history
            }
            R.id.indicator_history -> {
                indicator_camera.imageResource = R.mipmap.indicator_camera
                indicator_picture.imageResource = R.mipmap.indicator_picture
                indicator_audio.imageResource = R.mipmap.indicator_audio
                indicator_video.imageResource = R.mipmap.indicator_video
                indicator_history.imageResource = R.mipmap.indicator_history_white
            }
        }
    }

    private fun initUserInfo(){
        var user = Gson().fromJson<User>(userSp)
        with(user){
            if (position in 0..8){
                main_avater_img.imageResource = com.ruihu.rh_base.util.TransferShare.userInfoIcons[position]
            }else if (position == 9){
                var bitmap = android.graphics.BitmapFactory.decodeFile(avater)
                bitmap = bitmap.toOvalBitmap()
                main_avater_img.setImageBitmap(bitmap)
            }
            main_nick_name.text = name
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            exitBy2Click()
        }
        return false
    }
}