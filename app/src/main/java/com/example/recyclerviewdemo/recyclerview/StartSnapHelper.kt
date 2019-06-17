package com.example.recyclerviewdemo.recyclerview

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView

class StartSnapHelper : LinearSnapHelper() {
    private var mHorizontalHelper: OrientationHelper? = null
    private var mVerticalHelper: OrientationHelper? = null

    override fun calculateDistanceToFinalSnap(layoutManager: RecyclerView.LayoutManager, targetView: View): IntArray? {
        val out = IntArray(2)
        if (layoutManager.canScrollHorizontally()) {
            out[0] = distance2Start(targetView, getHorizontalHelper(layoutManager))
            out[1] = 0
        } else {
            out[0] = 0
            out[1] = distance2Start(targetView, getVerticalHelper(layoutManager))
        }
        return out
    }

    private fun distance2Start(
        targetView: View,
        orientationHelper: OrientationHelper
    ): Int {
        return orientationHelper.getDecoratedStart(targetView) - orientationHelper.startAfterPadding
    }

    override fun findSnapView(layoutManager: RecyclerView.LayoutManager?): View? {
        println("tag=== ${layoutManager?.childCount}")
        if (layoutManager is LinearLayoutManager) {
            return if (layoutManager.canScrollHorizontally()) {
                getStartView(layoutManager, getHorizontalHelper(layoutManager))
            } else {
                getStartView(layoutManager, getVerticalHelper(layoutManager))
            }
        }
        return super.findSnapView(layoutManager)
    }

    private fun getStartView(layoutManager: RecyclerView.LayoutManager, orientationHelper: OrientationHelper): View? {
        val firstChildPos = (layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
        val isLastChild = layoutManager.findLastCompletelyVisibleItemPosition() == layoutManager.itemCount - 1
        if (firstChildPos == RecyclerView.NO_POSITION || isLastChild) return null
        val firstChild = layoutManager.findViewByPosition(firstChildPos)
        if (orientationHelper.getDecoratedEnd(firstChild) >= orientationHelper.getDecoratedMeasurement(firstChild) / 2) {
            return firstChild
        }
        return layoutManager.findViewByPosition(firstChildPos + 1)
    }

    private fun getHorizontalHelper(layoutManager: RecyclerView.LayoutManager): OrientationHelper {
        if (mHorizontalHelper == null)
            mHorizontalHelper = OrientationHelper.createHorizontalHelper(layoutManager)
        return mHorizontalHelper!!
    }

    private fun getVerticalHelper(layoutManager: RecyclerView.LayoutManager): OrientationHelper {
        if (mVerticalHelper == null)
            mVerticalHelper = OrientationHelper.createVerticalHelper(layoutManager)
        return mVerticalHelper!!
    }
}