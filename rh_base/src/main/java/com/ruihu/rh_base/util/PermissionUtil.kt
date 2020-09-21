package com.ruihu.rh_base.util

import android.app.Activity
import android.content.Context
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker

object PermissionUtil {

    const val CHECK_REQUEST_PERMISSION_RESULT = 3

    fun hasPermission(context: Context, permissions: Array<String>) : Boolean{
        if (permissions == null || permissions.isEmpty()){
            return true
        }
        for (per in permissions){
            if (ContextCompat.checkSelfPermission(context, per) != PermissionChecker.PERMISSION_GRANTED){
                return false
            }
        }
        return true
    }

    fun requestPermissions(activity: Activity, permissions: Array<String>, requestCode: Int){
        ActivityCompat.requestPermissions(activity, permissions, requestCode)
    }

}