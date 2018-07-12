package com.hxl.scdjc_kotlin.mvp.presenter

import com.hazz.kotlinmvp.net.exception.ExceptionHandle
import com.hxl.scdjc_kotlin.base.BasePresenter
import com.hxl.scdjc_kotlin.mvp.contract.LoginContract
import com.hxl.scdjc_kotlin.mvp.model.LoginModel

/**
 * Created by Administrator
 * on 2018/7/12 星期四.
 */
class LoginPresenter : BasePresenter<LoginContract.View>(), LoginContract.Presenter {
    private val loginModel by lazy {
        LoginModel()
    }

    override fun login(userName: String, password: String) {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = loginModel.login(userName, password)!!
                .subscribe(
                        { loginBean ->
                            mRootView?.dismissLoading()
                            mRootView?.apply {
                                setLogin(loginBean)
                            }
                        },
                        { _ ->
                            mRootView?.dismissLoading()
                            mRootView?.apply {
                                showError(ExceptionHandle.errorMsg, ExceptionHandle.errorCode)
                            }
                        })
        addSubscription(disposable)
    }
}