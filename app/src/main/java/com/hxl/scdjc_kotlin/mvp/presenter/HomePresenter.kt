package com.hxl.scdjc_kotlin.mvp.presenter

import com.hazz.kotlinmvp.net.exception.ExceptionHandle
import com.hxl.scdjc_kotlin.base.BasePresenter
import com.hxl.scdjc_kotlin.mvp.contract.HomeContract
import com.hxl.scdjc_kotlin.mvp.model.HomeModel

/**
 * Created by Administrator
 * on 2018/7/11 星期三.
 */
class HomePresenter : BasePresenter<HomeContract.View>(), HomeContract.Presenter {

    private val homeModel by lazy {
        HomeModel()
    }

    /**
     * 获取文章列表数据
     */
    override fun getArticleData(columnId: Int, currentPage: Int) {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = homeModel.getArticle(columnId, currentPage)?.
                subscribe({ article ->
                    mRootView?.apply {
                        mRootView?.dismissLoading()
                        setArticleData(article)
                    }
                }, { _ ->
                    mRootView?.apply {
                        showError(ExceptionHandle.errorMsg, ExceptionHandle.errorCode)
                    }
                })
        addSubscription(disposable!!)
    }

    /**
     * 获取视屏列表数据
     */
    override fun getVideoData(columnId: Int, currentPage: Int) {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = homeModel.getVideo(columnId, currentPage)?.
                subscribe({ video ->
                    mRootView?.apply {
                        mRootView?.dismissLoading()
                        setVideoData(video)
                    }
                }, { _ ->
                    mRootView?.apply {
                        showError(ExceptionHandle.errorMsg, ExceptionHandle.errorCode)
                    }
                })
        addSubscription(disposable!!)
    }
}