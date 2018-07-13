package com.hxl.scdjc_kotlin.mvp.model

import com.hxl.scdjc_kotlin.app.AppConstant
import com.hxl.scdjc_kotlin.bean.ArticleBean
import com.hxl.scdjc_kotlin.bean.VideoBean
import com.hxl.scdjc_kotlin.net.response.ResponseTransformer
import com.hxl.scdjc_kotlin.net.RetrofitManager
import com.hxl.scdjc_kotlin.util.RxUtil
import io.reactivex.Observable


/**
 * Created by Administrator
 * on 2018/7/11 星期三.
 */
class HomeModel {

    /**
     * 获取文章列表
     */
    fun getArticle(columnID: Int, currentPage: Int): Observable<ArticleBean>? {
        return RetrofitManager.service
                .getArticleList(columnID, currentPage, AppConstant.PAGE_SIZE)
                .compose(ResponseTransformer.handleResult())
                .compose(RxUtil.rxSchedulerHelper())
    }

    /**
     * 获取视屏列表数据
     */
    fun getVideo(columnID: Int, currentPage: Int): Observable<VideoBean>? {
        return RetrofitManager.service
                .getVideoList(columnID, currentPage, AppConstant.PAGE_SIZE)
                .compose(ResponseTransformer.handleResult())
                .compose(RxUtil.rxSchedulerHelper())
    }
}