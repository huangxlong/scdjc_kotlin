package com.hxl.scdjc_kotlin.http

import android.util.Log
import com.google.gson.Gson
import com.hxl.scdjc_kotlin.http.cookie.CookiesManager
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by Administrator
 * on 2018/6/27 星期三.
 */
object RetrofitManager {
    private const val timeout: Long = 30
    const val baseUrl = "http://pro.djc888.com/"
    private var okHttpClient: OkHttpClient? = null
    private var retrofit: Retrofit? = null

    val service: HttpService by lazy { getHttp()!!.create(HttpService::class.java) }

    private fun getHttp(): Retrofit? {
        if (okHttpClient == null) {
            synchronized(RetrofitManager::class.java) {
                if (retrofit == null) {
                    val httpLoggingInterceptor = HttpLoggingInterceptor() {
                        Log.d("Request", it)
                    }
                    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

                    okHttpClient = OkHttpClient().newBuilder()
                            .connectTimeout(timeout, TimeUnit.SECONDS)
                            .readTimeout(timeout, TimeUnit.SECONDS)
                            .writeTimeout(timeout, TimeUnit.SECONDS)
                            .addInterceptor(httpLoggingInterceptor)
                            .cookieJar(CookiesManager())
                            .build()
                }

                retrofit = Retrofit.Builder()
                        .baseUrl(baseUrl)
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create(Gson()))
                        .client(okHttpClient)
                        .build()
            }
        }
        return retrofit
    }

}