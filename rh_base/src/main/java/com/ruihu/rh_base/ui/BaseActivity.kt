package com.ruihu.rh_base.ui

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.PermissionChecker
import com.ruihu.rh_base.util.PermissionUtil
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast


/**
 *  Created By RuiHu At 2020/9/10 11:20
 */
abstract class BaseActivity : AppCompatActivity() {

    private var isExit: Boolean = false

    protected val permissionMap  by lazy { hashMapOf(
        "com.ruihu.localtransfer.ui.activity.CreateOrModifyUserActivity" to arrayOf(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.CAMERA
        ),
        "com.ruihu.localtransfer.ui.activity.MainActivity" to arrayOf(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        ),
    ) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fullScreen()
        setContentView(getLayoutId())
        initView()
        initData()
    }

    abstract fun getLayoutId() : Int

    protected open fun fullScreen(){
        if (Build.VERSION.SDK_INT >= 28){
            // 延伸显示区域到刘海
            val lp: WindowManager.LayoutParams = window.attributes
            lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
            window.attributes = lp
            // 设置页面全屏显示
            val decorView: View = window.decorView
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        }
        if (Build.VERSION.SDK_INT >= 21) {
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
            window.statusBarColor = Color.TRANSPARENT
        }
    }

    protected open fun initView(){
        actionBar?.hide()
    }

    protected open fun initData(){}

    protected fun myToast(str: String){
        runOnUiThread { toast(str) }
    }

    protected inline fun <reified T : BaseActivity> startActivityAndFinish(){
        startActivity<T>()
        finish()
    }

    protected fun hideTitleBar(){
        window.requestFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }

    fun exitBy2Click() {
        val handler = Handler()
        if ((!isExit)) {
            isExit = true
            myToast("再按一次退出APP")
            handler.postDelayed({ isExit = false }, 1000 * 2) //x秒后没按就取消
        } else {
            finish()
            System.exit(0)
        }
    }
}