package com.hxl.scdjc_kotlin.bean

import com.hxl.scdjc_kotlin.bean.base.RspDto
import java.io.Serializable

/**
 * Created by Administrator
 * on 2018/7/5 星期四.
 */
data class VideoBean(var templateName: String, var videoList: List<RspDto.Video>?) : Serializable