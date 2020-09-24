package com.ruihu.localtransfer.ui.activity

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.ContentUris
import android.content.Intent
import android.database.Cursor
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.PermissionChecker
import androidx.recyclerview.widget.GridLayoutManager
import com.google.gson.Gson
import com.ruihu.localtransfer.R
import com.ruihu.localtransfer.`interface`.OnAdapterClickListener
import com.ruihu.localtransfer.adapter.ChooseAvaterAdapter
import com.ruihu.localtransfer.bean.User
import com.ruihu.rh_base.util.Constants
import com.ruihu.localtransfer.util.SharePreferenceDelegate
import com.ruihu.rh_base.ui.BaseActivity
import com.ruihu.rh_base.util.PermissionUtil
import com.ruihu.rh_base.util.TransferShare
import com.ruihu.rh_kit.extend.fromJson
import com.ruihu.rh_kit.extend.toJson
import com.ruihu.rh_kit.extend.toOvalBitmap
import com.ruihu.rh_view.dialog.CommonDialog
import kotlinx.android.synthetic.main.activity_create_or_modify_user.*
import kotlinx.android.synthetic.main.toolbar.*
import org.jetbrains.anko.imageResource
import java.io.File


/**
 *  Created By RuiHu At 2020/9/15 10:13
 */

class CreateOrModifyUserActivity : BaseActivity(), View.OnClickListener{

