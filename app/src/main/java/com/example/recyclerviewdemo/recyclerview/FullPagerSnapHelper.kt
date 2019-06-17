package com.example.recyclerviewdemo.recyclerview

import androidx.recyclerview.widget.RecyclerView
import kotlin.math.absoluteValue

class FullPagerSnapHelper {
    private var mRecyclerView: RecyclerView? = null
    private var mHalfItemWidth = 0
    private var mFullPageWidth = 0
    private var mScrolled = false

    private val mScrollListener = object : RecyclerView.OnScrollListener() {
        private var mTotalScrolledX = 0
        private var mTotalScrolledY = 0

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            println("tag=== onScrollStateChanged, state: $newState")
            if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                mScrolled = true
            }
            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                if (mScrolled) {
                    mScrolled = false
                    if (mTotalScrolledX.absoluteValue > 0 || mTotalScrolledY.absoluteValue > 0) {
                        snapToTargetExistingView(mTotalScrolledX, mTotalScrolledY)
                    }
                }
                mTotalScrolledX = 0
                mTotalScrolledY = 0
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            mTotalScrolledX += dx
            mTotalScrolledY += dy
        }
    }

    private val onFlingListener = object : RecyclerView.OnFlingListener() {
        override fun onFling(velocityX: Int, velocityY: Int): Boolean {
            println("tag=== onFling, vX: $velocityX, vY: $velocityY")
            return false
        }
    }

    @Throws(IllegalStateException::class)
    fun attachToRecyclerView(recyclerView: RecyclerView) {
        recyclerView.post {
            mFullPageWidth = recyclerView.measuredWidth
            mHalfItemWidth = recyclerView.measuredWidth / 6
            println("tag=== full: $mFullPageWidth, half: $mHalfItemWidth")
        }

        if (mRecyclerView != null) {
            destroyCallbacks()
        }
        mRecyclerView = recyclerView
        if (mRecyclerView != null) {
            setupCallbacks()
        }
    }

    @Throws(IllegalStateException::class)
    private fun setupCallbacks() {
        mRecyclerView?.addOnScrollListener(mScrollListener)
        mRecyclerView?.onFlingListener = onFlingListener
    }

    private fun destroyCallbacks() {
        mRecyclerView?.removeOnScrollListener(mScrollListener)
        mRecyclerView?.onFlingListener = null
    }

    private fun snapToTargetExistingView(dx: Int, dy: Int) {
        when {
            dx.absoluteValue >= mHalfItemWidth -> mRecyclerView?.smoothScrollBy(
                if (dx > 0) mFullPageWidth - dx else -mFullPageWidth - dx, 0
            )
            dy.absoluteValue >= mHalfItemWidth -> mRecyclerView?.smoothScrollBy(
                0, if (dy > 0) mFullPageWidth - dy else -mFullPageWidth - dy
            )
            else -> mRecyclerView?.smoothScrollBy(-dx, -dy)
        }
    }
}