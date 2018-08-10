package com.hxl.scdjc_kotlin.mvp.presenter

import com.hazz.kotlinmvp.net.exception.ExceptionHandle
import com.hxl.scdjc_kotlin.base.BasePresenter
import com.hxl.scdjc_kotlin.mvp.contract.NewsDetailContract
import com.hxl.scdjc_kotlin.mvp.model.NewsDetailModel

/**
 * Created by Administrator
 * on 2018/7/12 星期四.
 */
class NewsDetailPresenter : BasePresenter<NewsDetailContract.View>(), NewsDetailContract.Presenter {

    private val newsDetailModel by lazy {
        NewsDetailModel()
    }

    //获取文章详情
    override fun getNewsDetail(articleId: Int) {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = newsDetailModel.getNewsDetail(articleId)
                .subscribe(
                        { newsDetail ->
                            mRootView?.apply {
                                mRootView?.dismissLoading()
                                setNewsDetail(newsDetail)
                            }
                        },
                        { throwable ->
                            mRootView?.apply {
                                showError(throwable.message!!, ExceptionHandle.errorCode)
                            }
                        })
        addSubscription(disposable)
    }

    //文章点赞
    override fun thumpUp(articleId: Int) {
        checkViewAttached()
        val disposable = newsDetailModel.thumpUp(articleId)
                .subscribe(
                        { s ->
                            mRootView?.apply {
                                thumpUpResult(s)
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