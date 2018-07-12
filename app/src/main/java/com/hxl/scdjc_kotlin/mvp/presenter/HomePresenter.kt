package com.hxl.scdjc_kotlin.mvp.presenter

import com.hxl.scdjc_kotlin.base.BasePresenter
import com.hxl.scdjc_kotlin.bean.ArticleBean
import com.hxl.scdjc_kotlin.http.BaseSubscribe
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
        val disposable = homeModel.getArticle(columnId, currentPage)?.
                subscribe({ article ->
                    mRootView?.apply {
                        setArticleData(article)
                    }
                }, { throwable ->
                    mRootView?.apply {
                        showError(throwable.toString())
                    }
                })
        addSubscription(disposable!!)
    }

    override fun getVideoData(columnId: Int, currentPage: Int) {
        checkViewAttached()
        val disposable = homeModel.getVideo(columnId, currentPage)?.
                subscribe({ video ->
                    mRootView?.apply {
                        setVideoData(video)
                    }
                }, { throwable ->
                    mRootView?.apply {
                        showError(throwable.toString())
                    }
                })
        addSubscription(disposable!!)
    }
}