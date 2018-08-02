package com.hxl.scdjc_kotlin.ui.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.hazz.kotlinmvp.net.exception.ErrorStatus
import com.hxl.scdjc_kotlin.R
import com.hxl.scdjc_kotlin.app.AppConstant
import com.hxl.scdjc_kotlin.app.UrlConstant
import com.hxl.scdjc_kotlin.base.BaseFragment
import com.hxl.scdjc_kotlin.bean.ArticleBean
import com.hxl.scdjc_kotlin.bean.VideoBean
import com.hxl.scdjc_kotlin.bean.base.RspDto
import com.hxl.scdjc_kotlin.mvp.contract.HomeContract
import com.hxl.scdjc_kotlin.mvp.presenter.HomePresenter
import com.hxl.scdjc_kotlin.ui.activity.*
import com.hxl.scdjc_kotlin.ui.adapter.*
import com.hxl.scdjc_kotlin.util.CommonUtil
import com.hxl.scdjc_kotlin.util.ToastUtil
import com.hxl.scdjc_kotlin.view.ACache
import com.hxl.scdjc_kotlin.view.GlideImageLoader
import com.hxl.scdjc_kotlin.view.MyLoadMoreView
import com.youth.banner.Banner
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * Created by Administrator
 * on 2018/7/11 星期三.
 */
class HomeFragment : BaseFragment(), HomeContract.View {
    private lateinit var headerView: View
    private var videoList = mutableListOf<RspDto.Video>()
    private var newsList = mutableListOf<RspDto.Article>()
    private var aCache: ACache? = null
    private var columnList: RspDto.Column? = null
    private lateinit var name: String
    private var columnId: Int = 0
    private var currentPage: Int = 1
    private var isLoadMore: Boolean = false
    private var isRefresh: Boolean = false

