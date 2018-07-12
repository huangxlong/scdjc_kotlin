package com.hxl.scdjc_kotlin.mvp.contract

import com.hxl.scdjc_kotlin.base.IBaseView
import com.hxl.scdjc_kotlin.base.IPresenter
import com.hxl.scdjc_kotlin.bean.ArticleDetailBean

/**
 * Created by Administrator
 * on 2018/7/12 星期四.
 */
interface NewsDetailContract {
    interface View : IBaseView {

        fun setNewsDetail(articleDetailBean: ArticleDetailBean)

        /**
         * 点赞结果
         */
        fun thumpUpResult(result: String)

        /**
         * 显示错误信息
         */
        fun showError(errorMsg: String, errorCode: Int)
    }

    interface Presenter : IPresenter<View> {
        fun getNewsDetail(articleId: Int)

        fun thumpUp(articleId: Int)
    }
}