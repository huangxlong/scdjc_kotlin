package com.hxl.scdjc_kotlin.ui.activity


//
import android.annotation.SuppressLint
import android.app.Activity
import android.util.Log
import android.view.View
import android.webkit.*
import com.bumptech.glide.Glide
import com.hxl.scdjc_kotlin.R
import com.hxl.scdjc_kotlin.app.AppConstant
import com.hxl.scdjc_kotlin.base.BaseActivity
import com.hxl.scdjc_kotlin.bean.ArticleDetailBean
import com.hxl.scdjc_kotlin.http.BaseSubscribe
import com.hxl.scdjc_kotlin.http.RetrofitManager
import com.hxl.scdjc_kotlin.http.cookie.CookiesManager
import com.hxl.scdjc_kotlin.util.RxUtil
import com.hxl.scdjc_kotlin.util.ToastUtil
import com.hxl.scdjc_kotlin.view.LoadingView
import com.hxl.scdjc_kotlin.view.PreviewImageDialog
import kotlinx.android.synthetic.main.activity_news_detail.*

//
///**
// * Created by Administrator
// * on 2018/7/5 星期四.
// */
class NewsDetailActivity : BaseActivity() {
    override fun loadData() {
    }

    //    private var newsDetail: ArticleDetailBean? = null
//    private var articleId: Int = 0
    override fun getLayout(): Int {
        return R.layout.activity_news_detail
    }

    //
    override fun initView() {
//        articleId = intent.extras.get(AppConstant.ARTICLE_ID) as Int
//        val title = intent.extras.get(AppConstant.LINK_TITLE) as String
//        tv_title.text = title
//        initWebView()
//        getArticleDetail()
    }
//
//    private fun getArticleDetail() {
//        RetrofitManager.service
//                .getArticleDetail(articleId)
//                .compose(RxUtil.rxSchedulerHelper())
//                .subscribe(object : BaseSubscribe<ArticleDetailBean>() {
//                    override fun onSuccess(t: ArticleDetailBean?) {
//                        if (t == null) {
//                            loadingView.setStatus(LoadingView.STATUS_EMPTY)
//                        } else {
//                            loadingView.setStatus(LoadingView.STATUS_DONE)
//                            layout_main.visibility = View.VISIBLE
//                            newsDetail = t
//                            setDetailData()
//                        }
//                    }
//
//                    override fun onFail(errMsg: String?) {
//                        loadingView.setStatus(LoadingView.STATUS_ERROR)
//                        ToastUtil.show(this@NewsDetailActivity, errMsg!!)
//                    }
//
//                })
//
//    }
//
//    @SuppressLint("SetTextI18n")
//    private fun setDetailData() {
//        val article = newsDetail!!.article
//        Glide.with(this@NewsDetailActivity)
//                .load(RetrofitManager.baseUrl + article.employee.headImg)
//                .into(iv_photo)
//        tv_author.text = "投资顾问:" + article.employee.realName
//        tv_number.text = "(执业号:" + article.employee.practiceNum + ")"
//        tv_des.text = article.employee.employeeDetails
//        tv_teacher_read.text = "总阅读量:" + article.employee.readingQuantity
//        tv_teacher_zan.text = "总点赞量:" + article.employee.thumbUpQuantity
//
//        tv_article_title.text = article.title
//        tv_time.text = article.createTimeStr
//        tv_from.text = "来源:" + article.source
//
//        val url = RetrofitManager.baseUrl + "djc/article/v/jsp?id=" + articleId
//        CookiesManager.syncCookieToWebView(this@NewsDetailActivity, url)
//        webView.loadUrl(url)
//
//        if (newsDetail!!.upArticle == null) tv_up.visibility = View.GONE else tv_up.text = "上一篇:" + newsDetail!!.upArticle!!.title
//        if (newsDetail!!.nextArticle == null) tv_next.visibility = View.GONE else tv_next.text = "下一篇:" + newsDetail!!.nextArticle!!.title
//        if (newsDetail!!.advertising == null) iv_advertising.visibility = View.GONE else Glide.with(this@NewsDetailActivity).load(RetrofitManager.baseUrl + newsDetail!!.advertising!!.imgPath).into(iv_advertising)
//    }
//
//    @SuppressLint("SetJavaScriptEnabled", "AddJavascriptInterface")
//    private fun initWebView() {
//        webView.clearCache(true)
//        val settings = webView.settings
//        settings.javaScriptEnabled = true
//        settings.javaScriptCanOpenWindowsAutomatically = true
//        settings.setSupportZoom(false)
//        settings.useWideViewPort = true
//        settings.loadWithOverviewMode = true
//        settings.setAppCacheEnabled(false)
//        settings.domStorageEnabled = false
//        settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
//        settings.cacheMode = WebSettings.LOAD_NO_CACHE
//
//        webView.webViewClient = object : WebViewClient() {
//            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
//                view!!.loadUrl(url)
//                return true
//            }
//
//            override fun onPageFinished(view: WebView?, url: String?) {
//                super.onPageFinished(view, url)
//                view!!.clearCache(true)
//                imgReset()
//                addImageClickListener()
//            }
//        }
//        webView.addJavascriptInterface(JavaScriptInterface(this@NewsDetailActivity), "imagelistner")
//    }
//
//    //点击图片回调方法
//    class JavaScriptInterface(private val context: Activity) {
//        //必须添加注解,否则无法响应
//        @JavascriptInterface
//        fun openImage(img: String) {
//            Log.i("TAG", "响应点击事件!")
//            val imageDialog = PreviewImageDialog(context, img)
//            imageDialog.show()
//        }
//    }
//
//
//    /**
//     * 对图片进行重置大小，宽度就是手机屏幕宽度，高度根据宽度比便自动缩放
//     **/
//    private fun imgReset() {
//        webView.loadUrl("javascript:(function(){" + "var objs = document.getElementsByTagName('img'); " + "for(var i=0;i<objs.length;i++)  " + "{" + "var img = objs[i];   " + "img.style.maxWidth = '100%'; img.style.height = 'auto';" + "}" + "})()")
//    }
//
//    private fun addImageClickListener() {
//        // 这段js函数的功能就是，遍历所有的img节点，并添加onclick函数，函数的功能是在图片点击的时候调用本地java接口并传递url过去
//        webView.loadUrl("javascript:(function(){" + "var objs = document.getElementsByTagName(\"img\"); " + "for(var i=0;i<objs.length;i++)  " + "{" + "    objs[i].onclick=function()  " + "    {  " + "        window.imagelistner.openImage(this.src);  " + "    }  " + "}" + "})()")
//    }
}