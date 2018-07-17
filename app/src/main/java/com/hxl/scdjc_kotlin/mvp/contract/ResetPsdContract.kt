package com.hxl.scdjc_kotlin.mvp.contract

import com.hxl.scdjc_kotlin.base.IBaseView
import com.hxl.scdjc_kotlin.base.IPresenter

/**
 * Created by Administrator
 * on 2018/7/17 星期二.
 */
interface ResetPsdContract {
    interface View : IBaseView {
        fun showResetResult(result: String)

        /**
         * 显示错误信息
         */
        fun showError(errorMsg: String, errorCode: Int)
    }

    interface Presenter : IPresenter<View> {
        fun resetPsd(oldPsd: String, newPsd: String)
    }
}