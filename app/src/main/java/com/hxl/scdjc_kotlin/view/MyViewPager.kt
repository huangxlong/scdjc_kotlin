package com.hxl.scdjc_kotlin.view

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent

/**
 * 解决PhotoView缩放拉伸崩溃问题
 * Created by Administrator on 2018/1/25 0025.
 */

class MyViewPager : ViewPager {

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}
    constructor(context: Context) : super(context) {}

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        try {
            return super.onInterceptTouchEvent(ev)
        } catch (e: Exception) {
        }

        return false
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        try {
            return super.onTouchEvent(ev)
        } catch (ex: IllegalArgumentException) {
        }

        return false
    }
}
