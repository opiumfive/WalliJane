package com.iterika.walli.presentation

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

class RecyclerItemDecorator(private val topOffset: Int = 0, private val bottomOffset: Int = 0, private val leftOffset: Int = 0, private val rightOffset: Int = 0) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State?) {
        outRect.set(leftOffset, topOffset, rightOffset, bottomOffset)
    }
}
