package com.hxl.scdjc_kotlin.mvp.model

import com.hxl.scdjc_kotlin.bean.AdvertisingBean
import com.hxl.scdjc_kotlin.net.RetrofitManager
import com.hxl.scdjc_kotlin.net.response.ResponseTransformer
import com.hxl.scdjc_kotlin.util.RxUtil
import io.reactivex.Observable

/**
 * Created by Administrator
 * on 2018/7/13 星期五.
 */
class SplashModel {

    fun getAdvertising(): Observable<AdvertisingBean> {
        return RetrofitManager.service
                .getAdvertising()
                .compose(ResponseTransformer.handleResult())
                .compose(RxUtil.rxSchedulerHelper())
    }
}