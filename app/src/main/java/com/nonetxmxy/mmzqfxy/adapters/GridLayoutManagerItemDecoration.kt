package com.nonetxmxy.mmzqfxy.adapters

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.SizeUtils

class GridLayoutManagerItemDecoration(
    private val marginHorizontal: Float = 0f
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val layoutManager = parent.layoutManager as? GridLayoutManager ?: return
        if (parent.getChildAdapterPosition(view) % layoutManager.spanCount == 0) {
            outRect.left = 0
            outRect.right = SizeUtils.dp2px(marginHorizontal / 2)
        } else {
            outRect.left = SizeUtils.dp2px(marginHorizontal / 2)
            outRect.right = 0
        }
    }
}