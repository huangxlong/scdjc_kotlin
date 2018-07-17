package com.hxl.scdjc_kotlin.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hxl.scdjc_kotlin.R

/**
 * Created by Administrator
 * on 2018/6/12 星期二.
 */
class SearchHistoryAdapter(data: List<String>?) : BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_search_list, data) {

    override fun convert(helper: BaseViewHolder, item: String) {
        val position = helper.layoutPosition
        val searchKey = data[position]
        helper.setText(R.id.tv_search, searchKey).addOnClickListener(R.id.iv_delete)
    }
}
