package com.ruihu.localtransfer.data

import android.content.Context
import android.provider.MediaStore
import com.ruihu.localtransfer.bean.PhotoInfo
import com.ruihu.rh_base.util.Constants
import java.io.File
import java.util.*

/**
 *  Created By RuiHu At 2020/9/22 18:08
 */
object ImageAlbumUtil {


    private fun setAlbumList(context: Context?): HashMap<String, PhotoInfo>? {
        val allPhotosTemp = HashMap<String, PhotoInfo>()//所有照片
        //modify by kaifeng.lu for resolve picture disappear and display no picture end
        if (context != null) {
            val projection = arrayOf(
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.SIZE,
                MediaStore.Images.Media.DISPLAY_NAME
            )
            val where = MediaStore.Images.Media.MIME_TYPE + "=? or " + MediaStore.Images.Media.MIME_TYPE + "=?"
            val mCursor = context.contentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, where, arrayOf(
                    "image/jpeg",
                    "image/png"
                ),
                MediaStore.Images.Media.DATE_MODIFIED + " desc"
            )
            if (mCursor != null) {
                while (mCursor.moveToNext()) {
                    val path : String = mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media.DATA))
                    val size : Int = mCursor.getInt(mCursor.getColumnIndex(MediaStore.Images.Media.SIZE))/1024
                    val displayName : String  = mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME))
                    //用于展示相册初始化界面
                    // 获取该图片的父路径名
                    val dirPath : String = File(path).parentFile.absolutePath
                    //存储对应关系
                    if (!allPhotosTemp.containsKey(dirPath) && dirPath.toUpperCase().endsWith("DCIM/CAMERA")) {
                        val photoInfo = PhotoInfo(Constants.MEDIA_TYPE_IMAGE ,path, displayName, size.toString())
                        allPhotosTemp[path] = photoInfo
                        continue
                    }
                }
                mCursor.close()
            }
        }
        return allPhotosTemp
    }

    fun getAlbumList(context: Context?): HashMap<String,PhotoInfo>? {
        return setAlbumList(context)
    }
}