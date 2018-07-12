package com.hxl.scdjc_kotlin.base

/**
 * created: 2017/10/25
 * desc: Presenter 基类
 */


interface IPresenter<in V: IBaseView> {

    fun attachView(mRootView: V)

    fun detachView()

}
