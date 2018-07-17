package com.hxl.scdjc_kotlin.view

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.graphics.Point
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.text.TextUtils
import android.view.Display
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView

import com.hxl.scdjc_kotlin.R


class DescDialog : Dialog {
    private val mScreenPoint = Point()
    private var mRootActivity: Activity? = null

    private var mTitleView: TextView? = null
    private var mDescriptionView: TextView? = null
    private var mDescView2: TextView? = null
    private var mRightBtnView: Button? = null
    private var mLeftBtnView: Button? = null

    private var mTitle: String? = null
    private var mDescription: String? = null
    private var mCancelText: String? = null
    private var mOkText: String? = null
    private var isForce = false

    /**
     * 一个按钮Dialog
     *
     * @param arg
     * @param title
     * @param des
     */
    constructor(arg: Activity, title: String, des: String) : super(arg, R.style.LoginDialog) {
        mRootActivity = arg
        mTitle = title
        mDescription = des
    }

    /**
     * 一个按钮Dialog
     *
     * @param arg
     * @param title
     * @param des
     */
    constructor(arg: Activity, title: String, des: String, isForce: Boolean) : super(arg, R.style.LoginDialog) {
        mRootActivity = arg
        mTitle = title
        mDescription = des
        this.isForce = isForce
    }

    /**
     * 两个按钮的Dialog
     *
     * @param arg
     * @param title
     * @param des
     */
    constructor(arg: Activity, title: String, des: String,
                cancelText: String, okText: String) : super(arg, R.style.LoginDialog) {
        mRootActivity = arg
        mTitle = title
        mCancelText = cancelText
        mOkText = okText
        mDescription = des
    }

    /**
     * 两个按钮的Dialog
     *
     * @param arg
     * @param title
     * @param des
     */
    constructor(arg: Activity, title: String, des: String,
                cancelText: String, okText: String, isForce: Boolean) : super(arg, R.style.LoginDialog) {
        mRootActivity = arg
        mTitle = title
        mCancelText = cancelText
        mOkText = okText
        mDescription = des
        this.isForce = isForce
    }

    fun setTitle(text: String) {
        mTitle = text
        if (mTitleView != null) {
            mTitleView!!.text = mTitle
        }
    }

    fun setDescription(Text: String) {
        mDescription = Text
        if (mDescriptionView != null) {
            mDescriptionView!!.text = mDescription
        }
    }

    fun setLeftBtnText(cancelText: String) {
        mCancelText = cancelText
        if (mLeftBtnView != null) {
            mLeftBtnView!!.text = cancelText
        }
    }

    fun setDesc2Text(desc: String) {
        mDescView2!!.visibility = View.VISIBLE
        if (mDescView2 != null) {
            mDescView2!!.text = desc
        }
    }

    fun setTextGravity(gravity: Int) {
        mDescriptionView!!.visibility = View.VISIBLE
        if (mDescriptionView != null) {
            mDescriptionView!!.gravity = gravity
        }
    }

    fun setRightBtnText(okText: String) {
        mOkText = okText
        if (mRightBtnView != null) {
            mRightBtnView!!.text = okText
        }
    }

    fun setLeftClick(cancelClick: View.OnClickListener) {
        if (mLeftBtnView != null) {
            mLeftBtnView!!.setOnClickListener(cancelClick)
        }
    }

    fun setRightClick(okClick: View.OnClickListener) {
        if (mRightBtnView != null) {
            mRightBtnView!!.setOnClickListener(okClick)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window!!.setBackgroundDrawable(BitmapDrawable())
        this.setContentView(R.layout.dialog_desc)
        initDialogWindow()
        initView()
        setCanceledOnTouchOutside(false)
        setCancelable(!isForce)
    }

    internal fun initView() {
        mRightBtnView = findViewById<View>(R.id.btnOk) as Button
        mLeftBtnView = findViewById<View>(R.id.btnCancel) as Button
        mTitleView = findViewById<View>(R.id.title) as TextView
        mDescriptionView = findViewById<View>(R.id.description) as TextView
        mDescView2 = findViewById<View>(R.id.tvDesc2) as TextView

        mTitleView!!.text = mTitle

        mDescriptionView!!.text = mDescription
        if (!TextUtils.isEmpty(mOkText)) {
            mRightBtnView!!.text = mOkText
        }
        if (!TextUtils.isEmpty(mCancelText)) {
            mLeftBtnView!!.text = mCancelText
            mLeftBtnView!!.setOnClickListener { dismiss() }
        } else {
            mLeftBtnView!!.visibility = View.GONE
            mRightBtnView!!.setOnClickListener { dismiss() }
        }
    }

    override fun onBackPressed() {
        if (isForce) {
            dismiss()
            mRootActivity!!.finish()
        }
        super.onBackPressed()
    }

    @SuppressLint("NewApi")
    private fun initDialogWindow() {
        val dialogWindow = window

        /*
         * lp.x与lp.y表示相对于原始位置的偏移.
         * 当参数值包含Gravity.LEFT时,对话框出现在左边,所以lp.x就表示相对左边的偏移,负值忽略.
         * 当参数值包含Gravity.RIGHT时,对话框出现在右边,所以lp.x就表示相对右边的偏移,负值忽略.
         * 当参数值包含Gravity.TOP时,对话框出现在上边,所以lp.y就表示相对上边的偏移,负值忽略.
         * 当参数值包含Gravity.BOTTOM时,对话框出现在下边,所以lp.y就表示相对下边的偏移,负值忽略.
         * 当参数值包含Gravity.CENTER_HORIZONTAL时
         * ,对话框水平居中,所以lp.x就表示在水平居中的位置移动lp.x像素,正值向右移动,负值向左移动.
         * 当参数值包含Gravity.CENTER_VERTICAL时
         * ,对话框垂直居中,所以lp.y就表示在垂直居中的位置移动lp.y像素,正值向右移动,负值向左移动.
         * gravity的默认值为Gravity.CENTER,即Gravity.CENTER_HORIZONTAL |
         * Gravity.CENTER_VERTICAL.
         *
         * 本来setGravity的参数值为Gravity.LEFT | Gravity.TOP时对话框应出现在程序的左上角,但在
         * 我手机上测试时发现距左边与上边都有一小段距离,而且垂直坐标把程序标题栏也计算在内了,
         * Gravity.LEFT, Gravity.TOP, Gravity.BOTTOM与Gravity.RIGHT都是如此,据边界有一小段距离
         */
        //WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow!!.setGravity(Gravity.CENTER_HORIZONTAL or Gravity.CENTER_VERTICAL)
        //lp.y = - Utils.dip2px(50, mRootActivity.getResources());

        val m = mRootActivity!!.windowManager
        val d = m.defaultDisplay // 获取屏幕宽、高用

        try {
            d.getSize(mScreenPoint)
        } catch (ignore: NoSuchMethodError) { // Older device
            mScreenPoint.x = d.width
            mScreenPoint.y = d.height
        }

        val p = dialogWindow.attributes // 获取对话框当前的参数值
        p.width = (mScreenPoint.x * 0.80).toInt()    // 宽度设置为屏幕的0.9
        //p.height = (int) (mScreenPoint.y * 0.35);  // 高度设置为屏幕的0.35
        dialogWindow.attributes = p
    }
}
