package com.hxl.scdjc_kotlin.mvp.contract

import com.hxl.scdjc_kotlin.base.IBaseView
import com.hxl.scdjc_kotlin.base.IPresenter
import com.hxl.scdjc_kotlin.bean.ArticleBean

/**
 * Created by Administrator
 * on 2018/7/16 星期一.
 */
interface SearchContract {

    interface View : IBaseView {
        fun setSearchResult(article: ArticleBean)
        /**
         * 显示错误信息
         */
        fun showError(errorMsg: String, errorCode: Int)
    }

    interface Presenter : IPresenter<View> {
        fun search(searchKey: String, current: Int)
    }
}