package com.example.recyclerviewdemo.recyclerview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class GridItemDecoration(context: Context) : RecyclerView.ItemDecoration() {
    private val mBounds = Rect()
    private var mDivider: Drawable = ColorDrawable(context.resources.getColor(android.R.color.holo_purple))
    private var mDividerWidth = 10

    fun setDivider(divider: Drawable = mDivider, dividerWidth: Int = mDividerWidth) {
        mDivider = divider
        mDividerWidth = dividerWidth
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        if (parent.layoutManager!!.canScrollHorizontally()) {
            drawHorizontal(c, parent)
        } else if (parent.layoutManager!!.canScrollVertically()) {
            drawVertical(c, parent)
        }
    }

    private fun drawHorizontal(c: Canvas, parent: RecyclerView) {
        c.save()
        val count = parent.childCount
        for (i in 0 until count) {
            val child = parent.getChildAt(i)
            parent.getDecoratedBoundsWithMargins(child, mBounds)
            //画竖线
            mDivider.setBounds(mBounds.right - mDividerWidth, mBounds.top, mBounds.right, mBounds.bottom)
            mDivider.draw(c)
            //画横线
            mDivider.setBounds(mBounds.left, mBounds.bottom - mDividerWidth, mBounds.right, mBounds.bottom)
            mDivider.draw(c)
        }
        c.restore()
    }

    private fun drawVertical(c: Canvas, parent: RecyclerView) {
        c.save()
        val count = parent.childCount
        for (i in 0 until count) {
            val child = parent.getChildAt(i)
            parent.getDecoratedBoundsWithMargins(child, mBounds)
            //画横线
            mDivider.setBounds(mBounds.left, mBounds.bottom - mDividerWidth, mBounds.right, mBounds.bottom)
            //画竖线
            mDivider.setBounds(mBounds.right - mDividerWidth, mBounds.top, mBounds.right, mBounds.bottom)
            mDivider.draw(c)
        }
        c.restore()
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.set(0, 0, mDividerWidth, mDividerWidth)
    }
}