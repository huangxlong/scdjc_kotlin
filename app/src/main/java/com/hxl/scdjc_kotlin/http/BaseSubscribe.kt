package com.hxl.scdjc_kotlin.http

import com.hxl.scdjc_kotlin.bean.base.BaseRsp
import org.reactivestreams.Subscriber

/**
 * Created by Administrator
 * on 2018/6/27 星期三.
 */
abstract class BaseSubscribe<T> : Subscriber<BaseRsp<T>> {
    private val success = 0
    private val sessionError = 10002         //当前session已过期
    private val noRoot = 10000           //当前栏目未购买


    override fun onNext(t: BaseRsp<T>?) {
        if (t != null) {
            if (t.status == success) {
                onSuccess(t.data)
            } else onFail(t.message)
        }

    }

    override fun onError(e: Throwable?) {
        onFail(e.toString())
    }

    protected abstract fun onSuccess(t: T?)
    protected abstract fun onFail(errMsg: String?)
}