package com.hxl.scdjc_kotlin.view


import com.chad.library.adapter.base.loadmore.LoadMoreView
import com.hxl.scdjc_kotlin.R

/**
 * Created by Administrator
 * on 2018/6/22 星期五.
 */
class MyLoadMoreView : LoadMoreView() {


    override fun getLayoutId(): Int {
        return R.layout.layout_loading
    }

    override fun getLoadingViewId(): Int {
        return R.id.load_more_loading_view
    }

    override fun getLoadFailViewId(): Int {
        return R.id.load_more_load_fail_view
    }

    override fun getLoadEndViewId(): Int {
        return R.id.load_more_load_end_view
    }
}
