package com.hxl.scdjc_kotlin.net.cookie

import android.content.Context
import android.webkit.CookieManager
import android.webkit.CookieSyncManager

import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl

/**
 * @author lw
 * @date 2018/1/25
 */

class CookiesManager : CookieJar {

    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        if (cookies.size > 0) {
            for (item in cookies) {
                COOKIE_STORE.add(url, item)
            }
        }
    }

    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        return COOKIE_STORE.get(url)
    }

    companion object {

        private val COOKIE_STORE = PersistentCookieStore()

        /**
         * 清除所有cookie
         */
        fun clearAllCookies() {
            COOKIE_STORE.removeAll()
        }

        /**
         * 清除指定cookie
         *
         * @param url    HttpUrl
         * @param cookie Cookie
         * @return if clear cookies
         */
        fun clearCookies(url: HttpUrl, cookie: Cookie): Boolean {
            return COOKIE_STORE.remove(url, cookie)
        }

        /**
         * 获取cookies
         *
         * @return List<Cookie>
        </Cookie> */
        val cookies: List<Cookie>?
            get() = COOKIE_STORE.getCookies()


        /**
         * cookie同步webView
         */
        fun syncCookieToWebView(context: Context, url: String) {
            CookieSyncManager.createInstance(context)
            val cm = CookieManager.getInstance()
            val cookies = cookies
            cm.setAcceptCookie(true)
            if (cookies != null) {
                for (cookie in cookies) {
                    cm.setCookie(url, cookie.toString())//注意端口号和域名，这种方式可以同步所有cookie，包括sessionid
                }
            }
            CookieSyncManager.getInstance().sync()
        }
    }
}
