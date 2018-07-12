package com.hxl.scdjc_kotlin.bean

import com.hxl.scdjc_kotlin.bean.base.RspDto
import java.io.Serializable

/**
 * Created by Administrator
 * on 2018/7/5 星期四.
 */
data class ArticleBean(var articleList: List<RspDto.Article>?) : Serializable