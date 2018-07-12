package com.hxl.scdjc_kotlin.ui.activity


import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.webkit.*
import com.bumptech.glide.Glide
import com.hazz.kotlinmvp.net.exception.ErrorStatus
import com.hxl.scdjc_kotlin.R
import com.hxl.scdjc_kotlin.app.AppConstant
import com.hxl.scdjc_kotlin.base.BaseActivity
import com.hxl.scdjc_kotlin.bean.ArticleDetailBean
import com.hxl.scdjc_kotlin.http.RetrofitManager
import com.hxl.scdjc_kotlin.http.cookie.CookiesManager
import com.hxl.scdjc_kotlin.mvp.contract.NewsDetailContract
import com.hxl.scdjc_kotlin.mvp.presenter.NewsDetailPresenter
import com.hxl.scdjc_kotlin.util.ToastUtil
import com.hxl.scdjc_kotlin.view.PreviewImageDialog
import kotlinx.android.synthetic.main.activity_news_detail.*


/**
 * Created by Administrator
 * on 2018/7/5 星期四.
 */
class NewsDetailActivity : BaseActivity(), NewsDetailContract.View, View.OnClickListener {
    private val mPresenter by lazy { NewsDetailPresenter() }
    private lateinit var detail: ArticleDetailBean
    private var articleId: Int = 0

    override fun getLayout(): Int = R.layout.activity_news_detail

    /**
     * 初始化view
     */
    override fun initView() {
        mPresenter.attachView(this)
        mLayoutStatusView = multipleStatusView
        articleId = intent.extras.get(AppConstant.ARTICLE_ID) as Int
        val title = intent.extras.get(AppConstant.LINK_TITLE) as String
        tv_title.text = title
        initWebView()
        iv_zan.setOnClickListener(this)
        iv_back.setOnClickListener(this)
        tv_des.setOnClickListener(this)
        tv_next.setOnClickListener(this)
        tv_up.setOnClickListener(this)
        iv_advertising.setOnClickListener(this)
        scrollView.setOnTouchListener { _, _ ->
            changeDesLines(true)
            false
        }
    }

    /**
     * 请求数据
     */
    override fun loadData() {
        mPresenter.getNewsDetail(articleId)
    }

    override fun showLoading() {
        multipleStatusView.showLoading()
    }

    override fun dismissLoading() {
        multipleStatusView.showContent()
    }

    @SuppressLint("SetTextI18n")
    override fun setNewsDetail(articleDetailBean: ArticleDetailBean) {
        detail = articleDetailBean
        val article = articleDetailBean.article
        Glide.with(this@NewsDetailActivity)
                .load(RetrofitManager.baseUrl + article.employee.headImg)
                .into(iv_photo)
        tv_author.text = "投资顾问:" + article.employee.realName
        tv_number.text = "(执业号:" + article.employee.practiceNum + ")"
        tv_des.text = article.employee.employeeDetails
        tv_teacher_read.text = "总阅读量:" + article.employee.readingQuantity
        tv_teacher_zan.text = "总点赞量:" + article.employee.thumbUpQuantity

        tv_article_title.text = article.title
        tv_time.text = article.createTimeStr
        tv_from.text = "来源:" + article.source

        val url = RetrofitManager.baseUrl + "djc/article/v/jsp?id=" + articleId
        CookiesManager.syncCookieToWebView(this@NewsDetailActivity, url)
        webView.loadUrl(url)

        if (articleDetailBean.upArticle == null) tv_up.visibility = View.GONE else tv_up.text = "上一篇:" + articleDetailBean.upArticle!!.title
        if (articleDetailBean.nextArticle == null) tv_next.visibility = View.GONE else tv_next.text = "下一篇:" + articleDetailBean.nextArticle!!.title
        if (articleDetailBean.advertising == null) iv_advertising.visibility = View.GONE else Glide.with(this@NewsDetailActivity).load(RetrofitManager.baseUrl + articleDetailBean.advertising!!.imgPath).into(iv_advertising)
    }

