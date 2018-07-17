package com.hxl.scdjc_kotlin.mvp.presenter

import com.hazz.kotlinmvp.net.exception.ExceptionHandle
import com.hxl.scdjc_kotlin.base.BasePresenter
import com.hxl.scdjc_kotlin.mvp.contract.SearchContract
import com.hxl.scdjc_kotlin.mvp.model.SearchModel

/**
 * Created by Administrator
 * on 2018/7/16 星期一.
 */
class SearchPresenter : BasePresenter<SearchContract.View>(), SearchContract.Presenter {
    private val searchModel by lazy {
        SearchModel()
    }

    override fun search(searchKey: String, current: Int) {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = searchModel.search(searchKey, current)?.
                subscribe(
                        { article ->
                            mRootView?.apply {
                                mRootView?.dismissLoading()
                                setSearchResult(article)
                            }
                        },
                        { _ ->
                            mRootView?.apply {
                                showError(ExceptionHandle.errorMsg, ExceptionHandle.errorCode)
                            }
                        })
        addSubscription(disposable!!)
    }
}