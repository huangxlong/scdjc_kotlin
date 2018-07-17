package com.hxl.scdjc_kotlin.mvp.model

import com.hxl.scdjc_kotlin.app.AppConstant
import com.hxl.scdjc_kotlin.bean.ArticleBean
import com.hxl.scdjc_kotlin.net.RetrofitManager
import com.hxl.scdjc_kotlin.net.response.ResponseTransformer
import com.hxl.scdjc_kotlin.util.RxUtil
import io.reactivex.Observable

/**
 * Created by Administrator
 * on 2018/7/16 星期一.
 */
class SearchModel {

    /**
     * 搜索
     */
    fun search(searchKey: String, current: Int): Observable<ArticleBean>? {
        return RetrofitManager.service
                .search(searchKey, current, AppConstant.PAGE_SIZE)
                .compose(ResponseTransformer.handleResult())
                .compose(RxUtil.rxSchedulerHelper())
    }
}