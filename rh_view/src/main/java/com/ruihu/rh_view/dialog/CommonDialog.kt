package com.ruihu.rh_view.dialog

import android.app.Dialog
import android.content.Context
import android.util.DisplayMetrics
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.ruihu.rh_view.R
import org.jetbrains.anko.windowManager

/**
 *  Created By RuiHu At 2020/9/16 18:13
 */
class CommonDialog : Dialog {

    constructor(context: Context) : super(context)
    constructor(context: Context,themeResId: Int) : super(context,themeResId)

    class Builder {

        private var context : Context
        private var dialog : CommonDialog

        constructor(context: Context){
            this.context = context
            dialog = CommonDialog(context, R.style.myDialogTheme)
        }

        fun setContentView(view:View): Builder{
            dialog.addContentView(view,ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT))
            return this
        }

        fun create() : CommonDialog{
            val layoutParams: WindowManager.LayoutParams = dialog?.window?.attributes!!
            layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT
            //add bykaifeng.lu for defect340954 begin
            val dbMetrics = DisplayMetrics()
            context.windowManager.defaultDisplay.getMetrics(dbMetrics)
            val screenWidth = dbMetrics.widthPixels
            layoutParams.width = screenWidth - 200
            //add bykaifeng.lu for defect340954 end
            layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
            dialog?.window!!.attributes = layoutParams

            dialog.setCanceledOnTouchOutside(true)

            return dialog
        }
    }

}