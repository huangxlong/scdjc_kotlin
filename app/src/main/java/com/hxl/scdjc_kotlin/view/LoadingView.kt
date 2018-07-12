package com.hxl.scdjc_kotlin.view

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.hxl.scdjc_kotlin.R


/**
 * 装有空视图，错误视图和加载视图的控件
 *
 * @author Administrator
 */
class LoadingView : ViewGroup {
    private var container: RelativeLayout? = null
    private var btnRefresh: LinearLayout? = null
    private var img: ImageView? = null
    private var text: TextView? = null
    private var loadingView: LinearLayout? = null
    /**
     * 显示的文字
     */
    private var msg: String? = null
    /**
     * 图片资源
     */
    private var imageRes: Int = 0
    /**
     * 状态
     */
    private val status: Int = 0
    private var clickListener: View.OnClickListener? = null

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {

        val mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.LoadingView)
        // 获取自定义属性和默认值
        msg = mTypedArray.getString(R.styleable.LoadingView_txt)
        imageRes = mTypedArray.getInt(R.styleable.LoadingView_imageResource, R.drawable.pic_empty)
        mTypedArray.recycle()
        init()
    }

    private fun init() {
        container = LayoutInflater.from(context)
                .inflate(R.layout.layout_include_error, null) as RelativeLayout
        this.addView(container, ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
        img = container!!.findViewById<View>(R.id.imgTag) as ImageView
        text = container!!.findViewById<View>(R.id.textTag) as TextView
        loadingView = container!!.findViewById<View>(R.id.loadingView) as LinearLayout
        btnRefresh = container!!.findViewById<View>(R.id.btnRefresh) as LinearLayout
        if (imageRes != 0) {
            setImgRes(imageRes)
        }
        if (msg != null) {
            setText(msg!!)
        }
        setStatus(status)
    }

    /**
     * 设置错误页面的点击事件
     *
     * @param clickListener
     */
    fun setRefreshListener(clickListener: View.OnClickListener) {
        this.clickListener = clickListener
        if (img != null) {
            img!!.setOnClickListener(clickListener)
        }
    }

    /**
     * 设置状态
     *
     * @param status
     */
    fun setStatus(status: Int) {
        when (status) {
            STATUS_LOADING -> {
                this.visibility = View.VISIBLE
                //                loadingView.startRotate();
                loadingView!!.visibility = View.VISIBLE
                text!!.visibility = View.GONE
                img!!.visibility = View.GONE
                btnRefresh!!.visibility = View.GONE
                isClickable = false
            }
            STATUS_ERROR -> {
                this.visibility = View.VISIBLE
                //                loadingView.stopRotate();
                loadingView!!.clearAnimation()
                loadingView!!.visibility = View.GONE
                text!!.visibility = View.VISIBLE
                img!!.visibility = View.VISIBLE
                btnRefresh!!.visibility = View.GONE
                isClickable = true
                if (clickListener != null) {
                    img!!.setOnClickListener(clickListener)
                } else {
                    img!!.setOnClickListener(null)
                }
                setImgRes(R.drawable.pic_empty)
                setText(context.resources.getString(R.string.text_loading_error))
            }
            STATUS_EMPTY -> {
                this.visibility = View.VISIBLE
                //                loadingView.stopRotate();
                loadingView!!.clearAnimation()
                loadingView!!.visibility = View.GONE
                text!!.visibility = View.VISIBLE
                img!!.visibility = View.VISIBLE
                btnRefresh!!.visibility = View.GONE
                isClickable = false
                setImgRes(R.drawable.pic_empty)
                setText(context.resources.getString(R.string.text_no_data))
            }
            STATUS_DONE ->
                //                loadingView.stopRotate();
                this.visibility = View.GONE

            else -> {
            }
        }
    }

    /**
     * 设置显示图片
     *
     * @param imgRes
     */
    fun setImgRes(imgRes: Int) {
        img!!.setImageResource(imgRes)
    }

    /**
     * text
     *
     * @param msg
     */
    fun setText(msg: String) {
        text!!.text = msg
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        // 记录总高度
        var mTotalHeight = 0
        // 遍历所有子视图
        val childCount = childCount
        for (i in 0 until childCount) {
            val childView = getChildAt(i)

            // 获取在onMeasure中计算的视图尺寸
            val measureHeight = childView.measuredHeight
            val measuredWidth = childView.measuredWidth

            childView.layout(l, mTotalHeight, measuredWidth, mTotalHeight + measureHeight)

            mTotalHeight += measureHeight

        }
    }

    /**
     * 计算控件的大小
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val measureWidth = measureWidth(widthMeasureSpec)
        val measureHeight = measureHeight(heightMeasureSpec)
        // 计算自定义的ViewGroup中所有子控件的大小
        measureChildren(widthMeasureSpec, heightMeasureSpec)
        // 设置自定义的控件MyViewGroup的大小
        setMeasuredDimension(measureWidth, measureHeight)
    }

    private fun measureWidth(pWidthMeasureSpec: Int): Int {
        var result = 0
        val widthMode = View.MeasureSpec.getMode(pWidthMeasureSpec)// 得到模式
        val widthSize = View.MeasureSpec.getSize(pWidthMeasureSpec)// 得到尺寸

        when (widthMode) {
        /**
         * mode共有三种情况，取值分别为MeasureSpec.UNSPECIFIED, MeasureSpec.EXACTLY,
         * MeasureSpec.AT_MOST。
         *
         *
         * MeasureSpec.EXACTLY是精确尺寸，
         * 当我们将控件的layout_width或layout_height指定为具体数值时如andorid
         * :layout_width="50dip"，或者为FILL_PARENT是，都是控件大小已经确定的情况，都是精确尺寸。
         *
         *
         * MeasureSpec.AT_MOST是最大尺寸，
         * 当控件的layout_width或layout_height指定为WRAP_CONTENT时
         * ，控件大小一般随着控件的子空间或内容进行变化，此时控件尺寸只要不超过父控件允许的最大尺寸即可
         * 。因此，此时的mode是AT_MOST，size给出了父控件允许的最大尺寸。
         *
         *
         * MeasureSpec.UNSPECIFIED是未指定尺寸，这种情况不多，一般都是父控件是AdapterView，
         * 通过measure方法传入的模式。
         */
            View.MeasureSpec.AT_MOST, View.MeasureSpec.EXACTLY -> result = widthSize
        }
        return result
    }

    private fun measureHeight(pHeightMeasureSpec: Int): Int {
        var result = 0

        val heightMode = View.MeasureSpec.getMode(pHeightMeasureSpec)
        val heightSize = View.MeasureSpec.getSize(pHeightMeasureSpec)

        when (heightMode) {
            View.MeasureSpec.AT_MOST, View.MeasureSpec.EXACTLY -> result = heightSize
        }
        return result
    }

    companion object {

        /**
         * 加载
         */
        val STATUS_LOADING = 0
        /**
         * 数据为空
         */
        val STATUS_EMPTY = 1
        /**
         * 加载错误
         */
        val STATUS_ERROR = 2
        /**
         * 加载完成
         */
        val STATUS_DONE = 3
    }
}
