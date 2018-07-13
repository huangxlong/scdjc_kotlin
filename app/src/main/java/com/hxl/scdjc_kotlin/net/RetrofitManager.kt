package com.hxl.scdjc_kotlin.net

import android.util.Log
import com.google.gson.Gson
import com.hxl.scdjc_kotlin.app.ApiService
import com.hxl.scdjc_kotlin.app.UrlConstant
import com.hxl.scdjc_kotlin.net.cookie.CookiesManager
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
    private var okHttpClient: OkHttpClient? = null
    private var retrofit: Retrofit? = null

    val service: ApiService by lazy { getHttp()!!.create(ApiService::class.java) }

    private fun getHttp(): Retrofit? {
        if (retrofit == null) {
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
                        .baseUrl(UrlConstant.BASE_URL)
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create(Gson()))
                        .client(okHttpClient!!)
                        .build()
            }
        }
        return retrofit
    }

}