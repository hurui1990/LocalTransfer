package com.ruihu.localtransfer.ui.activity

import android.util.Log
import com.ruihu.localtransfer.R
import com.ruihu.localtransfer.util.SharePreferenceDelegate
import com.ruihu.rh_base.ui.BaseActivity
import com.ruihu.rh_kit.util.getVersionName
import kotlinx.android.synthetic.main.activity_launcher.*
import org.jetbrains.anko.custom.async
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.doAsyncResult

class LauncherActivity : BaseActivity() {

    override fun fullScreen() {
        super.fullScreen()
        hideTitleBar()
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_launcher
    }

    override fun initData() {
        super.initData()

        app_version.text = getVersionName()

        doAsync {
            Thread.sleep(2000)
            startActivityAndFinish<WelcomeActivity>()
        }

    }
}