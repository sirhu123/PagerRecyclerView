package com.example.recyclerviewdemo.recyclerview

import android.view.View
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper

class PagerSnapHelper(private val itemCount: Int) : SnapHelper() {
    private val TAG = "PagerSnapHelper"

    private var mVerticalHelper: OrientationHelper? = null
    private var mHorizontalHelper: OrientationHelper? = null
    private var mRecyclerViewWidth = 0
    private var mRecyclerViewHeight = 0
    private var mCurrentScrolledX = 0
    private var mCurrentScrolledY = 0
    private var mScrolledX = 0
    private var mScrolledY = 0
    private var mFlung = false

    private val mScrollListener = object : RecyclerView.OnScrollListener() {
        private var scrolledByUser = false

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            if (newState == RecyclerView.SCROLL_STATE_DRAGGING) scrolledByUser = true
            if (newState == RecyclerView.SCROLL_STATE_IDLE && scrolledByUser) {
                scrolledByUser = false
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            mScrolledX += dx
            mScrolledY += dy
            if (scrolledByUser) {
                mCurrentScrolledX += dx
                mCurrentScrolledY += dy
            }
        }
    }

    override fun attachToRecyclerView(recyclerView: RecyclerView?) {
        super.attachToRecyclerView(recyclerView)
        recyclerView?.run {
            addOnScrollListener(mScrollListener)
            post {
                mRecyclerViewWidth = width
                mRecyclerViewHeight = height
            }
        }
    }

    override fun calculateDistanceToFinalSnap(layoutManager: RecyclerView.LayoutManager, targetView: View): IntArray? {
        val out = IntArray(2)
        if (layoutManager.canScrollHorizontally()) {
            out[0] = distanceToStart(targetView, getHorizontalHelper(layoutManager))
            out[1] = 0
        } else if (layoutManager.canScrollVertically()) {
            out[0] = 0
            out[1] = distanceToStart(targetView, getVerticalHelper(layoutManager))
        }
        return out
    }

    private fun distanceToStart(targetView: View, orientationHelper: OrientationHelper): Int {
        return orientationHelper.getDecoratedStart(targetView) - orientationHelper.startAfterPadding
    }

    override fun findTargetSnapPosition(
        layoutManager: RecyclerView.LayoutManager?,
        velocityX: Int,
        velocityY: Int
    ): Int {
        val targetPosition = getTargetPosition()
        mFlung = targetPosition != RecyclerView.NO_POSITION
        println("$TAG findTargetSnapPosition, pos: $targetPosition")
        return targetPosition
    }

    override fun findSnapView(layoutManager: RecyclerView.LayoutManager?): View? {
        if (mFlung) {
            resetCurrentScrolled()
            mFlung = false
            return null
        }
        if (layoutManager == null) return null
        val targetPosition = getTargetPosition()
        println("$TAG findSnapView, pos: $targetPosition")
        if (targetPosition == RecyclerView.NO_POSITION) return null
        layoutManager.startSmoothScroll(createScroller(layoutManager).apply {
            this?.targetPosition = targetPosition
        })
        return null
    }

    private fun getTargetPosition(): Int {
        println("$TAG getTargetPosition, mScrolledX: $mScrolledX, mCurrentScrolledX: $mCurrentScrolledX")
        val page = when {
            mCurrentScrolledX > 0 -> mScrolledX / mRecyclerViewWidth + 1
            mCurrentScrolledX < 0 -> mScrolledX / mRecyclerViewWidth
            mCurrentScrolledY > 0 -> mScrolledY / mRecyclerViewHeight + 1
            mCurrentScrolledY < 0 -> mScrolledY / mRecyclerViewHeight
            else -> RecyclerView.NO_POSITION
        }
        resetCurrentScrolled()
        return if (page == RecyclerView.NO_POSITION) RecyclerView.NO_POSITION else page * itemCount
    }

    private fun resetCurrentScrolled() {
        mCurrentScrolledX = 0
        mCurrentScrolledY = 0
    }

    private fun getVerticalHelper(layoutManager: RecyclerView.LayoutManager): OrientationHelper {
        if (mVerticalHelper == null || mVerticalHelper!!.layoutManager !== layoutManager) {
            mVerticalHelper = OrientationHelper.createVerticalHelper(layoutManager)
        }
        return mVerticalHelper!!
    }

    private fun getHorizontalHelper(layoutManager: RecyclerView.LayoutManager): OrientationHelper {
        if (mHorizontalHelper == null || mHorizontalHelper!!.layoutManager !== layoutManager) {
            mHorizontalHelper = OrientationHelper.createHorizontalHelper(layoutManager)
        }
        return mHorizontalHelper!!
    }
}