package com.hxl.scdjc_kotlin.ui.fragment

import android.content.Intent
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.hxl.scdjc_kotlin.R
import com.hxl.scdjc_kotlin.app.AppConstant
import com.hxl.scdjc_kotlin.base.BaseFragment
import com.hxl.scdjc_kotlin.bean.ArticleBean
import com.hxl.scdjc_kotlin.bean.VideoBean
import com.hxl.scdjc_kotlin.bean.base.RspDto
import com.hxl.scdjc_kotlin.http.RetrofitManager
import com.hxl.scdjc_kotlin.mvp.contract.HomeContract
import com.hxl.scdjc_kotlin.mvp.presenter.HomePresenter
import com.hxl.scdjc_kotlin.ui.activity.NewsDetailActivity
import com.hxl.scdjc_kotlin.ui.activity.NewsListActivity
import com.hxl.scdjc_kotlin.ui.activity.VideoActivity
import com.hxl.scdjc_kotlin.ui.activity.WebActivity
import com.hxl.scdjc_kotlin.ui.adapter.*
import com.hxl.scdjc_kotlin.util.CommonUtil
import com.hxl.scdjc_kotlin.util.ToastUtil
import com.hxl.scdjc_kotlin.view.ACache
import com.hxl.scdjc_kotlin.view.GlideImageLoader
import com.hxl.scdjc_kotlin.view.LoadingView
import com.youth.banner.Banner
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer
import kotlinx.android.synthetic.main.fragment_home_.*

/**
 * Created by Administrator
 * on 2018/7/11 星期三.
 */
class HomeFragmentMvp : BaseFragment(), HomeContract.View {

    private lateinit var headerView: View
    private var videoList = mutableListOf<RspDto.Video>()
    private var newsList = mutableListOf<RspDto.Article>()
    private var aCache: ACache? = null
    private var columnList: RspDto.Column? = null
    private lateinit var name: String
    private var columnId: Int = 0
    private var currentPage: Int = 1
    private var isLoadMore: Boolean = false

    private val mPresenter by lazy { HomePresenter() }
    private var adapter: BaseQuickAdapter<*, *>? = null
    private val newsCustomAdapter: NewsAdapter by lazy { NewsAdapter(R.layout.item_news_custom, newsList, true) }
    private val newsBigAdapter: NewsAdapter by lazy { NewsAdapter(R.layout.item_news_big, newsList, true) }
    private val newsColumnAdapter by lazy { NewsColumnAdapter(newsList, true) }
    private val videoBigAdapter: VideoBigAdapter by lazy { VideoBigAdapter(videoList, true) }
    private val videoSmallAdapter: VideoSmallAdapter by lazy { VideoSmallAdapter(videoList, true) }
    private val newsTimeAdapter: NewsTimeAdapter by lazy { NewsTimeAdapter(newsList, true) }
    private val newsTextAdapter: NewsTextAdapter by lazy { NewsTextAdapter(newsList) }
    private val layoutManager by lazy { LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false) }

    companion object {
        fun getInstance(columnList: RspDto.Column): HomeFragmentMvp {
            val homeFragmentMvp = HomeFragmentMvp()
            homeFragmentMvp.columnList = columnList
            return homeFragmentMvp
        }
    }

    override fun getLayout(): Int = R.layout.fragment_home_

    override fun initView() {
        mPresenter.attachView(this)
        aCache = ACache.get(mContext)
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary)
        name = columnList!!.templateName
        columnId = columnList!!.id
        initBannerAndColumnChild()
        initRecycler()
    }

    /**
     * 请求数据
     */
    override fun lazyLoad() {
        startLoad()
    }

    private fun startLoad() {
        if (name.contains(AppConstant.TYPE_VIDEO)) {
            mPresenter.getVideoData(columnId, currentPage)
        } else {
            mPresenter.getArticleData(columnId, currentPage)
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
        adapter!!.setOnLoadMoreListener {
            isLoadMore = true
            currentPage++
            startLoad()
        }
    }


    override fun showError(errMsg: String) {
        if (isLoadMore) {
            isLoadMore = false
            adapter!!.loadMoreFail()
        }
        ToastUtil.show(mContext, errMsg)
    }

    override fun showLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun dismissLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setArticleData(articleData: ArticleBean) {
        if (isLoadMore) {
            if (articleData.articleList == null || articleData.articleList!!.isEmpty()) {
                adapter!!.loadMoreEnd()
            } else {
                adapter!!.loadMoreComplete()
            }
        }
        loadingView.setStatus(LoadingView.STATUS_DONE)
        recycler.visibility = View.VISIBLE
        isLoadMore = false
        newsList.addAll(articleData.articleList!!)
        notifyRecycler()
    }

    override fun setVideoData(videoData: VideoBean) {
        if (isLoadMore) {
            if (videoData.videoList == null || videoData.videoList!!.isEmpty()) {
                adapter!!.loadMoreEnd()
            } else {
                adapter!!.loadMoreComplete()
            }
        }
        isLoadMore = false
        videoList.addAll(videoData.videoList!!)
        notifyRecycler()
    }

    private fun notifyRecycler() {
        if (isLoadMore) {
            when (name) {
                AppConstant.TYPE_IMG_MEDIUM -> (adapter as NewsAdapter).setNewData(newsList)
                AppConstant.TYPE_IMG_SMALL -> (adapter as NewsColumnAdapter).setNewData(newsList)
                AppConstant.TYPE_IMG_BIG -> (adapter as NewsAdapter).setNewData(newsList)
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
    private fun initBannerAndColumnChild() {
        headerView = LayoutInflater.from(mContext).inflate(R.layout.layout_home_header, null)
        val mBanner: Banner = headerView.findViewById(R.id.banner)
        val recyclerTab: RecyclerView = headerView.findViewById(R.id.recyclerView)
        val imgList = columnList!!.advertisingList.map { RetrofitManager.baseUrl + it.imgPath }
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
                val (name1, _, checkImgPath, _, _, templateName, id) = childrenList[position]
                val intent: Intent
                if (TextUtils.isEmpty(checkImgPath)) {
                    intent = Intent(mContext, NewsListActivity::class.java)
                    intent.putExtra(AppConstant.COLUMN_ID, id)
                    intent.putExtra(AppConstant.TYPE, templateName)
                    intent.putExtra(AppConstant.COLUMN_TITLE, name1)
                } else {
                    intent = Intent(mContext, WebActivity::class.java)
                    intent.putExtra(AppConstant.LINK_URL, "http://v.djc021.com")
                    intent.putExtra(AppConstant.LINK_TITLE, "直播")
                }
                startActivity(intent)
            }
        })
    }


}