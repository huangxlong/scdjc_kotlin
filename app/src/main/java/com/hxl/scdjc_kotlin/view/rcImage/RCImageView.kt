package com.hxl.scdjc_kotlin.view.rcImage

/**
 * Created by Administrator
 * on 2018/5/29 星期二.
 */

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.Checkable
import android.widget.ImageView


/**
 * 作用：圆角图片
 * 作者：GcsSloop
 */
@SuppressLint("AppCompatCustomView")
class RCImageView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : ImageView(context, attrs, defStyleAttr), Checkable, RCAttrs {

    internal var mRCHelper: RCHelper

    //--- 公开接口 ----------------------------------------------------------------------------------

    override var isClipBackground: Boolean
        get() = mRCHelper.mClipBackground
        set(clipBackground) {
            mRCHelper.mClipBackground = clipBackground
            invalidate()
        }

    override var isRoundAsCircle: Boolean
        get() = mRCHelper.mRoundAsCircle
        set(roundAsCircle) {
            mRCHelper.mRoundAsCircle = roundAsCircle
            invalidate()
        }

    override val topLeftRadius: Float
        get() = mRCHelper.radii[0]

    override val topRightRadius: Float
        get() = mRCHelper.radii[2]

    override val bottomLeftRadius: Float
        get() = mRCHelper.radii[4]

    override val bottomRightRadius: Float
        get() = mRCHelper.radii[6]

    override var strokeWidth: Int
        get() = mRCHelper.mStrokeWidth
        set(strokeWidth) {
            mRCHelper.mStrokeWidth = strokeWidth
            invalidate()
        }

    override var strokeColor: Int
        get() = mRCHelper.mStrokeColor
        set(strokeColor) {
            mRCHelper.mStrokeColor = strokeColor
            invalidate()
        }

    init {
        mRCHelper = RCHelper()
        mRCHelper.initAttrs(context, attrs!!)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mRCHelper.onSizeChanged(this, w, h)
    }

    override fun draw(canvas: Canvas) {
        mRCHelper.refreshRegion(this)
        if (mRCHelper.mClipBackground) {
            canvas.save()
            canvas.clipPath(mRCHelper.mClipPath)
            super.draw(canvas)
            canvas.restore()
        } else {
            super.draw(canvas)
        }
    }

    override fun onDraw(canvas: Canvas) {
        canvas.saveLayer(mRCHelper.mLayer, null, Canvas.ALL_SAVE_FLAG)
        super.onDraw(canvas)
        mRCHelper.onClipDraw(canvas)
        canvas.restore()
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        val action = ev.action
        if (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_UP) {
            refreshDrawableState()
        } else if (action == MotionEvent.ACTION_CANCEL) {
            isPressed = false
            refreshDrawableState()
        }
        if (!mRCHelper.mAreaRegion.contains(ev.x.toInt(), ev.y.toInt())) {
            isPressed = false
            refreshDrawableState()
            return false
        }
        return super.dispatchTouchEvent(ev)
    }

    override fun setRadius(radius: Int) {
        for (i in 0 until mRCHelper.radii.size) {
            mRCHelper.radii[i] = radius.toFloat()
        }
        invalidate()
    }

    override fun setTopLeftRadius(topLeftRadius: Int) {
        mRCHelper.radii[0] = topLeftRadius.toFloat()
        mRCHelper.radii[1] = topLeftRadius.toFloat()
        invalidate()
    }

    override fun setTopRightRadius(topRightRadius: Int) {
        mRCHelper.radii[2] = topRightRadius.toFloat()
        mRCHelper.radii[3] = topRightRadius.toFloat()
        invalidate()
    }

    override fun setBottomLeftRadius(bottomLeftRadius: Int) {
        mRCHelper.radii[4] = bottomLeftRadius.toFloat()
        mRCHelper.radii[5] = bottomLeftRadius.toFloat()
        invalidate()
    }

    override fun setBottomRightRadius(bottomRightRadius: Int) {
        mRCHelper.radii[6] = bottomRightRadius.toFloat()
        mRCHelper.radii[7] = bottomRightRadius.toFloat()
        invalidate()
    }


    //--- Selector 支持 ----------------------------------------------------------------------------

    override fun drawableStateChanged() {
        super.drawableStateChanged()
        mRCHelper.drawableStateChanged(this)
    }

    override fun setChecked(checked: Boolean) {
        if (mRCHelper.mChecked != checked) {
            mRCHelper.mChecked = checked
            refreshDrawableState()
            if (mRCHelper.mOnCheckedChangeListener != null) {
                mRCHelper.mOnCheckedChangeListener!!
                        .onCheckedChanged(this, mRCHelper.mChecked)
            }
        }
    }

    override fun isChecked(): Boolean {
        return mRCHelper.mChecked
    }

    override fun toggle() {
        isChecked = !mRCHelper.mChecked
    }

    fun setOnCheckedChangeListener(listener: RCHelper.OnCheckedChangeListener) {
        mRCHelper.mOnCheckedChangeListener = listener
    }
}
