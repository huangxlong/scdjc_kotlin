package com.hxl.scdjc_kotlin.mvp.presenter

import com.hazz.kotlinmvp.net.exception.ExceptionHandle
import com.hxl.scdjc_kotlin.base.BasePresenter
import com.hxl.scdjc_kotlin.mvp.contract.SplashContract
import com.hxl.scdjc_kotlin.mvp.model.SplashModel

/**
 * Created by Administrator
 * on 2018/7/13 星期五.
 */
class SplashPresenter : BasePresenter<SplashContract.View>(), SplashContract.Presenter {
    private val splashModel by lazy { SplashModel() }

    override fun getAdvertising() {
        checkViewAttached()
        val disposable = splashModel.getAdvertising()
                .subscribe(
                        { advertising ->
                            mRootView?.apply {
                                setSplashResource(advertising)
                            }
                        },
                        { _ ->
                            mRootView?.apply {
                                showError(ExceptionHandle.errorMsg, ExceptionHandle.errorCode)
                            }
                        }
                )
        addSubscription(disposable)
    }
}