package com.hxl.scdjc_kotlin.ui.adapter

import android.widget.ImageView

import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hxl.scdjc_kotlin.R

/**
 * 时间轴图片列表adapter
 * Created by Administrator
 * on 2018/6/15 星期五.
 */
class TimeImgAdapter(data: List<String>?) : BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_time_image, data) {

    override fun convert(helper: BaseViewHolder, item: String) {
        val position = helper.layoutPosition
        val imageUrl = data[position]
        Glide.with(mContext).load(imageUrl).into(helper.getView(R.id.iv_photo))
    }
}