    private var mPosition = -1
    private var avaterStr  = ""
    private val userInfoIcons by lazy { TransferShare.userInfoIcons }
    private var userSP by SharePreferenceDelegate("user", "")
    private var dialog : Dialog? = null
    private var photoFile : File? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_create_or_modify_user
    }

    @SuppressLint("ResourceType")
    override fun initView() {
        super.initView()
        toolbar.apply {

            setTitle("创建角色", Gravity.LEFT or Gravity.CENTER_VERTICAL)

            setLeftBack(R.drawable.ic_back) { finish() }

            setRightImg(R.drawable.ic_check) { saveUser() }
        }

        take_photo.setOnClickListener(this)

        if (!PermissionUtil.hasPermission(this, permissions)){
            PermissionUtil.requestPermissions(
                this,
                permissions,
                PermissionUtil.CHECK_REQUEST_PERMISSION_RESULT
            )
        }
    }

    override fun initData() {
        super.initData()

        choose_avater_listview.layoutManager = GridLayoutManager(this, 3)
        val chooseAvaterAdapter = ChooseAvaterAdapter(userInfoIcons)
        choose_avater_listview.adapter = chooseAvaterAdapter

        chooseAvaterAdapter.setOnClickListener(object : OnAdapterClickListener {

            override fun onClick(view: View, position: Int) {
                avater_select_img.imageResource = userInfoIcons[position]
                chooseAvaterAdapter.setSelectBg(position)
                mPosition = position
                avaterStr = ""
            }

        })

        if (userSP.isNotEmpty()){
            toolbar.setTitle("编辑角色", Gravity.LEFT or Gravity.CENTER_VERTICAL)
            val user = Gson().fromJson<User>(userSP)
            with(user){
                mPosition = position
                avaterStr = avater
                if (position in 0..8){
                    avater_select_img.imageResource = userInfoIcons[position]
                }else if (position == 9){
                    var bitmap = BitmapFactory.decodeFile(avater)
                    bitmap = bitmap.toOvalBitmap()
                    avater_select_img.setImageBitmap(bitmap)
                }
                set_user_name.setText(name)
                chooseAvaterAdapter.setSelectBg(position)
            }
        }

    }

    /**
     * 点击事件
     * */
    override fun onClick(v: View?) {
        when(v?.id){
            R.id.take_photo -> {
                showTakePhotoDlg()
            }
            R.id.from_photo -> {
                takePhotoFromCamera()
            }
            R.id.from_gallery -> {
                takePhotoFromGallery()
            }
            else -> {}
        }
    }

    /**
     * 调取系统相机拍照并返回照片
     * */
    private fun takePhotoFromCamera(){
        dialog?.dismiss()
        if (!PermissionUtil.hasPermission(this, permissions)){
            PermissionUtil.requestPermissions(
                this,
                permissions,
                PermissionUtil.CHECK_REQUEST_PERMISSION_RESULT
            )
            return
        }
        val path = Environment.getExternalStorageDirectory().path+"/Local/local_transfer_${System.currentTimeMillis()}.jpg"
        photoFile = File(path)
        var uriForFile = Uri.fromFile(photoFile)
        var intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uriForFile)
        startActivityForResult(intent, Constants.REQUEST_CODE_FOR_CAMERA)
    }

    private fun takePhotoFromGallery(){
        dialog?.dismiss()
        if (!PermissionUtil.hasPermission(this, permissions)){
            PermissionUtil.requestPermissions(
                this,
                permissions,
                PermissionUtil.CHECK_REQUEST_PERMISSION_RESULT
            )
            return
        }
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, Constants.REQUEST_CODE_FOR_GALLERY)
    }

    private fun handleImageOmKitKat(data: Intent) {
        var imagePath: String? = null
        val uri = data.data
        if (DocumentsContract.isDocumentUri(this, uri)) {
            //如果document类型是U日，则通过document id处理
            val docId = DocumentsContract.getDocumentId(uri)
            if ("com.android.providers.media.documents" == uri!!.authority) {
                val id = docId.split(":".toRegex()).toTypedArray()[1] //解析出数字格式id
                val selection = MediaStore.Images.Media._ID + "=" + id
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection)
            } else if ("com.android.providers.downloads.documents" == uri.authority) {
                val contentUri = ContentUris.withAppendedId(
                    Uri.parse("content://downloads/public_downloads"),
                    java.lang.Long.valueOf(docId)
                )
                imagePath = getImagePath(contentUri, null)
            }
        } else if ("content".equals(uri!!.scheme, ignoreCase = true)) {
            //如果是普通类型 用普通方法处理
            imagePath = getImagePath(uri, null)
        } else if ("file".equals(uri.scheme, ignoreCase = true)) {
            //如果file类型位uri直街获取图片路径即可
            imagePath = uri.path
        }
        displayImage(imagePath)
    }

    private fun getImagePath(uri: Uri, selection: String?): String? {
        var path: String? = null
        //通过Uri和selection来获取真实图片路径
        val cursor: Cursor? = contentResolver.query(uri, null, selection, null, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA))
            }
            cursor.close()
        }
        return path
    }

    private fun displayImage(imagePath: String?) {
        avaterStr = imagePath!!
        if (imagePath != null) {
            val bitmap = BitmapFactory.decodeFile(imagePath)
            avater_select_img.setImageBitmap(bitmap.toOvalBitmap())
        } else {
            Toast.makeText(this, "fail to get image", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * 展示Dialog
     * */
    private fun showTakePhotoDlg(){
        if (dialog != null && dialog!!.isShowing){
            return
        }
        if (dialog == null){
            var view = LayoutInflater.from(this).inflate(R.layout.take_photo_layout, null)

            val fromPhoto : TextView = view.findViewById(R.id.from_photo)
            val fromGallery : TextView = view.findViewById(R.id.from_gallery)
            fromPhoto.setOnClickListener(this)
            fromGallery.setOnClickListener(this)

            dialog = CommonDialog.Builder(this)
                .setContentView(view)
                .create()
        }
        dialog!!.show()
    }

    /**
     * 保存用户信息
     * */
    private fun saveUser(){
        var name = set_user_name.text.toString()
        if (name.isEmpty()){
            myToast("请设置昵称")
            return
        }
        if (mPosition == -1){
            myToast("请设置头像")
            return
        }
        var user = User(name, mPosition)
        with(user) {
            if (mPosition in 0..8){
                avater = ""
            }else if(mPosition == 9){
                avater = avaterStr
            }
        }
        val toJson = user.toJson()
        userSP = toJson
        if (userSP.isNotEmpty()) {
            myToast("保存成功")
            startActivityAndFinish<MainActivity>()
        }

    }

    /**
     * 拍照及选取图片的返回值
     * */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != -1){
            return
        }
        when(requestCode){
            Constants.REQUEST_CODE_FOR_CAMERA -> {
                val path = photoFile!!.absolutePath
                mPosition = 9
                avaterStr = path
                var bitmap = BitmapFactory.decodeFile(path)
                bitmap = bitmap.toOvalBitmap()
                avater_select_img.setImageBitmap(bitmap)
            }
            Constants.REQUEST_CODE_FOR_GALLERY -> {
                mPosition = 9
                handleImageOmKitKat(data!!)
            }
        }
    }

    /**
     * 对权限的处理
     * */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (PermissionUtil.CHECK_REQUEST_PERMISSION_RESULT === requestCode) {
            for (permission in permissions) {
                if (PermissionChecker.checkSelfPermission(this, permission) != PermissionChecker.PERMISSION_GRANTED) {
                    if (Build.VERSION.SDK_INT >= 23) {
                        myToast("请设置相应权限")
                        return
                    }
                }
            }
        }
    }
}