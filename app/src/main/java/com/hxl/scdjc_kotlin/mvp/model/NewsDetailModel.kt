package com.hxl.scdjc_kotlin.mvp.model

import com.hxl.scdjc_kotlin.bean.ArticleDetailBean
import com.hxl.scdjc_kotlin.http.RetrofitManager
import com.hxl.scdjc_kotlin.http.response.ResponseTransformer
import com.hxl.scdjc_kotlin.util.RxUtil
import io.reactivex.Observable

/**
 * Created by Administrator
 * on 2018/7/12 星期四.
 */
class NewsDetailModel {

    /**
     * 获取文章详情信息
     */
    fun getNewsDetail(articleId: Int): Observable<ArticleDetailBean> {
        return RetrofitManager.service
                .getArticleDetail(articleId)
                .compose(ResponseTransformer.handleResult())
                .compose(RxUtil.rxSchedulerHelper())
    }

    /**
     * 点赞
     */
    fun thumpUp(articleId: Int): Observable<String> {
        return RetrofitManager.service
                .thumbUp(articleId)
                .compose(ResponseTransformer.handleResult())
                .compose(RxUtil.rxSchedulerHelper())
    }
}