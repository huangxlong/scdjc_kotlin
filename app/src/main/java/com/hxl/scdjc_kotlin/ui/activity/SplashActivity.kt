package com.hxl.scdjc_kotlin.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.CountDownTimer
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.GlideDrawable
import com.bumptech.glide.request.animation.GlideAnimation
import com.bumptech.glide.request.target.SimpleTarget
import com.gyf.barlibrary.ImmersionBar
import com.hxl.scdjc_kotlin.R
import com.hxl.scdjc_kotlin.app.UrlConstant
import com.hxl.scdjc_kotlin.base.BaseActivity
import com.hxl.scdjc_kotlin.bean.AdvertisingBean
import com.hxl.scdjc_kotlin.mvp.contract.SplashContract
import com.hxl.scdjc_kotlin.mvp.presenter.SplashPresenter
import kotlinx.android.synthetic.main.activity_splash.*

/**
 * Created by Administrator
 * on 2018/7/13 星期五.
 */
class SplashActivity : BaseActivity(), SplashContract.View {
    private val mPresenter by lazy { SplashPresenter() }
    private lateinit var immersionBar: ImmersionBar
    private val timer = object : CountDownTimer(4000, 1000) {
        @SuppressLint("SetTextI18n")
        override fun onTick(millisUntilFinished: Long) {
            tv_pass.text = (millisUntilFinished / 1000).toString() + " 跳过"
        }

        override fun onFinish() {
            startAct()
        }
    }

    override fun getLayout(): Int = R.layout.activity_splash

    override fun initView() {
        mPresenter.attachView(this)
        immersionBar = ImmersionBar.with(this)
        immersionBar.init()
        tv_pass.setOnClickListener {
            timer.cancel()
            startAct()
        }
    }

    override fun loadData() {
        mPresenter.getAdvertising()
    }

    override fun setSplashResource(advertising: AdvertisingBean) {
        timer.start()
        tv_pass.visibility = View.VISIBLE
        Glide.with(this)
                .load(UrlConstant.BASE_URL + advertising.advertising.imgPath)
                .into(object : SimpleTarget<GlideDrawable>() {
                    override fun onResourceReady(resource: GlideDrawable, glideAnimation: GlideAnimation<in GlideDrawable>) {
                        window.decorView.background = resource
                    }
                })
    }

    override fun showLoading() {
    }

    override fun dismissLoading() {
    }

    override fun showError(errorMsg: String, errorCode: Int) {
    }

    private fun startAct() {
        startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        immersionBar.destroy()
        timer.cancel()
    }
}