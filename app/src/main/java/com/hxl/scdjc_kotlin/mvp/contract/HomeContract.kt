package com.hxl.scdjc_kotlin.mvp.contract

import com.hxl.scdjc_kotlin.base.IBaseView
import com.hxl.scdjc_kotlin.base.IPresenter
import com.hxl.scdjc_kotlin.bean.ArticleBean
import com.hxl.scdjc_kotlin.bean.VideoBean

/**
 * Created by Administrator
 * on 2018/7/11 星期三.
 */
interface HomeContract {
    interface View : IBaseView {
        fun setArticleData(articleData: ArticleBean, isNetWork: Boolean)

        fun setVideoData(videoData: VideoBean, isNetWork: Boolean)

        /**
         * 显示错误信息
         */
        fun showError(errorMsg: String, errorCode: Int)
    }

    interface Presenter : IPresenter<View> {
        /**
         * 获取首页（文章/视屏）列表数据
         */
        fun getArticleData(columnId: Int, currentPage: Int)

        fun getVideoData(columnId: Int, currentPage: Int)

    }
}