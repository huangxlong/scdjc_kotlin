package com.hxl.scdjc_kotlin.ui.adapter

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils

import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hxl.scdjc_kotlin.R
import com.hxl.scdjc_kotlin.app.AppConstant
import com.hxl.scdjc_kotlin.app.UrlConstant
import com.hxl.scdjc_kotlin.bean.base.RspDto
import com.hxl.scdjc_kotlin.ui.activity.NewsDetailActivity

//import com.hxl.scdjc_kotlin.ui.activity.NewsDetailActivity

/**
 * Created by Administrator
 * on 2018/5/17 星期四.
 */
class NewsColumnAdapter(data: List<RspDto.Article>?, private val hasHeader: Boolean?) : BaseQuickAdapter<RspDto.Article, BaseViewHolder>(R.layout.item_news_column, data) {

    override fun convert(helper: BaseViewHolder, item: RspDto.Article) {
        helper.setIsRecyclable(false)
        val position: Int = if (hasHeader!!) helper.layoutPosition - 1 else helper.layoutPosition
        val data = data[position]

        if (TextUtils.isEmpty(data.articleIntro)) helper.setGone(R.id.tv_des, true)
        if (data.top == 1) helper.setVisible(R.id.btn_top, true)

        helper.setText(R.id.tv_title, data.title)
                .setText(R.id.tv_des, "\t\t" + data.articleIntro)
                .setText(R.id.tv_time, data.createTimeStr)
                .setText(R.id.tv_zan, "" + data.thumbUpQuantity)
                .setText(R.id.tv_read, "" + data.readingQuantity)
        Glide.with(mContext)
                .load(UrlConstant.BASE_URL + data.titleImg)
                .into(helper.getView(R.id.iv_img))

        val childrenList = data.children
        if (childrenList != null && childrenList.isNotEmpty() && childrenList[0].id != 0) {
            val layoutManager = LinearLayoutManager(mContext)
            layoutManager.orientation = LinearLayoutManager.VERTICAL
            val recyclerView = helper.getView<RecyclerView>(R.id.recyclerView)
            recyclerView.isNestedScrollingEnabled = false
            val newsAdapter = NewsAdapter(R.layout.item_news_custom, childrenList, false)
            recyclerView.layoutManager = layoutManager
            recyclerView.adapter = newsAdapter
            helper.setVisible(R.id.recyclerView, true)

            newsAdapter.setOnItemClickListener({ _, _, position1 ->
                val intent = Intent(mContext, NewsDetailActivity::class.java)
                intent.putExtra(AppConstant.ARTICLE_ID, childrenList[position1].id)
                intent.putExtra(AppConstant.LINK_TITLE, childrenList[position1].title)
                mContext.startActivity(intent)
            })
        }

    }
}
