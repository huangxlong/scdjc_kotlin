package com.hxl.scdjc_kotlin.view.rcImage

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PointF
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.RectF
import android.graphics.Region
import android.util.AttributeSet
import android.view.View
import android.widget.Checkable
import com.hxl.scdjc_kotlin.R


import java.util.ArrayList

/**
 * Created by Administrator
 * on 2018/5/29 星期二.
 */
class RCHelper {
    var radii = FloatArray(8)   // top-left, top-right, bottom-right, bottom-left
    lateinit var mClipPath: Path                 // 剪裁区域路径
    lateinit var mPaint: Paint                   // 画笔
    var mRoundAsCircle = false // 圆形
    var mDefaultStrokeColor: Int = 0        // 默认描边颜色
    var mStrokeColor: Int = 0               // 描边颜色
    var mStrokeColorStateList: ColorStateList? = null// 描边颜色的状态
    var mStrokeWidth: Int = 0               // 描边半径
    var mClipBackground: Boolean = false        // 是否剪裁背景
    lateinit var mAreaRegion: Region             // 内容区域
    var mEdgeFix = 10              // 边缘修复
    lateinit var mLayer: RectF                   // 画布图层大小


    //--- Selector 支持 ----------------------------------------------------------------------------

    var mChecked: Boolean = false              // 是否是 check 状态
    var mOnCheckedChangeListener: OnCheckedChangeListener? = null

    fun initAttrs(context: Context, attrs: AttributeSet) {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.RCAttrs)
        mRoundAsCircle = ta.getBoolean(R.styleable.RCAttrs_round_as_circle, false)
        mStrokeColorStateList = ta.getColorStateList(R.styleable.RCAttrs_stroke_color)
        if (null != mStrokeColorStateList) {
            mStrokeColor = mStrokeColorStateList!!.defaultColor
            mDefaultStrokeColor = mStrokeColorStateList!!.defaultColor
        } else {
            mStrokeColor = Color.WHITE
            mDefaultStrokeColor = Color.WHITE
        }
        mStrokeWidth = ta.getDimensionPixelSize(R.styleable.RCAttrs_stroke_width, 0)
        mClipBackground = ta.getBoolean(R.styleable.RCAttrs_clip_background, false)
        val roundCorner = ta.getDimensionPixelSize(R.styleable.RCAttrs_round_corner, 0)
        val roundCornerTopLeft = ta.getDimensionPixelSize(R.styleable.RCAttrs_round_corner_top_left, roundCorner)
        val roundCornerTopRight = ta.getDimensionPixelSize(R.styleable.RCAttrs_round_corner_top_right, roundCorner)
        val roundCornerBottomLeft = ta.getDimensionPixelSize(R.styleable.RCAttrs_round_corner_bottom_left, roundCorner)
        val roundCornerBottomRight = ta.getDimensionPixelSize(R.styleable.RCAttrs_round_corner_bottom_right, roundCorner)
        ta.recycle()

        radii[0] = roundCornerTopLeft.toFloat()
        radii[1] = roundCornerTopLeft.toFloat()

        radii[2] = roundCornerTopRight.toFloat()
        radii[3] = roundCornerTopRight.toFloat()

        radii[4] = roundCornerBottomRight.toFloat()
        radii[5] = roundCornerBottomRight.toFloat()

        radii[6] = roundCornerBottomLeft.toFloat()
        radii[7] = roundCornerBottomLeft.toFloat()

        mLayer = RectF()
        mClipPath = Path()
        mAreaRegion = Region()
        mPaint = Paint()
        mPaint.color = Color.WHITE
        mPaint.isAntiAlias = true
    }

    fun onSizeChanged(view: View, w: Int, h: Int) {
        mLayer.set(0f, 0f, w.toFloat(), h.toFloat())
        refreshRegion(view)
    }

    fun refreshRegion(view: View) {
        val w = mLayer.width().toInt()
        val h = mLayer.height().toInt()
        val areas = RectF()
        areas.left = view.paddingLeft.toFloat()
        areas.top = view.paddingTop.toFloat()
        areas.right = (w - view.paddingRight).toFloat()
        areas.bottom = (h - view.paddingBottom).toFloat()
        mClipPath.reset()
        if (mRoundAsCircle) {
            val d = if (areas.width() >= areas.height()) areas.height() else areas.width()
            val r = d / 2
            val center = PointF((w / 2).toFloat(), (h / 2).toFloat())
            mClipPath.addCircle(center.x, center.y, r, Path.Direction.CW)
        } else {
            mClipPath.addRoundRect(areas, radii, Path.Direction.CW)
        }
        mClipPath.moveTo((-mEdgeFix).toFloat(), (-mEdgeFix).toFloat())  // 通过空操作让Path区域占满画布
        mClipPath.moveTo((w + mEdgeFix).toFloat(), (h + mEdgeFix).toFloat())
        val clip = Region(areas.left.toInt(), areas.top.toInt(), areas.right.toInt(), areas.bottom.toInt())
        mAreaRegion.setPath(mClipPath, clip)
    }

    fun onClipDraw(canvas: Canvas) {
        if (mStrokeWidth > 0) {
            // 支持半透明描边，将与描边区域重叠的内容裁剪掉
            mPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OUT)
            mPaint.color = Color.WHITE
            mPaint.strokeWidth = (mStrokeWidth * 2).toFloat()
            mPaint.style = Paint.Style.STROKE
            canvas.drawPath(mClipPath, mPaint)
            // 绘制描边
            mPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_OVER)
            mPaint.color = mStrokeColor
            mPaint.style = Paint.Style.STROKE
            canvas.drawPath(mClipPath, mPaint)
        }
        mPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_IN)
        mPaint.color = Color.WHITE
        mPaint.style = Paint.Style.FILL
        canvas.drawPath(mClipPath, mPaint)
    }

    fun drawableStateChanged(view: View) {
        if (view is RCAttrs) {
            val stateListArray = ArrayList<Int>()
            if (view is Checkable) {
                stateListArray.add(android.R.attr.state_checkable)
                if ((view as Checkable).isChecked)
                    stateListArray.add(android.R.attr.state_checked)
            }
            if (view.isEnabled) stateListArray.add(android.R.attr.state_enabled)
            if (view.isFocused) stateListArray.add(android.R.attr.state_focused)
            if (view.isPressed) stateListArray.add(android.R.attr.state_pressed)
            if (view.isHovered) stateListArray.add(android.R.attr.state_hovered)
            if (view.isSelected) stateListArray.add(android.R.attr.state_selected)
            if (view.isActivated) stateListArray.add(android.R.attr.state_activated)
            if (view.hasWindowFocus()) stateListArray.add(android.R.attr.state_window_focused)

            if (mStrokeColorStateList != null && mStrokeColorStateList!!.isStateful) {
                val stateList = IntArray(stateListArray.size)
                for (i in stateListArray.indices) {
                    stateList[i] = stateListArray[i]
                }
                val stateColor = mStrokeColorStateList!!.getColorForState(stateList, mDefaultStrokeColor)
                (view as RCAttrs).strokeColor = stateColor
            }
        }
    }

    interface OnCheckedChangeListener {
        fun onCheckedChanged(view: View, isChecked: Boolean)
    }
}