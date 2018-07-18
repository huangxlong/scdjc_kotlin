package com.hxl.scdjc_kotlin.view

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView

/**
 * 正方形ImageView
 * Created by Administrator
 * on 2018/6/15 星期五.
 */
class SquareImageView : ImageView {
    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        //高度就是宽度值
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
    }
}
