package com.ruihu.rh_base.widget

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.appcompat.widget.Toolbar
import com.ruihu.rh_base.R
import kotlinx.android.synthetic.main.layout_common_bar.view.*
import org.jetbrains.anko.imageResource
import org.jetbrains.anko.layoutInflater

/**
 *  Created By RuiHu At 2020/9/11 11:40
 */

class CommonToolbar : Toolbar{

    private val commonBarTitle : TextView

    constructor(context: Context) : this(context,null)

    constructor(context: Context, attrs: AttributeSet?) : this(context,attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ){
        var view = context.layoutInflater.inflate(R.layout.layout_common_bar,null,true)
        commonBarTitle= view.findViewById(R.id.common_bar_title)
        addView(view)
    }

    fun setTitle(title:String,gra:Int = Gravity.CENTER){
        commonBarTitle.apply {
            text = title
            paint.isFakeBoldText = true
            gravity = gra
        }
    }

    fun setLeftBack(@IdRes imageResourceId:Int,click:OnClickListener){
        common_bar_left_img.apply {
            visibility = VISIBLE
            imageResource = imageResourceId
            setOnClickListener(click)
        }
    }

    fun setRightImg(@IdRes imageResourceId: Int,click: OnClickListener){
        common_bar_right_img.apply {
            visibility = VISIBLE
            imageResource = imageResourceId
            setOnClickListener(click)
        }
    }

}