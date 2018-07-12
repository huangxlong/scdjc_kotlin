package com.hxl.scdjc_kotlin.mvp.model

import com.hxl.scdjc_kotlin.bean.LoginBean
import com.hxl.scdjc_kotlin.http.response.ResponseTransformer
import com.hxl.scdjc_kotlin.http.RetrofitManager
import com.hxl.scdjc_kotlin.util.RxUtil
import io.reactivex.Observable

/**
 * Created by Administrator
 * on 2018/7/12 星期四.
 */
class LoginModel {

    fun login(userName: String, password: String): Observable<LoginBean>? {
        return RetrofitManager.service
                .login(userName, password)
                .compose(ResponseTransformer.handleResult())
                .compose(RxUtil.rxSchedulerHelper())
    }
}