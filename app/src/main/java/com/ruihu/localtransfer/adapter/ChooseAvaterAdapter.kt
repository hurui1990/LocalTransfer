package com.ruihu.localtransfer.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.ruihu.localtransfer.R
import com.ruihu.localtransfer.`interface`.OnAdapterClickListener
import org.jetbrains.anko.backgroundResource
import org.jetbrains.anko.imageResource

/**
 *  Created By RuiHu At 2020/9/15 11:28
 */
class ChooseAvaterAdapter(private var icons:IntArray) : RecyclerView.Adapter<ChooseAvaterAdapter.ViewHolder>(){

    private lateinit var onClickListener : OnAdapterClickListener
    private var selectPosition = -1
    private lateinit var context : Context;

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_choose_avater,parent,false)
        var viewHolder = ViewHolder(view)
        view.setOnClickListener {
            onClickListener.onClick(view,viewHolder.layoutPosition)
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            icons[position].let {
                image.imageResource = it
            }

            layout.apply {
                if (position == selectPosition){
                    backgroundResource = R.drawable.circle_blue_bg
                    elevation = 30f
                    animation = AnimationUtils.loadAnimation(context,R.anim.image_select_anim)
                    animation.start()
                }else{
                    backgroundResource = 0
                    elevation = 0f
                }
            }
        }
    }

    override fun getItemCount() = icons.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image: ImageView = itemView.findViewById(R.id.avater_img)
        var layout : LinearLayout = itemView.findViewById(R.id.avater_layout)
    }

    fun setOnClickListener(listener:OnAdapterClickListener){
        onClickListener = listener
    }

    fun setSelectBg(position: Int){
        selectPosition = position
        notifyDataSetChanged()
    }
}
