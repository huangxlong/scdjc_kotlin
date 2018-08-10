package com.hxl.scdjc_kotlin.mvp.presenter

import com.hazz.kotlinmvp.net.exception.ExceptionHandle
import com.hxl.scdjc_kotlin.base.BasePresenter
import com.hxl.scdjc_kotlin.mvp.contract.UserContract
import com.hxl.scdjc_kotlin.mvp.model.UserModel

/**
 * Created by Administrator
 * on 2018/7/17 星期二.
 */
class UserPresenter : BasePresenter<UserContract.View>(), UserContract.Presenter {
    private val userModel by lazy {
        UserModel()
    }

    override fun getUserInfo(columnName: String) {
        checkViewAttached()
        val disposable = userModel.getUserInfo(columnName)
                .subscribe(
                        { userInfo ->
                            mRootView?.apply {
                                setUserInfo(userInfo)
                            }
                        },
                        { throwable ->
                            mRootView?.apply {
                                showError(throwable.message!!, ExceptionHandle.errorCode)
                            }
                        }
                )
        addSubscription(disposable)
    }
}