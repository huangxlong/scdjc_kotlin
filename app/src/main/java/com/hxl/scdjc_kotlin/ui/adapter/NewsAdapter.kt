package com.hxl.scdjc_kotlin.ui.adapter

import android.view.View
import android.widget.ImageView

import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hxl.scdjc_kotlin.R
import com.hxl.scdjc_kotlin.app.UrlConstant
import com.hxl.scdjc_kotlin.bean.base.RspDto

/**
 * Created by Administrator
 * on 2018/4/4 星期三.
 */
class NewsAdapter(layoutResId: Int, data: List<RspDto.Article>?, private val hasHeader: Boolean?) : BaseQuickAdapter<RspDto.Article, BaseViewHolder>(layoutResId, data) {

    override fun convert(helper: BaseViewHolder, item: RspDto.Article) {
        val position: Int
        if (hasHeader!!) {
            position = helper.layoutPosition - 1
        } else {
            position = helper.layoutPosition
        }
        helper.setText(R.id.tv_title, data[position].title)
                .setText(R.id.tv_des, data[position].articleIntro)
                .setText(R.id.tv_time, data[position].createTimeStr)
                .setText(R.id.tv_read, "" + data[position].readingQuantity)
                .setText(R.id.tv_zan, "" + data[position].thumbUpQuantity)
        Glide.with(mContext)
                .load<Any>(UrlConstant.BASE_URL + data[position].titleImg)
                .into(helper.getView<View>(R.id.iv_img) as ImageView)
    }
}
