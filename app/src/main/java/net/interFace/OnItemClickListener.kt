package net.interFace

import android.view.View

interface OnItemClickListener {

    fun onItemClick(view: View, position: Int, type: Int)
}