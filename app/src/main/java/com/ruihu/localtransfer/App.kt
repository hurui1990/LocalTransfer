package com.ruihu.localtransfer

import android.app.Application
import android.os.Build
import android.os.StrictMode
import android.os.StrictMode.VmPolicy
import kotlin.properties.Delegates


/**
 *  Created By RuiHu At 2020/9/15 16:37
 */

class App : Application() {

    companion object {
        var instance : App by Delegates.notNull()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val builder = VmPolicy.Builder()
            StrictMode.setVmPolicy(builder.build())
        }
    }

}