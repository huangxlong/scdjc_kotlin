package com.hxl.scdjc_kotlin.util

import com.hazz.kotlinmvp.rx.scheduler.IoMainScheduler


/**
 * Created by Administrator
 * on 2018/6/27 星期三.
 */
object RxUtil {
    fun <T> rxSchedulerHelper(): IoMainScheduler<T> {
        return IoMainScheduler()
    }


}