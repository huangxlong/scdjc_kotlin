package com.hxl.scdjc_kotlin.net.response

/**
 * 0：成功，1：失败
 * Created by Administrator
 * on 2018/6/27 星期三.
 */
data class BaseRsp<T>(var status: Int, var message: String, var data: T? = null)





