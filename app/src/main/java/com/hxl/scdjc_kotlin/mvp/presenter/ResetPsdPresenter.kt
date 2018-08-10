package com.hxl.scdjc_kotlin.mvp.presenter

import com.hazz.kotlinmvp.net.exception.ExceptionHandle
import com.hxl.scdjc_kotlin.base.BasePresenter
import com.hxl.scdjc_kotlin.mvp.contract.ResetPsdContract
import com.hxl.scdjc_kotlin.mvp.model.ResetPsdModel

/**
 * Created by Administrator
 * on 2018/7/17 星期二.
 */
class ResetPsdPresenter : BasePresenter<ResetPsdContract.View>(), ResetPsdContract.Presenter {
    private val resetPsdModel by lazy { ResetPsdModel() }

    override fun resetPsd(oldPsd: String, newPsd: String) {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = resetPsdModel.resetPsd(oldPsd, newPsd)
                .subscribe(
                        { result ->
                            mRootView?.apply {
                                mRootView!!.dismissLoading()
                                showResetResult(result)
                            }
                        },
                        { throwable ->
                            mRootView?.apply {
                                mRootView!!.dismissLoading()
                                showError(throwable.message!!, ExceptionHandle.errorCode)
                            }
                        }
                )
        addSubscription(disposable)
    }
}