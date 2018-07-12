package com.hxl.scdjc_kotlin.app

import android.app.Activity
import android.app.Application
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import java.util.ArrayList

/**
 * Created by Administrator
 * on 2018/6/27 星期三.
 */
open class App : Application() {
    private var mActivities: MutableList<Activity> = ArrayList()

    companion object {
        lateinit var sApp: App

        fun addActivity(activity: Activity) {
            sApp.mActivities.add(activity)

        }

        fun removeActivity(activity: Activity) {
            sApp.mActivities.remove(activity)
        }

        fun clearActivities() {
            for (activity in sApp.mActivities) {
                activity.finish()

            }
            sApp.mActivities.clear()
        }

        fun exit() {
            clearActivities()
            System.exit(0)
        }
    }

    override fun onCreate() {
        super.onCreate()
        sApp = this
        Logger.addLogAdapter(AndroidLogAdapter())
    }


}