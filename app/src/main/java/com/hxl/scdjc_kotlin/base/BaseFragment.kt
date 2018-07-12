package com.hxl.scdjc_kotlin.base

import android.app.Activity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.hxl.scdjc_kotlin.R
import com.hxl.scdjc_kotlin.view.MultipleStatusView

/**
 * Created by Administrator
 * on 2018/7/4 星期三.
 */
abstract class BaseFragment : Fragment() {
    open lateinit var mContext: Activity
    /**
     * 视图是否加载完毕
     */
    private var isViewPrepare = false
    /**
     * 数据是否加载过了
     */
    private var hasLoadData = false
    /**
     * 多种状态的 View 的切换
     */
    protected var mLayoutStatusView: MultipleStatusView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayout(), null)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this.activity!!
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) lazyLoadDataIfPrepared()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isViewPrepare = true
        initView()
        lazyLoadDataIfPrepared()
        //多种状态切换的view 重试点击事件
        mLayoutStatusView?.setOnRetryClickListener { lazyLoad() }
    }

    //懒加载
    private fun lazyLoadDataIfPrepared() {
        if (userVisibleHint && isViewPrepare && !hasLoadData) {
            lazyLoad()
            hasLoadData = true
        }

    }

    /**
     * 获取布局文件
     */
    protected abstract fun getLayout(): Int

    /**
     * 初始化view
     */
    protected abstract fun initView()

    /**
     * 懒加载
     */
    protected abstract fun lazyLoad()
}