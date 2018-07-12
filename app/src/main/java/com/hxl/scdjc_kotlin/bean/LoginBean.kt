package com.hxl.scdjc_kotlin.bean

import com.hxl.scdjc_kotlin.bean.base.RspDto
import java.io.Serializable

/**
 * Created by Administrator
 * on 2018/7/4 星期三.
 */
data class LoginBean(var columnList: List<RspDto.Column>) : Serializable