package com.example.ezogreen.otherclasses
import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.annotation.DimenRes
import androidx.recyclerview.widget.RecyclerView

class MarginItemDecoration(private val marginInDp: Int, private val context: Context) : RecyclerView.ItemDecoration() {

    private val margin: Int = context.resources.getDimensionPixelSize(marginInDp)

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.left = margin
        outRect.right = margin
        outRect.top = margin
        outRect.bottom = margin
    }
}