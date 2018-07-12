package com.hxl.scdjc_kotlin.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hxl.scdjc_kotlin.R
import com.hxl.scdjc_kotlin.bean.base.RspDto
import com.hxl.scdjc_kotlin.util.CommonUtil

/**
 * Created by Administrator
 * on 2018/6/1 星期五.
 */
class NewsTextAdapter(data: List<RspDto.Article>?) : BaseQuickAdapter<RspDto.Article, BaseViewHolder>(R.layout.item_text, data) {

    override fun convert(helper: BaseViewHolder, item: RspDto.Article) {
        helper.setIsRecyclable(false)
        val position = helper.layoutPosition - 1
        val data = data[position]
        helper.setText(R.id.tv_content, data.articleIntro)
                .setText(R.id.tv_time, CommonUtil.FormatTimeOnlyTime(data.createTimeStr))
                .setText(R.id.tv_read, "阅：" + data.readingQuantity)


    }
}
