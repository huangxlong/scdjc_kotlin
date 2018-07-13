package com.hxl.scdjc_kotlin.ui.adapter

import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hxl.scdjc_kotlin.R
import com.hxl.scdjc_kotlin.app.UrlConstant
import com.hxl.scdjc_kotlin.bean.base.RspDto

/**
 * Created by Administrator
 * on 2018/5/31 星期四.
 */
class VideoBigAdapter(data: List<RspDto.Video>?, private val hasHeader: Boolean?) : BaseQuickAdapter<RspDto.Video, BaseViewHolder>(R.layout.item_big_video, data) {

    override fun convert(helper: BaseViewHolder, item: RspDto.Video) {
        val position: Int
        if (hasHeader!!) {
            position = helper.layoutPosition - 1
        } else {
            position = helper.layoutPosition
        }
        val data = data[position]

        helper.setText(R.id.tv_title, data.title)
                .setText(R.id.tv_playerNum, "播放次数：" + data.playQuantity)
        Glide.with(mContext)
                .load<Any>(UrlConstant.BASE_URL+ data.thumbUpQuantity)
                .into(helper.getView(R.id.iv_photo))
    }
}
