package com.hxl.scdjc_kotlin.app

import android.app.Application
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger

/**
 * Created by Administrator
 * on 2018/6/27 星期三.
 */
open class App : Application() {


    companion object {
        lateinit var sApp: App
    }

    override fun onCreate() {
        super.onCreate()
        sApp = this
        Logger.addLogAdapter(AndroidLogAdapter())
    }
}