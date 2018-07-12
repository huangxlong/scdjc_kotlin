//package com.hxl.scdjc_kotlin.ui.fragment
//
//import android.content.Intent
//import android.support.v7.widget.GridLayoutManager
//import android.support.v7.widget.LinearLayoutManager
//import android.support.v7.widget.RecyclerView
//import android.text.TextUtils
//import android.view.LayoutInflater
//import android.view.View
//import android.widget.TextView
//import com.chad.library.adapter.base.BaseQuickAdapter
//import com.hxl.scdjc_kotlin.R
//import com.hxl.scdjc_kotlin.app.AppConstant
//import com.hxl.scdjc_kotlin.base.BaseFragment
//import com.hxl.scdjc_kotlin.bean.ArticleBean
//import com.hxl.scdjc_kotlin.bean.VideoBean
//import com.hxl.scdjc_kotlin.bean.base.RspDto
//import com.hxl.scdjc_kotlin.http.BaseSubscribe
//import com.hxl.scdjc_kotlin.http.RetrofitManager
//import com.hxl.scdjc_kotlin.ui.activity.*
//import com.hxl.scdjc_kotlin.ui.adapter.*
//import com.hxl.scdjc_kotlin.util.CommonUtil
//import com.hxl.scdjc_kotlin.util.RxUtil
//import com.hxl.scdjc_kotlin.util.ToastUtil
//import com.hxl.scdjc_kotlin.view.ACache
//import com.hxl.scdjc_kotlin.view.GlideImageLoader
//import com.hxl.scdjc_kotlin.view.LoadingView
//import com.youth.banner.Banner
//import com.youth.banner.BannerConfig
//import com.youth.banner.Transformer
//import kotlinx.android.synthetic.main.fragment_home_.*
//
///**
// * Created by Administrator
// * on 2018/7/4 星期三.
// */
//class HomeFragment : BaseFragment() {
//    private var aCache: ACache? = null
//    private lateinit var headerView: View
//    private var columnList: RspDto.Column? = null
//    private var videoList = mutableListOf<RspDto.Video>()
//    private var newsList = mutableListOf<RspDto.Article>()
//    private var newsAdapter: NewsAdapter? = null
//    private var newsColumnAdapter: NewsColumnAdapter? = null
//    private var videoBigAdapter: VideoBigAdapter? = null
//    private var videoSmallAdapter: VideoSmallAdapter? = null
//    private var newsTimeAdapter: NewsTimeAdapter? = null
//    private var newsTextAdapter: NewsTextAdapter? = null
//    private lateinit var name: String
//    private var columnId: Int = 0
//    private var currentPage = 1
//
//    fun newInstance(): HomeFragment {
//        return HomeFragment()
//    }
//
//    override fun getLayout(): Int {
//        return R.layout.fragment_home_
//    }
//
//    override fun initView() {
//        columnList = (activity as MainTabActivity).loginBean.columnList[0]
//        name = columnList!!.templateName
//        columnId = columnList!!.id
//        aCache = ACache.get(mContext)
//        swipeRefresh.setColorSchemeResources(R.color.colorPrimary)
//        loadingView.setStatus(LoadingView.STATUS_LOADING)
//        initBannerAndColumnChild()
//        initRecycler()
//        swipeRefresh.setOnRefreshListener { refresh() }
//    }
//
//    private fun refresh() {
//        val adapter = recycler.adapter as BaseQuickAdapter<*, *>? ?: return
//        adapter.setEnableLoadMore(true)
//        currentPage = 1
//        if (name == AppConstant.TYPE_VIDEO_BIG || name == AppConstant.TYPE_VIDEO_SMALL) {
//            RetrofitManager.service
//                    .getVideoList(columnId, currentPage, AppConstant.PAGE_SIZE)
//                    .compose(RxUtil.rxSchedulerHelper())
//                    .subscribe(object : BaseSubscribe<VideoBean>() {
//                        override fun onSuccess(t: VideoBean?) {
//                            swipeRefresh.isRefreshing = false
//                            aCache!!.put("video_list$columnId", t, ACache.TIME_HOUR)
//                            videoList.clear()
//                            videoList.addAll(t!!.videoList!!)
//                            notifyRecycler()
//                        }
//
//                        override fun onFail(errMsg: String?) {
//                            swipeRefresh.isRefreshing = false
//                            ToastUtil.show(mContext, errMsg!!)
//                        }
//                    })
//        } else {
//            RetrofitManager.service
//                    .getArticleList(columnId, currentPage, AppConstant.PAGE_SIZE)
//                    .compose(RxUtil.rxSchedulerHelper())
//                    .subscribe(object : BaseSubscribe<ArticleBean>() {
//                        override fun onSuccess(t: ArticleBean?) {
//                            swipeRefresh.isRefreshing = false
//                            aCache!!.put("news_list$columnId", t, ACache.TIME_HOUR)
//                            newsList.clear()
//                            newsList.addAll(t!!.articleList!!)
//                            notifyRecycler()
//                        }
//
//                        override fun onFail(errMsg: String?) {
//                            swipeRefresh.isRefreshing = false
//                            ToastUtil.show(mContext, errMsg!!)
//                        }
//                    })
//        }
//    }
//
//    override fun lazyLoad() {
//        if (name == AppConstant.TYPE_VIDEO_BIG || name == AppConstant.TYPE_VIDEO_SMALL) {
//            //获取视频列表
//            val videoBean = aCache!!.getAsObject("video_list$columnId") as VideoBean?
//            if (videoBean == null) {
//                RetrofitManager.service
//                        .getVideoList(columnId, currentPage, AppConstant.PAGE_SIZE)
//                        .compose(RxUtil.rxSchedulerHelper())
//                        .subscribe(object : BaseSubscribe<VideoBean>() {
//                            override fun onSuccess(t: VideoBean?) {
//                                loadingView.setStatus(LoadingView.STATUS_DONE)
//                                recycler.visibility = View.VISIBLE
//                                aCache!!.put("video_list$columnId", t, ACache.TIME_HOUR)
//                                videoList.addAll(t!!.videoList!!)
//                                notifyRecycler()
//                            }
//
//                            override fun onFail(errMsg: String?) {
//                                loadingView.setStatus(LoadingView.STATUS_ERROR)
//                                ToastUtil.show(mContext, errMsg!!)
//                            }
//
//                        })
//
//            } else {
//                loadingView.setStatus(LoadingView.STATUS_DONE)
//                recycler.visibility = View.VISIBLE
//                videoList.addAll(videoBean.videoList!!)
//                notifyRecycler()
//            }
//        } else {
//            //获取文章列表
//            val articleBean = aCache!!.getAsObject("news_list$columnId") as ArticleBean?
//            if (articleBean == null) {
//                RetrofitManager.service
//                        .getArticleList(columnId, currentPage, AppConstant.PAGE_SIZE)
//                        .compose(RxUtil.rxSchedulerHelper())
//                        .subscribe(object : BaseSubscribe<ArticleBean>() {
//                            override fun onSuccess(t: ArticleBean?) {
//                                loadingView.setStatus(LoadingView.STATUS_DONE)
//                                recycler.visibility = View.VISIBLE
//                                aCache!!.put("news_list$columnId", t, ACache.TIME_HOUR)
//                                newsList.addAll(t!!.articleList!!)
//                                notifyRecycler()
//                            }
//
//                            override fun onFail(errMsg: String?) {
//                                loadingView.setStatus(LoadingView.STATUS_ERROR)
//                                ToastUtil.show(mContext, errMsg!!)
//                            }
//
//                        })
//            } else {
//                loadingView.setStatus(LoadingView.STATUS_DONE)
//                recycler.visibility = View.VISIBLE
//                newsList.addAll(articleBean.articleList!!)
//                notifyRecycler()
//            }
//        }
//
//    }
//
//    private fun notifyRecycler() {
//        when (name) {
//            AppConstant.TYPE_VIDEO_SMALL -> {
//                videoSmallAdapter!!.setNewData(videoList)
//                videoSmallAdapter!!.notifyDataSetChanged()
//            }
//            AppConstant.TYPE_VIDEO_BIG -> {
//                videoBigAdapter!!.setNewData(videoList)
//                videoBigAdapter!!.notifyDataSetChanged()
//            }
//            AppConstant.TYPE_IMG_BIG -> {
//                newsAdapter!!.setNewData(newsList)
//                newsAdapter!!.notifyDataSetChanged()
//            }
//            AppConstant.TYPE_IMG_MEDIUM -> {
//                newsAdapter!!.setNewData(newsList)
//                newsAdapter!!.notifyDataSetChanged()
//            }
//            AppConstant.TYPE_IMG_SMALL -> {
//                newsColumnAdapter!!.setNewData(newsList)
//                newsColumnAdapter!!.notifyDataSetChanged()
//            }
//            AppConstant.TYPE_TEXT -> {
//                newsTextAdapter!!.setNewData(newsList)
//                newsTextAdapter!!.notifyDataSetChanged()
//            }
//            AppConstant.TYPE_TIME -> {
//                newsTimeAdapter!!.setNewData(newsList)
//                newsTimeAdapter!!.notifyDataSetChanged()
//            }
//        }
//    }
//
//    private fun initBannerAndColumnChild() {
//        headerView = LayoutInflater.from(mContext).inflate(R.layout.layout_home_header, null)
//        val mBanner: Banner = headerView.findViewById(R.id.banner)
//        val recyclerTab: RecyclerView = headerView.findViewById(R.id.recyclerView)
//        val imgList = mutableListOf<String>()
//        for (i in columnList!!.advertisingList) {
//            imgList.add(RetrofitManager.baseUrl + i.imgPath)
//        }
//        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
//                .setImageLoader(GlideImageLoader())
//                .setImages(imgList)
//                .setBannerAnimation(Transformer.DepthPage)
//                .isAutoPlay(true)
//                .setDelayTime(3500)
//                .setIndicatorGravity(BannerConfig.CENTER)
//                .start()
//
//        recyclerTab.isNestedScrollingEnabled = false  //不可滑动
//        val childrenList = mutableListOf<RspDto.Column>()
//        childrenList.addAll(columnList!!.childrenColumn)
//        val columnAdapter = ColumnAdapter(mContext, childrenList)
//        val layoutManager = GridLayoutManager(mContext, 3)
//        layoutManager.orientation = LinearLayoutManager.VERTICAL
//        recyclerTab.layoutManager = layoutManager
//        recyclerTab.adapter = columnAdapter
//
//        columnAdapter.setOnItemClickListener(object : ColumnAdapter.OnItemClickListener {
//            override fun onItemClick(view: View, position: Int) {
//                val (name1, _, checkImgPath, _, _, templateName, id) = childrenList[position]
//                val intent: Intent
//                if (TextUtils.isEmpty(checkImgPath)) {
//                    intent = Intent(mContext, NewsListActivity::class.java)
//                    intent.putExtra(AppConstant.COLUMN_ID, id)
//                    intent.putExtra(AppConstant.TYPE, templateName)
//                    intent.putExtra(AppConstant.COLUMN_TITLE, name1)
//                } else {
//                    intent = Intent(mContext, WebActivity::class.java)
//                    intent.putExtra(AppConstant.LINK_URL, "http://v.djc021.com")
//                    intent.putExtra(AppConstant.LINK_TITLE, "直播")
//                }
//                startActivity(intent)
//            }
//        })
//    }
//
//    private fun initRecycler() {
//        val layoutManager = LinearLayoutManager(mContext)
//        layoutManager.orientation = LinearLayoutManager.VERTICAL
//        recycler.layoutManager = layoutManager
//
//        when (name) {
//            AppConstant.TYPE_IMG_SMALL -> {
//                newsColumnAdapter = NewsColumnAdapter(newsList, true)
//                newsColumnAdapter!!.addHeaderView(headerView)
//                recycler.adapter = newsColumnAdapter
//            }
//            AppConstant.TYPE_IMG_MEDIUM -> {
//                newsAdapter = NewsAdapter(R.layout.item_news_custom, newsList, true)
//                newsAdapter!!.addHeaderView(headerView)
//                recycler.adapter = newsAdapter
//            }
//            AppConstant.TYPE_IMG_BIG -> {
//                newsAdapter = NewsAdapter(R.layout.item_news_big, newsList, true)
//                newsAdapter!!.addHeaderView(headerView)
//                recycler.adapter = newsAdapter
//            }
//            AppConstant.TYPE_VIDEO_BIG -> {
//                videoBigAdapter = VideoBigAdapter(videoList, true)
//                videoBigAdapter!!.addHeaderView(headerView)
//                recycler.adapter = videoBigAdapter
//            }
//            AppConstant.TYPE_VIDEO_SMALL -> {
//                videoSmallAdapter = VideoSmallAdapter(videoList, true)
//                videoSmallAdapter!!.addHeaderView(headerView)
//                recycler.adapter = videoSmallAdapter
//            }
//            AppConstant.TYPE_TEXT -> {
//                newsTextAdapter = NewsTextAdapter(newsList)
//                val view = LayoutInflater.from(mContext)
//                        .inflate(R.layout.layout_text_header, null)
//                val tvTime = view.findViewById<TextView>(R.id.tv_time)
//                tvTime.text = CommonUtil.getSystemTimeAndWeek()
//                newsTextAdapter!!.addHeaderView(headerView)
//                newsTextAdapter!!.addHeaderView(view)
//                recycler.adapter = newsTextAdapter
//            }
//            AppConstant.TYPE_TIME -> {
//                newsTimeAdapter = NewsTimeAdapter(newsList, true)
//                newsTimeAdapter!!.addHeaderView(headerView)
//                recycler.adapter = newsTimeAdapter
//            }
//        }
//
//        val adapter = recycler.adapter as BaseQuickAdapter<*, *>? ?: return
//
//
//        adapter.setOnItemClickListener { _, view, position ->
//            if (name == AppConstant.TYPE_TIME) return@setOnItemClickListener
//
//            if (name == AppConstant.TYPE_TEXT) {
//                val tvContent = view.findViewById<TextView>(R.id.tv_content)
//                if (tvContent.maxLines == 3) {
//                    tvContent.maxLines = 50
//                    tvContent.ellipsize = null
//                } else {
//                    tvContent.maxLines = 3
//                    tvContent.ellipsize = TextUtils.TruncateAt.END
//                }
//                return@setOnItemClickListener
//            }
//
//            val intent: Intent
//            if (name.contains(AppConstant.TYPE_VIDEO)) {
//                intent = Intent(mContext, VideoActivity::class.java)
//                intent.putExtra(AppConstant.VIDEO_ID, videoList[position].id)
//            } else {
//                intent = Intent(mContext, NewsDetailActivity::class.java)
//                intent.putExtra(AppConstant.ARTICLE_ID, newsList[position].id)
//                intent.putExtra(AppConstant.LINK_TITLE, newsList[position].title)
//            }
//            startActivity(intent)
//        }
//
//        adapter.setOnLoadMoreListener { loadMore(adapter) }
//
//    }
//
//    /**
//     * 加载更多
//     */
//    private fun loadMore(adapter: BaseQuickAdapter<*, *>) {
//        currentPage++
//        if (name == AppConstant.TYPE_VIDEO_SMALL || name == AppConstant.TYPE_VIDEO_BIG) {
//            RetrofitManager.service
//                    .getVideoList(columnId, currentPage, AppConstant.PAGE_SIZE)
//                    .compose(RxUtil.rxSchedulerHelper())
//                    .subscribe(object : BaseSubscribe<VideoBean>() {
//                        override fun onSuccess(t: VideoBean?) {
//                            if (t!!.videoList == null || t.videoList!!.isEmpty()) {
//                                adapter.loadMoreEnd()
//                            } else {
//                                adapter.loadMoreComplete()
//                                videoList.addAll(t.videoList!!)
//                                notifyRecycler()
//                            }
//                        }
//
//                        override fun onFail(errMsg: String?) {
//                            adapter.loadMoreFail()
//                            ToastUtil.show(mContext, errMsg!!)
//                        }
//                    })
//        } else {
//            RetrofitManager.service
//                    .getArticleList(columnId, currentPage, AppConstant.PAGE_SIZE)
//                    .compose(RxUtil.rxSchedulerHelper())
//                    .subscribe(object : BaseSubscribe<ArticleBean>() {
//                        override fun onSuccess(t: ArticleBean?) {
//                            if (t!!.articleList == null || t.articleList!!.isEmpty()) {
//                                adapter.loadMoreEnd()
//                            } else {
//                                adapter.loadMoreComplete()
//                                newsList.addAll(t.articleList!!)
//                                notifyRecycler()
//                            }
//                        }
//
//                        override fun onFail(errMsg: String?) {
//                            adapter.loadMoreFail()
//                            ToastUtil.show(mContext, errMsg!!)
//                        }
//                    })
//        }
//    }
//}