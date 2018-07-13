package com.hxl.scdjc_kotlin.ui.activity

import com.hxl.scdjc_kotlin.R
import com.hxl.scdjc_kotlin.base.BaseActivity

///**
// * Created by Administrator
// * on 2018/7/9 星期一.
// */
class NewsListActivity : BaseActivity() {
    override fun loadData() {
    }

    //    private var videoList = mutableListOf<RspDto.Video>()
//    private var newsList = mutableListOf<RspDto.Article>()
//    private var newsAdapter: NewsAdapter? = null
//    private var newsColumnAdapter: NewsColumnAdapter? = null
//    private var videoBigAdapter: VideoBigAdapter? = null
//    private var videoSmallAdapter: VideoSmallAdapter? = null
//    private var newsTimeAdapter: NewsTimeAdapter? = null
//    private var newsTextAdapter: NewsTextAdapter? = null
//    private var tvTime: TextView? = null
//    private var aCache: ACache? = null
//    private lateinit var type: String
//    private var columnId: Int = 0
//    private var currentPage = 1
//
    override fun getLayout(): Int = R.layout.activity_news_list

    override fun initView() {
//        aCache = ACache.get(this@NewsListActivity)
//        iv_back.visibility = View.VISIBLE
//        type = intent.extras.getString(AppConstant.TYPE)
//        columnId = intent.extras.getInt(AppConstant.COLUMN_ID)
//        val title = intent.extras.getString(AppConstant.COLUMN_TITLE)
//        tv_title.text = title
//        initRecycler()
//        getDataFromType()
//        swipeRefresh.setOnRefreshListener { refresh() }
    }
//
//    private fun refresh() {
//        val adapter = recycler.adapter as BaseQuickAdapter<*, *>? ?: return
//        adapter.setEnableLoadMore(true)
//        currentPage = 1
//        if (type == AppConstant.TYPE_VIDEO_BIG || type == AppConstant.TYPE_VIDEO_SMALL) {
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
//                            ToastUtil.show(this@NewsListActivity, errMsg!!)
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
//                            ToastUtil.show(this@NewsListActivity, errMsg!!)
//                        }
//                    })
//        }
//    }
//
//    private fun getDataFromType() {
//        if (type == AppConstant.TYPE_VIDEO_BIG || type == AppConstant.TYPE_VIDEO_SMALL) {
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
//                                ToastUtil.show(this@NewsListActivity, errMsg!!)
//                            }
//                        })
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
//                                ToastUtil.show(this@NewsListActivity, errMsg!!)
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
//        when (type) {
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
//                tvTime!!.text = CommonUtil.getSystemTimeAndWeek()
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
//    private fun initRecycler() {
//        val layoutManager = LinearLayoutManager(this@NewsListActivity)
//        layoutManager.orientation = LinearLayoutManager.VERTICAL
//        recycler.layoutManager = layoutManager
//
//        when (type) {
//            AppConstant.TYPE_IMG_SMALL -> {
//                newsColumnAdapter = NewsColumnAdapter(newsList, false)
//                recycler.adapter = newsColumnAdapter
//            }
//            AppConstant.TYPE_IMG_MEDIUM -> {
//                newsAdapter = NewsAdapter(R.layout.item_news_custom, newsList, false)
//                recycler.adapter = newsAdapter
//            }
//            AppConstant.TYPE_IMG_BIG -> {
//                newsAdapter = NewsAdapter(R.layout.item_news_big, newsList, false)
//                recycler.adapter = newsAdapter
//            }
//            AppConstant.TYPE_VIDEO_BIG -> {
//                videoBigAdapter = VideoBigAdapter(videoList, false)
//                recycler.adapter = videoBigAdapter
//            }
//            AppConstant.TYPE_VIDEO_SMALL -> {
//                videoSmallAdapter = VideoSmallAdapter(videoList, false)
//                recycler.adapter = videoSmallAdapter
//            }
//            AppConstant.TYPE_TEXT -> {
//                newsTextAdapter = NewsTextAdapter(newsList)
//                val view = LayoutInflater.from(this).inflate(R.layout.layout_text_header, null)
//                tvTime = view.findViewById(R.id.tv_time)
//                tvTime!!.text = CommonUtil.getSystemTimeAndWeek()
//                newsTextAdapter!!.addHeaderView(view)
//                recycler.adapter = newsTextAdapter
//            }
//            AppConstant.TYPE_TIME -> {
//                newsTimeAdapter = NewsTimeAdapter(newsList, false)
//                recycler.adapter = newsTimeAdapter
//            }
//        }
//
//        val adapter = recycler.adapter as BaseQuickAdapter<*, *>? ?: return
//        adapter.setOnItemClickListener { _, view, position ->
//            if (type == AppConstant.TYPE_TIME) return@setOnItemClickListener
//
//            if (type == AppConstant.TYPE_TEXT) {
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
//            if (type.contains(AppConstant.TYPE_VIDEO)) {
//                intent = Intent(this@NewsListActivity, VideoActivity::class.java)
//                intent.putExtra(AppConstant.VIDEO_ID, videoList[position].id)
//            } else {
//                intent = Intent(this@NewsListActivity, NewsDetailActivity::class.java)
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
//    private fun loadMore(adapter: BaseQuickAdapter<*, *>) {
//        currentPage++
//        if (type == AppConstant.TYPE_VIDEO_SMALL || type == AppConstant.TYPE_VIDEO_BIG) {
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
//                            ToastUtil.show(this@NewsListActivity, errMsg!!)
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
//                            ToastUtil.show(this@NewsListActivity, errMsg!!)
//                        }
//                    })
//        }
//    }
}