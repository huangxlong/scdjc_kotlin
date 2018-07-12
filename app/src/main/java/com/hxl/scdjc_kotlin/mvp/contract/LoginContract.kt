package com.hxl.scdjc_kotlin.mvp.contract

import com.hxl.scdjc_kotlin.base.IBaseView
import com.hxl.scdjc_kotlin.base.IPresenter
import com.hxl.scdjc_kotlin.bean.LoginBean

/**
 * Created by Administrator
 * on 2018/7/12 星期四.
 */
interface LoginContract {
    interface View : IBaseView {
        fun setLogin(loginBean: LoginBean)

        /**
         * 显示错误信息
         */
        fun showError(errorMsg: String, errorCode: Int)
    }

    interface Presenter : IPresenter<View> {
        fun login(userName: String, password: String)
    }
}