package com.hxl.scdjc_kotlin.ui.activity

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.hazz.kotlinmvp.net.exception.ErrorStatus
import com.hxl.scdjc_kotlin.R
import com.hxl.scdjc_kotlin.app.AppConstant
import com.hxl.scdjc_kotlin.base.BaseActivity
import com.hxl.scdjc_kotlin.bean.ArticleBean
import com.hxl.scdjc_kotlin.bean.VideoBean
import com.hxl.scdjc_kotlin.bean.base.RspDto
import com.hxl.scdjc_kotlin.mvp.contract.HomeContract
import com.hxl.scdjc_kotlin.mvp.presenter.HomePresenter
import com.hxl.scdjc_kotlin.ui.adapter.*
import com.hxl.scdjc_kotlin.util.CommonUtil
import com.hxl.scdjc_kotlin.util.ToastUtil
import com.hxl.scdjc_kotlin.view.ACache
import com.hxl.scdjc_kotlin.view.MyLoadMoreView
import kotlinx.android.synthetic.main.activity_news_list.*
import kotlinx.android.synthetic.main.layout_title.*

///**
// * Created by Administrator
// * on 2018/7/9 星期一.
// */
class NewsListActivity : BaseActivity(), HomeContract.View {
    private val mPresenter by lazy { HomePresenter() }
    private var videoList = mutableListOf<RspDto.Video>()
    private var newsList = mutableListOf<RspDto.Article>()
    private var adapter: BaseQuickAdapter<*, *>? = null
    private val newsCustomAdapter: NewsAdapter by lazy { NewsAdapter(R.layout.item_news_custom, newsList, false) }
    private val newsBigAdapter: NewsAdapter by lazy { NewsAdapter(R.layout.item_news_big, newsList, false) }
    private val newsColumnAdapter by lazy { NewsColumnAdapter(newsList, false) }
    private val videoBigAdapter: VideoBigAdapter by lazy { VideoBigAdapter(videoList, false) }
    private val videoSmallAdapter: VideoSmallAdapter by lazy { VideoSmallAdapter(videoList, false) }
    private val newsTimeAdapter: NewsTimeAdapter by lazy { NewsTimeAdapter(newsList, false) }
    private val newsTextAdapter: NewsTextAdapter by lazy { NewsTextAdapter(newsList) }
    private val layoutManager by lazy { LinearLayoutManager(this@NewsListActivity, LinearLayoutManager.VERTICAL, false) }
    private var tvTime: TextView? = null
    private var aCache: ACache? = null
    private lateinit var name: String
    private var columnId: Int = 0
    private var currentPage = 1
    private var isLoadMore: Boolean = false
    private var isRefresh: Boolean = false
    //
    override fun getLayout(): Int = R.layout.activity_news_list

