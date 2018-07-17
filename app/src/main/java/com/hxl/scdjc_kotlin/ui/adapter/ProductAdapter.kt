package com.hxl.scdjc_kotlin.ui.adapter

import android.view.View
import android.widget.ImageView

import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hxl.scdjc_kotlin.R
import com.hxl.scdjc_kotlin.app.UrlConstant
import com.hxl.scdjc_kotlin.bean.UserBean

/**
 * Created by Administrator
 * on 2018/5/28 星期一.
 */
class ProductAdapter(data: List<UserBean.ColumnList>?) : BaseQuickAdapter<UserBean.ColumnList, BaseViewHolder>(R.layout.item_product, data) {

    override fun convert(helper: BaseViewHolder, item: UserBean.ColumnList) {
        val position = helper.layoutPosition
        val data = data[position]

        helper.setText(R.id.tv_name, data.columnInfoVO.name)
                .setText(R.id.tv_time, "服务时间： " + data.startTimeStr + " - " + data.endTimeStr)
        Glide.with(mContext)
                .load(UrlConstant.BASE_URL + data.columnInfoVO.imgPath)
                .into(helper.getView<View>(R.id.iv_product) as ImageView)
    }
}