    override fun thumpUpResult(result: String) {
        ToastUtil.show(this@NewsDetailActivity, "点赞成功")
    }

    override fun showError(errorMsg: String, errorCode: Int) {
        ToastUtil.show(this@NewsDetailActivity, errorMsg)
        if (errorCode == ErrorStatus.NETWORK_ERROR) {
            multipleStatusView.showNoNetwork()
        } else {
            multipleStatusView.showError()
        }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.tv_zan -> mPresenter.thumpUp(articleId)
            R.id.iv_back -> onBackPressed()
            R.id.tv_des -> changeDesLines(false)
            R.id.tv_next -> goUpOrNext(detail.nextArticle!!.id, detail.nextArticle!!.title)
            R.id.tv_up -> goUpOrNext(detail.upArticle!!.id, detail.upArticle!!.title)
            R.id.iv_advertising -> {
            }
        }
    }

    private fun goUpOrNext(articleId: Int, title: String) {
        startActivity(
                Intent(this@NewsDetailActivity, NewsDetailActivity::class.java)
                        .putExtra(AppConstant.ARTICLE_ID, articleId)
                        .putExtra(AppConstant.LINK_TITLE, title)
        )
    }

    private fun changeDesLines(isScroll: Boolean) {
        if (isScroll) {
            if (tv_des.maxLines > 3) {
                tv_des.maxLines = 3
                tv_des.ellipsize = TextUtils.TruncateAt.END
            }
        } else {
            if (tv_des.maxLines > 3) {
                tv_des.maxLines = 3
                tv_des.ellipsize = TextUtils.TruncateAt.END
            } else {
                tv_des.maxLines = 50
                tv_des.ellipsize = null
            }
        }
    }


    @SuppressLint("SetJavaScriptEnabled", "AddJavascriptInterface")
    private fun initWebView() {
        webView.clearCache(true)
        val settings = webView.settings
        settings.javaScriptEnabled = true
        settings.javaScriptCanOpenWindowsAutomatically = true
        settings.setSupportZoom(false)
        settings.useWideViewPort = true
        settings.loadWithOverviewMode = true
        settings.setAppCacheEnabled(false)
        settings.domStorageEnabled = false
        settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
        settings.cacheMode = WebSettings.LOAD_NO_CACHE

        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view!!.loadUrl(url)
                return true
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                view!!.clearCache(true)
                imgReset()
                addImageClickListener()
            }
        }
        webView.addJavascriptInterface(JavaScriptInterface(this@NewsDetailActivity), "imagelistner")
    }

    //点击图片回调方法
    class JavaScriptInterface(private val context: Activity) {
        //必须添加注解,否则无法响应
        @JavascriptInterface
        fun openImage(img: String) {
            Log.i("TAG", "响应点击事件!")
            val imageDialog = PreviewImageDialog(context, img)
            imageDialog.show()
        }
    }


    /**
     * 对图片进行重置大小，宽度就是手机屏幕宽度，高度根据宽度比便自动缩放
     **/
    private fun imgReset() {
        webView.loadUrl("javascript:(function(){" + "var objs = document.getElementsByTagName('img'); " + "for(var i=0;i<objs.length;i++)  " + "{" + "var img = objs[i];   " + "img.style.maxWidth = '100%'; img.style.height = 'auto';" + "}" + "})()")
    }

    private fun addImageClickListener() {
        // 这段js函数的功能就是，遍历所有的img节点，并添加onclick函数，函数的功能是在图片点击的时候调用本地java接口并传递url过去
        webView.loadUrl("javascript:(function(){" + "var objs = document.getElementsByTagName(\"img\"); " + "for(var i=0;i<objs.length;i++)  " + "{" + "    objs[i].onclick=function()  " + "    {  " + "        window.imagelistner.openImage(this.src);  " + "    }  " + "}" + "})()")
    }
}