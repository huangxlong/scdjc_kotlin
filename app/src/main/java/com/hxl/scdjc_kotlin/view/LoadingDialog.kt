package com.hxl.scdjc_kotlin.view

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.graphics.Point
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import android.widget.ProgressBar
import android.widget.TextView
import com.hxl.scdjc_kotlin.R
import com.hxl.scdjc_kotlin.util.DensityUtil


class LoadingDialog(arg: Activity) : Dialog(arg, R.style.LoginDialog) {
    private val mScreenPoint = Point()


    private var mRootActivity: Activity? = null

    private var mMessageView: TextView? = null

    private var mIcon: ProgressBar? = null

    internal var mMessage = "加载中..."

    init {
        mRootActivity = arg
    }

    fun setMessage(message: String) {
        mMessage = message
        if (mMessageView != null) {
            if (TextUtils.isEmpty(message)) {
                mMessageView!!.visibility = View.GONE
            } else {
                mMessageView!!.visibility = View.VISIBLE
                mMessageView!!.text = message
            }
        }
    }

    override fun isShowing(): Boolean {
        return super.isShowing()
    }

    override fun show() {
        if (mIcon != null) {
            mIcon!!.visibility = View.VISIBLE
        }
        super.show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window!!.setBackgroundDrawable(BitmapDrawable())
        this.setContentView(R.layout.dialog_loading)
        initDialogWindow()
        mMessageView = findViewById<View>(R.id.text) as TextView
        if (TextUtils.isEmpty(mMessage)) {
            mMessage = "加载中..."
        }
        mMessageView!!.text = mMessage
        mIcon = findViewById<View>(R.id.icon) as ProgressBar
        val operatingAnim = AnimationUtils.loadAnimation(mRootActivity, R.anim.common_loading)
        val lin = LinearInterpolator()
        operatingAnim.interpolator = lin
        //mIcon.startAnimation(operatingAnim);
    }

    @SuppressLint("NewApi")
    private fun initDialogWindow() {
        val dialogWindow = window

        val lp = dialogWindow!!.attributes
        dialogWindow.setGravity(Gravity.CENTER)
        lp.y = 0
        lp.dimAmount = 0.0f
        val m = mRootActivity!!.windowManager
        val d = m.defaultDisplay // 获取屏幕宽、高用

        try {
            d.getSize(mScreenPoint)
        } catch (ignore: NoSuchMethodError) { // Older device
            mScreenPoint.x = d.width
            mScreenPoint.y = d.height
        }


        val p = dialogWindow.attributes // 获取对话框当前的参数值
        p.width = DensityUtil.dip2px(context, 160F)    // 宽度设置为屏幕的0.9
        p.width = (mScreenPoint.x * 1.0).toInt()
        p.height = mScreenPoint.y  // 高度设置为屏幕的0.35
        dialogWindow.attributes = p
    }

    companion object {
        val NUM = 4
    }
}
