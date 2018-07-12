package com.hxl.scdjc_kotlin.http

import com.hazz.kotlinmvp.net.exception.ApiException
import com.hazz.kotlinmvp.net.exception.ErrorStatus
import com.hazz.kotlinmvp.net.exception.ExceptionHandle
import com.hxl.scdjc_kotlin.bean.base.BaseRsp

import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import io.reactivex.functions.Function

/**
 * Created by Administrator
 * on 2018/7/11 星期三.
 */

object ResponseTransformer {
    fun <T> handleResult(): ObservableTransformer<BaseRsp<T>, T> {
        return ObservableTransformer { upstream ->
            upstream
                    .onErrorResumeNext(ErrorResumeFunction())
                    .flatMap(ResponseFunction())
        }
    }


    /**
     * 非服务器产生的异常，比如本地无无网络请求，Json数据解析错误等等。
     *
     * @param <T>
    </T> */
    private class ErrorResumeFunction<T> : Function<Throwable, ObservableSource<out BaseRsp<T>>> {

        @Throws(Exception::class)
        override fun apply(throwable: Throwable): ObservableSource<out BaseRsp<T>> {
            return Observable.error<BaseRsp<T>>(Throwable(ExceptionHandle.handleException(throwable)))
        }
    }

    /**
     * 服务其返回的数据解析
     * 正常服务器返回数据和服务器可能返回的exception
     *
     * @param <T>
    </T> */
    private class ResponseFunction<T> : Function<BaseRsp<T>, ObservableSource<T>> {

        @Throws(Exception::class)
        override fun apply(tResponse: BaseRsp<T>): ObservableSource<T> {
            val code = tResponse.status
            val message = tResponse.message
            return if (code == ErrorStatus.SUCCESS) {
                Observable.just(tResponse.data)
            } else {
                Observable.error(ApiException(message))
            }
        }
    }

}
