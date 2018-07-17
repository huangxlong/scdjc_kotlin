package com.hxl.scdjc_kotlin.mvp.model

import com.hxl.scdjc_kotlin.net.RetrofitManager
import com.hxl.scdjc_kotlin.net.response.ResponseTransformer
import com.hxl.scdjc_kotlin.util.RxUtil
import io.reactivex.Observable

/**
 * Created by Administrator
 * on 2018/7/17 星期二.
 */
class ResetPsdModel {

    /**
     * 修改密码
     */
    fun resetPsd(oldPsd: String, newPsd: String): Observable<String> {
        return RetrofitManager.service
                .resetPassword(oldPsd, newPsd)
                .compose(ResponseTransformer.handleResult())
                .compose(RxUtil.rxSchedulerHelper())
    }
}