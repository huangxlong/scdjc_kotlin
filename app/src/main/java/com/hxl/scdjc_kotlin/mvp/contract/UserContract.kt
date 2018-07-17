package com.hxl.scdjc_kotlin.mvp.contract

import com.hxl.scdjc_kotlin.base.IBaseView
import com.hxl.scdjc_kotlin.base.IPresenter
import com.hxl.scdjc_kotlin.bean.UserBean

/**
 * Created by Administrator
 * on 2018/7/17 星期二.
 */
interface UserContract {

    interface View : IBaseView {
        fun setUserInfo(userInfo: UserBean)
        /**
         * 显示错误信息
         */
        fun showError(errorMsg: String, errorCode: Int)
    }

    interface Presenter : IPresenter<View> {
        fun getUserInfo(columnName: String)
    }
}