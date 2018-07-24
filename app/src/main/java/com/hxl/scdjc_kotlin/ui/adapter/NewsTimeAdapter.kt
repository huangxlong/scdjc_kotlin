package com.hxl.scdjc_kotlin.ui.adapter

import android.content.Intent
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.text.TextUtils

import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hxl.scdjc_kotlin.R
import com.hxl.scdjc_kotlin.app.UrlConstant
import com.hxl.scdjc_kotlin.bean.base.RspDto
import com.hxl.scdjc_kotlin.ui.activity.BigImageActivity
import com.hxl.scdjc_kotlin.util.CommonUtil
import com.xiao.nicevideoplayer.NiceVideoPlayer
import com.xiao.nicevideoplayer.TxVideoPlayerController

import java.util.Arrays

/**
 * Created by Administrator
 * on 2018/6/14 星期四.
 */
class NewsTimeAdapter(data: List<RspDto.Article>?, private val hasHeader: Boolean?) : BaseQuickAdapter<RspDto.Article, BaseViewHolder>(R.layout.item_time, data) {

    override fun convert(helper: BaseViewHolder, item: RspDto.Article) {
        helper.setIsRecyclable(false)
        val position: Int = if (hasHeader!!) helper.layoutPosition - 1 else helper.layoutPosition

        val data = data[position]
        Glide.with(mContext)
                .load<Any>(UrlConstant.BASE_URL + data.employee.headImg)
                .into(helper.getView(R.id.iv_photo))
        val str = "<font color=\"#ca0000\">" + data.title + "  " + "</font>" + data.articleIntro
        helper.setText(R.id.tv_time, data.createTimeStr)
                .setText(R.id.tv_name, "投资顾问：" + data.employee.realName + "  (执业号：" + data.employee.practiceNum + ")")
                .setText(R.id.tv_read, "" + data.readingQuantity)
                .setText(R.id.tv_zan, "" + data.thumbUpQuantity)
                .setText(R.id.tv_title, Html.fromHtml(str))
                .setText(R.id.tv_tag, data.tag)

        if (!TextUtils.isEmpty(data.videoPath)) {
            //视屏部分
            helper.setVisible(R.id.niceVideoPlayer, true)
            val player = helper.getView<NiceVideoPlayer>(R.id.niceVideoPlayer)
            player.setPlayerType(NiceVideoPlayer.TYPE_IJK)
            player.setUp(UrlConstant.BASE_URL + data.videoPath, null)
            val controller = TxVideoPlayerController(mContext)
            controller.setTitle("")    //标题
            controller.setLenght(data.videoDuration) //播放时间
            Glide.with(mContext)
                    .load<Any>(UrlConstant.BASE_URL + data.thumbnailPath)
                    .crossFade()
                    .into(controller.imageView())
            player.setController(controller)
        } else if (!TextUtils.isEmpty(data.articleIntroDetails)) {
            // 图片部分
            helper.setVisible(R.id.recyclerView, true)
            val recycler = helper.getView<RecyclerView>(R.id.recyclerView)
            recycler.isNestedScrollingEnabled = false  //禁止滑动
            val imgList = CommonUtil.getImgFromHtml(data.articleIntroDetails) ?: return
            val lineCount: Int
            lineCount = if (imgList.size == 4) {
                2
            } else if (imgList.size >= 3 || imgList.isEmpty()) {
                3
            } else {
                imgList.size
            }
            val timeImgAdapter = TimeImgAdapter(Arrays.asList<String>(*imgList))
            val layoutManager = GridLayoutManager(mContext, lineCount)
            layoutManager.orientation = LinearLayoutManager.VERTICAL
            recycler.layoutManager = layoutManager
            recycler.adapter = timeImgAdapter

            timeImgAdapter.setOnItemClickListener { _, _, position1 ->
                val intent = Intent(mContext, BigImageActivity::class.java)
                intent.putExtra("image", imgList)
                intent.putExtra("position", position1)
                mContext.startActivity(intent)
            }
        } else if (!TextUtils.isEmpty(data.voicePath)) {
            //语音部分

        }

    }
}
