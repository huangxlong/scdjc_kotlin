package com.hxl.scdjc_kotlin.base

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import com.hxl.scdjc_kotlin.R
import com.hxl.scdjc_kotlin.app.App
import com.hxl.scdjc_kotlin.view.LoadingDialog
import com.hxl.scdjc_kotlin.view.MultipleStatusView

/**
 * Created by Administrator
 * on 2018/6/27 星期三.
 */
abstract class BaseActivity : AppCompatActivity() {
    /**
     * 多种状态的 View 的切换
     */
    protected var mLayoutStatusView: MultipleStatusView? = null
    private var tag = "BaseActivity"
    private lateinit var loadingDialog: LoadingDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tag = javaClass.simpleName
        setContentView(getLayout())
        App.addActivity(this)
        setWindowStatusBarColor(this, R.color.colorPrimary)
        initView()
        loadData()
        initListener()
    }

    private fun initListener() {
        mLayoutStatusView?.setOnRetryClickListener { loadData() }
    }

    /**
     * 加载布局
     */
    protected abstract fun getLayout(): Int

    /**
     * 初始化view
     */
    protected abstract fun initView()

    /**
     * 请求数据
     */
    protected abstract fun loadData()

    //设置状态栏颜色
    fun setWindowStatusBarColor(activity: Activity, colorResId: Int) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val window = activity.window
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                window.statusBarColor = activity.resources.getColor(colorResId)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    fun showLoadingDialog() {
        if (isDestroy()) return

        loadingDialog = LoadingDialog(this@BaseActivity)
        loadingDialog.setMessage(getString(R.string.text_loading))
        loadingDialog.show()

    }


    open fun showLoadingDialog(text: String) {
        if (isDestroy()) return

        loadingDialog = LoadingDialog(this@BaseActivity)
        loadingDialog.setMessage(text)
        loadingDialog.show()

    }

    fun hideLoadingDialog() {
        if (isDestroy()) return
        loadingDialog.dismiss()
    }

    override fun onDestroy() {
        App.removeActivity(this)
        super.onDestroy()
    }

    private fun isDestroy(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (this.isFinishing || this.isDestroyed) {
                return true
            }
        } else {
            if (this.isFinishing) {
                return true
            }
        }
        return false
    }


}