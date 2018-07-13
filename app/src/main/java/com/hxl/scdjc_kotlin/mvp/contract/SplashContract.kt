package com.hxl.scdjc_kotlin.mvp.contract

import com.hxl.scdjc_kotlin.base.IBaseView
import com.hxl.scdjc_kotlin.base.IPresenter
import com.hxl.scdjc_kotlin.bean.AdvertisingBean

/**
 * Created by Administrator
 * on 2018/7/13 星期五.
 */
interface SplashContract {
    interface View : IBaseView {
        fun setSplashResource(advertising: AdvertisingBean)
        /**
         * 显示错误信息
         */
        fun showError(errorMsg: String, errorCode: Int)
    }

    interface Presenter : IPresenter<View> {
        fun getAdvertising()
    }
}