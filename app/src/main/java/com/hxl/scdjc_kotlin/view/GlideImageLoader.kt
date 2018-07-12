package com.hxl.scdjc_kotlin.view

import android.content.Context
import android.widget.ImageView

import com.bumptech.glide.Glide
import com.youth.banner.loader.ImageLoader

/**
 * Created by Administrator
 * on 2018/4/9 星期一.
 */
class GlideImageLoader : ImageLoader() {
    override fun displayImage(context: Context, path: Any, imageView: ImageView) {
        Glide.with(context).load(path).centerCrop().into(imageView)
    }
}
