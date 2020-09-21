package com.ruihu.rh_kit.extend

import android.graphics.*

/**
 *  Created By RuiHu At 2020/9/15 09:24
 */

fun Bitmap.toOvalBitmap():Bitmap{
    //获取bmp的宽高 小的一个做为圆的直径r
    //获取bmp的宽高 小的一个做为圆的直径r
    val w: Int = this.width
    val h: Int = this.height
    val r = w.coerceAtMost(h)
    //创建一个paint
    val paint = Paint()
    paint.isAntiAlias = true
    //新创建一个Bitmap对象newBitmap 宽高都是r
    val newBitmap = Bitmap.createBitmap(r, r, Bitmap.Config.ARGB_8888)
    //创建一个使用newBitmap的Canvas对象
    val canvas = Canvas(newBitmap)
    //创建一个BitmapShader对象 使用传递过来的原Bitmap对象bmp
    val bitmapShader = BitmapShader(this, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
    //paint设置shader
    paint.shader = bitmapShader
    //canvas画一个圆 使用设置了shader的paint
    canvas.drawCircle((r / 2).toFloat(), (r / 2).toFloat(), (r / 2).toFloat(), paint)
    return newBitmap
}