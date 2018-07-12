package com.hxl.scdjc_kotlin.util

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast

import com.hxl.scdjc_kotlin.R


/**
 * 自定义Toast统一管理类
 *
 *
 * Created by Administrator on 2018/2/23.
 */
class ToastUtil private constructor() {

    init {
        /* cannot be instantiated */
        throw UnsupportedOperationException("cannot be instantiated")
    }

    companion object {

        private var mToast: Toast? = null

        /**
         * 默认短时间Toast
         *
         * @param context
         * @param message
         */
        fun show(context: Context, message: Int) {
            show(context, message, Toast.LENGTH_SHORT)
        }

        /**
         * Toast
         *
         * @param context
         * @param resId
         * @param duration
         */
        fun show(context: Context, resId: Int, duration: Int) {
            show(context, context.getString(resId), duration)
        }

        /**
         * 自定义Toast样式
         *
         * @param context
         * @param message
         */
        @JvmOverloads
        fun show(context: Context, message: String, duration: Int = Toast.LENGTH_SHORT) {
            if (mToast == null) {
                val view = LayoutInflater.from(context).inflate(R.layout.layout_toast, null)
                mToast = Toast(context)
                mToast!!.setGravity(Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL, 0, 0)
                mToast!!.view = view
            }
            mToast!!.duration = duration
            val textView = mToast!!.view.findViewById<View>(R.id.toast_text) as TextView
            textView.text = message
            mToast!!.show()
        }

        fun cancel() {
            if (mToast != null) {
                mToast!!.cancel()
            }
        }
    }

}
/**
 * 默认短时间Toast
 *
 * @param context
 * @param message
 */
