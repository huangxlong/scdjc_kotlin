package com.hxl.scdjc_kotlin.mvp.model

import com.hxl.scdjc_kotlin.bean.UserBean
import com.hxl.scdjc_kotlin.net.RetrofitManager
import com.hxl.scdjc_kotlin.net.response.ResponseTransformer
import com.hxl.scdjc_kotlin.util.RxUtil
import io.reactivex.Observable

/**
 * Created by Administrator
 * on 2018/7/17 星期二.
 */
class UserModel {

    /**
     * 获取个人中心信息
     */
    fun getUserInfo(columnName: String): Observable<UserBean> {
        return RetrofitManager.service
                .getUserInfo(columnName)
                .compose(ResponseTransformer.handleResult())
                .compose(RxUtil.rxSchedulerHelper())
    }
}