    private val mPresenter by lazy { HomePresenter() }
    private var adapter: BaseQuickAdapter<*, *>? = null
    private val newsCustomAdapter: NewsAdapter by lazy { NewsAdapter(R.layout.item_news_custom, newsList, true) }
    private val newsBigAdapter: NewsBigAdapter by lazy { NewsBigAdapter(newsList, true) }
    private val newsColumnAdapter by lazy { NewsColumnAdapter(newsList, true) }
    private val videoBigAdapter: VideoBigAdapter by lazy { VideoBigAdapter(videoList, true) }
    private val videoSmallAdapter: VideoSmallAdapter by lazy { VideoSmallAdapter(videoList, true) }
    private val newsTimeAdapter: NewsTimeAdapter by lazy { NewsTimeAdapter(newsList, true) }
    private val newsTextAdapter: NewsTextAdapter by lazy { NewsTextAdapter(newsList) }
    private val layoutManager by lazy { LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false) }

    companion object {
        fun getInstance(columnList: RspDto.Column): HomeFragment {
            val homeFragmentMvp = HomeFragment()
            homeFragmentMvp.columnList = columnList
            return homeFragmentMvp
        }
    }

    override fun getLayout(): Int = R.layout.fragment_home

    override fun initView() {
        mPresenter.attachView(this)
        mLayoutStatusView = multipleStatusView
        aCache = ACache.get(mContext)
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary)
        name = columnList!!.templateName
        columnId = columnList!!.id
        initBannerAndColumnChild()
        initRecycler()
        layout_search.setOnClickListener { startActivity(Intent(mContext, SearchActivity::class.java)) }
        swipeRefresh.setOnRefreshListener {
            isRefresh = true
            currentPage = 1
            adapter!!.setEnableLoadMore(true)
            startLoad(true)
        }
    }

    /**
     * 请求数据
     */
    override fun lazyLoad() {
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

    private fun initRecycler() {
        recycler.layoutManager = layoutManager
        when (name) {
            AppConstant.TYPE_IMG_SMALL -> {
                newsColumnAdapter.addHeaderView(headerView)
                recycler.adapter = newsColumnAdapter
            }
            AppConstant.TYPE_IMG_MEDIUM -> {
                newsCustomAdapter.addHeaderView(headerView)
                recycler.adapter = newsCustomAdapter
            }
            AppConstant.TYPE_IMG_BIG -> {
                newsBigAdapter.addHeaderView(headerView)
                recycler.adapter = newsBigAdapter
            }
            AppConstant.TYPE_VIDEO_BIG -> {
                videoBigAdapter.addHeaderView(headerView)
                recycler.adapter = videoBigAdapter
            }
            AppConstant.TYPE_VIDEO_SMALL -> {
                videoSmallAdapter.addHeaderView(headerView)
                recycler.adapter = videoSmallAdapter
            }
            AppConstant.TYPE_TEXT -> {
                val view = LayoutInflater.from(mContext)
                        .inflate(R.layout.layout_text_header, null)
                val tvTime = view.findViewById<TextView>(R.id.tv_time)
                tvTime.text = CommonUtil.getSystemTimeAndWeek()
                newsTextAdapter.addHeaderView(headerView)
                newsTextAdapter.addHeaderView(view)
                recycler.adapter = newsTextAdapter
            }
            AppConstant.TYPE_TIME -> {
                newsTimeAdapter.addHeaderView(headerView)
                recycler.adapter = newsTimeAdapter
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
                intent = Intent(mContext, VideoActivity::class.java)
                intent.putExtra(AppConstant.VIDEO_ID, videoList[position].id)
            } else {
                intent = Intent(mContext, NewsDetailActivity::class.java)
                intent.putExtra(AppConstant.ARTICLE_ID, newsList[position].id)
                intent.putExtra(AppConstant.LINK_TITLE, newsList[position].title)
            }
            startActivity(intent)
        }
        adapter!!.setLoadMoreView(MyLoadMoreView())
        adapter!!.setOnLoadMoreListener {
            isLoadMore = true
            currentPage++
            startLoad(true)
        }
    }

    override fun showError(errorMsg: String, errorCode: Int) {
        if (isLoadMore) {
            isLoadMore = false
            adapter!!.loadMoreFail()
        }
        if (isRefresh) {
            swipeRefresh.isRefreshing = false
            isRefresh = false
        }
        ToastUtil.show(mContext, errorMsg)
        if (errorCode == ErrorStatus.NETWORK_ERROR) {
            multipleStatusView?.showNoNetwork()
        } else {
            multipleStatusView?.showError()
        }
    }

    override fun showLoading() {
        if (!isLoadMore && !isRefresh) {
            multipleStatusView.showLoading()
        }
    }

    override fun dismissLoading() {
        if (isRefresh) {
            swipeRefresh.isRefreshing = false
        } else if (!isLoadMore && !isRefresh) {
            multipleStatusView.showContent()
        }
    }

    override fun setArticleData(articleData: ArticleBean, isNetWork: Boolean) {
        if (isNetWork) {
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
        if (isNetWork) {
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
            videoList.clear()
        }
        videoList.addAll(videoData.videoList!!)
        notifyRecycler()
        isRefresh = false
        isLoadMore = false
    }

    private fun notifyRecycler() {
        if (isRefresh) {
            when (name) {
                AppConstant.TYPE_IMG_MEDIUM -> (adapter as NewsAdapter).setNewData(newsList)
                AppConstant.TYPE_IMG_SMALL -> (adapter as NewsColumnAdapter).setNewData(newsList)
                AppConstant.TYPE_IMG_BIG -> (adapter as NewsBigAdapter).setNewData(newsList)
                AppConstant.TYPE_TEXT -> (adapter as NewsTextAdapter).setNewData(newsList)
                AppConstant.TYPE_TIME -> (adapter as NewsTimeAdapter).setNewData(newsList)
                AppConstant.TYPE_VIDEO_BIG -> (adapter as VideoBigAdapter).setNewData(videoList)
                AppConstant.TYPE_VIDEO_SMALL -> (adapter as VideoSmallAdapter).setNewData(videoList)
            }
        }
        adapter!!.notifyDataSetChanged()
    }

    /**
     * 初始化banner...
     */
    @SuppressLint("InflateParams")
    private fun initBannerAndColumnChild() {
        headerView = LayoutInflater.from(mContext).inflate(R.layout.layout_home_header, null)
        val mBanner: Banner = headerView.findViewById(R.id.banner)
        val recyclerTab: RecyclerView = headerView.findViewById(R.id.recyclerView)
        val imgList = columnList!!.advertisingList.map { UrlConstant.BASE_URL + it.imgPath }
        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
                .setImageLoader(GlideImageLoader())
                .setImages(imgList)
                .setBannerAnimation(Transformer.DepthPage)
                .isAutoPlay(true)
                .setDelayTime(3500)
                .setIndicatorGravity(BannerConfig.CENTER)
                .start()

        recyclerTab.isNestedScrollingEnabled = false  //不可滑动
        val childrenList = mutableListOf<RspDto.Column>()
        childrenList.addAll(columnList!!.childrenColumn)
        val columnAdapter = ColumnAdapter(mContext, childrenList)
        val layoutManager = GridLayoutManager(mContext, 3)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerTab.layoutManager = layoutManager
        recyclerTab.adapter = columnAdapter

        columnAdapter.setOnItemClickListener(object : ColumnAdapter.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                val column = childrenList[position]
                val intent: Intent
                if (TextUtils.isEmpty(column.checkImgPath)) {
                    intent = Intent(mContext, NewsListActivity::class.java)
                    intent.putExtra(AppConstant.COLUMN_ID, column.id)
                    intent.putExtra(AppConstant.TYPE, column.templateName)
                    intent.putExtra(AppConstant.COLUMN_TITLE, column.name)
                } else {
                    intent = Intent(mContext, WebActivity::class.java)
                    intent.putExtra(AppConstant.LINK_URL, "http://v.djc021.com")
                    intent.putExtra(AppConstant.LINK_TITLE, "直播")
                }
                startActivity(intent)
                childrenList[position].isRead = 1
                columnAdapter.notifyDataSetChanged()

                val isRead = childrenList.count { it.isRead == 1 }  //计算集合满足条件的个数
                if (isRead == childrenList.size) {
                    //全部都是已读状态
                    val mainTabActivity = activity as MainTabActivity
                    mainTabActivity.notifyIsRead(0)
                }
            }
        })
    }
}