package com.hxl.scdjc_kotlin.ui.activity

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.text.Html
import android.view.KeyEvent
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.hxl.scdjc_kotlin.R
import com.hxl.scdjc_kotlin.app.AppConstant
import com.hxl.scdjc_kotlin.base.BaseActivity
import com.hxl.scdjc_kotlin.http.cookie.CookiesManager
import kotlinx.android.synthetic.main.activity_web.*
import kotlinx.android.synthetic.main.layout_title.*


/**
 * Created by Administrator
 * on 2018/5/9 星期三.
 */
class WebActivity : BaseActivity(), View.OnClickListener {
    override fun loadData() {
    }

    override fun getLayout(): Int = R.layout.activity_web

    override fun initView() {
        val articleLink = intent.extras.getString(AppConstant.LINK_URL)
        val articleTitle = intent.extras.getString(AppConstant.LINK_TITLE)
        iv_back.visibility = View.VISIBLE
        tv_title.text = Html.fromHtml(articleTitle)
        initWebView()
        CookiesManager.syncCookieToWebView(this, articleLink)
        webView.loadUrl(articleLink)
    }

    override fun onClick(v: View?) = onBackPressed()

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView() {
        webView.clearCache(true)
        val settings = webView.settings
        settings.javaScriptCanOpenWindowsAutomatically = true
        settings.javaScriptEnabled = true
        settings.setSupportZoom(false)
        settings.useWideViewPort = true
        settings.loadWithOverviewMode = true
        settings.setAppCacheEnabled(false)
        settings.domStorageEnabled = true
        settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
        settings.cacheMode = WebSettings.LOAD_NO_CACHE

        webView.webViewClient = MyWebClient()
        webView.webChromeClient = MyWebChromeClient()
    }


    internal inner class MyWebChromeClient : WebChromeClient() {

        override fun onProgressChanged(view: WebView, newProgress: Int) {
            if (progressBar == null) return
            if (newProgress == 100) {
                progressBar.visibility = View.GONE//加载完网页进度条消失
            } else {
                progressBar.visibility = View.VISIBLE//开始加载网页时显示进度条
                progressBar.progress = newProgress//设置进度值
            }

        }
    }


    inner class MyWebClient : WebViewClient() {
        override fun onPageStarted(view: WebView, url: String, favicon: Bitmap) {
            super.onPageStarted(view, url, favicon)
        }

        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            view.loadUrl(url)
            return true
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onDestroy() {
        webView.removeAllViews()
        webView.clearCache(true)
        super.onDestroy()
    }
}
