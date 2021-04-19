package com.kronos.sample

import android.view.View
import android.widget.ImageView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView

/**
 * @Author LiABao
 * @Since 2020/10/15
 */
class Adapter(itemView: View) : RecyclerView.ViewHolder(itemView) {
    init {
        itemView.findViewById<ImageView>(R.id.tv1)
    }

}