    override fun initView() {
        mPresenter.attachView(this)
        aCache = ACache.get(this@NewsListActivity)
        mLayoutStatusView = multipleStatusView
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary)
        iv_back.visibility = View.VISIBLE
        name = intent.extras.getString(AppConstant.TYPE)
        columnId = intent.extras.getInt(AppConstant.COLUMN_ID)
        val title = intent.extras.getString(AppConstant.COLUMN_TITLE)
        tv_title.text = title
        initRecycler()
        iv_back.setOnClickListener { onBackPressed() }
        swipeRefresh.setOnRefreshListener {
            currentPage = 1
            adapter!!.setEnableLoadMore(true)
            isRefresh = true
            startLoad(true)
        }
    }

    override fun loadData() {
        startLoad(false)
    }

    private fun startLoad(isRefreshOrLoadMore: Boolean) {
        if (name.contains(AppConstant.TYPE_VIDEO)) {
            val videoData = aCache!!.getAsObject("video_list$columnId") as VideoBean?
            if (videoData == null || isRefreshOrLoadMore) {
                mPresenter.getVideoData(columnId, currentPage)
            } else {
                setVideoData(videoData, false)
            }
        } else {
            val articleData = aCache!!.getAsObject("news_list$columnId") as ArticleBean?
            if (articleData == null || isRefreshOrLoadMore) {
                mPresenter.getArticleData(columnId, currentPage)
            } else {
                setArticleData(articleData, false)
            }
        }
    }

    override fun showLoading() {
        if (!isLoadMore && !isRefresh)
            mLayoutStatusView?.showLoading()
    }

    override fun dismissLoading() {
        if (isRefresh) {
            swipeRefresh.isRefreshing = false
        } else if (!isRefresh && !isLoadMore) {
            mLayoutStatusView?.showContent()
        }
    }

    override fun setArticleData(articleData: ArticleBean, isNetWork: Boolean) {
        if (isNetWork && !isLoadMore) {
            aCache!!.put("news_list$columnId", articleData)
        }
        if (isLoadMore) {
            if (articleData.articleList == null || articleData.articleList!!.isEmpty()) {
                adapter!!.loadMoreEnd()
            } else {
                adapter!!.loadMoreComplete()
            }
        }
        if (isRefresh) {
            newsList.clear()
        }
        newsList.addAll(articleData.articleList!!)
        notifyRecycler()
        isRefresh = false
        isLoadMore = false
    }

    override fun setVideoData(videoData: VideoBean, isNetWork: Boolean) {
        if (isNetWork && !isLoadMore) {
            aCache!!.put("video_list$columnId", videoData)
        }
        if (isLoadMore) {
            if (videoData.videoList == null || videoData.videoList!!.isEmpty()) {
                adapter!!.loadMoreEnd()
            } else {
                adapter!!.loadMoreComplete()
            }
        }
        if (isRefresh) {
            newsList.clear()
        }
        videoList.addAll(videoData.videoList!!)
        notifyRecycler()
        isRefresh = false
        isLoadMore = false
    }

    override fun showError(errorMsg: String, errorCode: Int) {
        if (isLoadMore) {
            isLoadMore = false
            adapter!!.loadMoreFail()
        }
        if (isRefresh) {
            isRefresh = false
            swipeRefresh.isRefreshing = false
        }
        ToastUtil.show(this@NewsListActivity, errorMsg)
        if (errorCode == ErrorStatus.NETWORK_ERROR) {
            multipleStatusView.showNoNetwork()
        } else {
            multipleStatusView.showError()
        }
    }

    private fun notifyRecycler() {
        if (isRefresh) {
            when (name) {
                AppConstant.TYPE_IMG_MEDIUM -> (adapter as NewsAdapter).setNewData(newsList)
                AppConstant.TYPE_IMG_SMALL -> (adapter as NewsColumnAdapter).setNewData(newsList)
                AppConstant.TYPE_IMG_BIG -> (adapter as NewsAdapter).setNewData(newsList)
                AppConstant.TYPE_TIME -> (adapter as NewsTimeAdapter).setNewData(newsList)
                AppConstant.TYPE_VIDEO_BIG -> (adapter as VideoBigAdapter).setNewData(videoList)
                AppConstant.TYPE_VIDEO_SMALL -> (adapter as VideoSmallAdapter).setNewData(videoList)
                AppConstant.TYPE_TEXT -> {
                    tvTime!!.text = CommonUtil.getSystemTimeAndWeek()
                    (adapter as NewsTextAdapter).setNewData(newsList)
                }
            }
        }
        adapter!!.notifyDataSetChanged()
    }

    private fun initRecycler() {
        recycler.layoutManager = layoutManager
        when (name) {
            AppConstant.TYPE_IMG_SMALL -> recycler.adapter = newsColumnAdapter
            AppConstant.TYPE_IMG_MEDIUM -> recycler.adapter = newsCustomAdapter
            AppConstant.TYPE_IMG_BIG -> recycler.adapter = newsBigAdapter
            AppConstant.TYPE_VIDEO_BIG -> recycler.adapter = videoBigAdapter
            AppConstant.TYPE_VIDEO_SMALL -> recycler.adapter = videoSmallAdapter
            AppConstant.TYPE_TIME -> recycler.adapter = newsTimeAdapter
            AppConstant.TYPE_TEXT -> {
                val view = LayoutInflater.from(this).inflate(R.layout.layout_text_header, null)
                tvTime = view.findViewById(R.id.tv_time)
                tvTime!!.text = CommonUtil.getSystemTimeAndWeek()
                newsTextAdapter.addHeaderView(view)
                recycler.adapter = newsTextAdapter
            }
        }
        adapter = recycler.adapter as BaseQuickAdapter<*, *>? ?: return
        adapter!!.setOnItemClickListener { _, view, position ->
            if (name == AppConstant.TYPE_TIME) return@setOnItemClickListener

            if (name == AppConstant.TYPE_TEXT) {
                val tvContent = view.findViewById<TextView>(R.id.tv_content)
                if (tvContent.maxLines == 3) {
                    tvContent.maxLines = 50
                    tvContent.ellipsize = null
                } else {
                    tvContent.maxLines = 3
                    tvContent.ellipsize = TextUtils.TruncateAt.END
                }
                return@setOnItemClickListener
            }

            val intent: Intent
            if (name.contains(AppConstant.TYPE_VIDEO)) {
                intent = Intent(this@NewsListActivity, VideoActivity::class.java)
                intent.putExtra(AppConstant.VIDEO_ID, videoList[position].id)
            } else {
                intent = Intent(this@NewsListActivity, NewsDetailActivity::class.java)
                intent.putExtra(AppConstant.ARTICLE_ID, newsList[position].id)
                intent.putExtra(AppConstant.LINK_TITLE, newsList[position].title)
            }
            startActivity(intent)
        }

        adapter!!.setLoadMoreView(MyLoadMoreView())
        adapter!!.setOnLoadMoreListener {
            currentPage++
            isLoadMore = true
            startLoad(true)
        }
    }
}