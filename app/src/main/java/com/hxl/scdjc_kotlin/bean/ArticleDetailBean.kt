package com.hxl.scdjc_kotlin.bean

import com.hxl.scdjc_kotlin.bean.base.RspDto
import java.io.Serializable

/**
 * Created by Administrator
 * on 2018/7/6 星期五.
 */
data class ArticleDetailBean(var upArticle: RspDto.Article?,
                             var columnId: Int, var article: RspDto.Article,
                             var columnName: String,
                             var nextArticle: RspDto.Article?,
                             var advertising: RspDto.Advertising?) : Serializable
