package com.hxl.scdjc_kotlin.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.GlideDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.hxl.scdjc_kotlin.R
import com.hxl.scdjc_kotlin.util.ToastUtil
import com.hxl.scdjc_kotlin.view.photoview.PhotoView
import com.hxl.scdjc_kotlin.view.photoview.PhotoViewAttacher


/**
 * Created by Administrator on 2018/1/25 0025.
 */

class PhotosViewFragment : Fragment() {

    private var imgUrl: String? = null
    private var attacher: PhotoViewAttacher? = null
    private var mAttacher: PhotoViewAttacher? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        imgUrl = arguments!!.getString("url")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_photo, container, false)
        val ivPhoto = view.findViewById<PhotoView>(R.id.iv_photo)
        if (attacher == null) {
            attacher = PhotoViewAttacher(ivPhoto)
        }
        if (imgUrl != null) {
            Glide.with(this)
                    .load(imgUrl)
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .listener(object : RequestListener<String, GlideDrawable> {
                        override fun onException(e: Exception, model: String, target: Target<GlideDrawable>, isFirstResource: Boolean): Boolean {
                            ToastUtil.show(activity!!.applicationContext, "图片加载失败")
                            return false
                        }

                        override fun onResourceReady(resource: GlideDrawable, model: String, target: Target<GlideDrawable>, isFromMemoryCache: Boolean, isFirstResource: Boolean): Boolean {

                            mAttacher = PhotoViewAttacher(ivPhoto)
                            mAttacher!!.setOnPhotoTapListener { view, x, y -> activity!!.finish() }//单击监听
                            return false
                        }
                    })
                    .into(ivPhoto)
        }
        return view
    }

    companion object {

        fun newInstance(url: String): PhotosViewFragment {
            val viewFragment = PhotosViewFragment()
            val bundle = Bundle()
            bundle.putString("url", url)
            viewFragment.arguments = bundle

            return viewFragment
        }
    }
}
