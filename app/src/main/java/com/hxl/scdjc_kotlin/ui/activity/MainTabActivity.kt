package com.hxl.scdjc_kotlin.ui.activity

import android.content.Intent
import android.support.v4.app.Fragment
import android.view.View
import com.bumptech.glide.Glide
import com.hxl.scdjc_kotlin.R
import com.hxl.scdjc_kotlin.app.App
import com.hxl.scdjc_kotlin.app.AppConstant
import com.hxl.scdjc_kotlin.app.UrlConstant
import com.hxl.scdjc_kotlin.base.BaseActivity
import com.hxl.scdjc_kotlin.bean.LoginBean
import com.hxl.scdjc_kotlin.net.cookie.CookiesManager
import com.hxl.scdjc_kotlin.ui.fragment.HomeFragment
import com.hxl.scdjc_kotlin.ui.fragment.SecondFragment
import com.hxl.scdjc_kotlin.ui.fragment.ThreeFragment
import com.hxl.scdjc_kotlin.ui.fragment.UserFragment
import com.hxl.scdjc_kotlin.util.ToastUtil
import kotlinx.android.synthetic.main.activity_main_tab.*

/**
 * Created by Administrator
 * on 2018/7/4 星期三.
 */
class MainTabActivity : View.OnClickListener, BaseActivity() {

    private var mFragment = mutableListOf<Fragment>()
    private lateinit var loginBean: LoginBean
    private var mContent: Fragment? = null

    override fun getLayout(): Int {
        return R.layout.activity_main_tab
    }

    override fun initView() {
        layout_lianjin.setOnClickListener(this)
        layout_cangjin.setOnClickListener(this)
        layout_logo.setOnClickListener(this)
        layout_juejin.setOnClickListener(this)
        layout_me.setOnClickListener(this)
        loginBean = intent.extras.get(AppConstant.LOGIN_RSP) as LoginBean
        setTabResource(0)

        mFragment.add(HomeFragment.getInstance(loginBean.columnList[0]))
        mFragment.add(SecondFragment.getInstance(loginBean.columnList[1]))
        mFragment.add(ThreeFragment.getInstance(loginBean.columnList[3]))
        mFragment.add(UserFragment.getInstance(loginBean.columnList[4].name))
        switchContent(null, mFragment[0])
    }

    override fun loadData() {
    }


    private fun setTabResource(selectIndex: Int) {
        val columnList = loginBean.columnList
        tv_lianjin.text = columnList[0].name
        tv_lianjin.setTextColor(resources.getColor(R.color.main_tab_normal))
        tv_cangjin.text = columnList[1].name
        tv_cangjin.setTextColor(resources.getColor(R.color.main_tab_normal))
        tv_juejin.text = columnList[3].name
        tv_juejin.setTextColor(resources.getColor(R.color.main_tab_normal))
        tv_me.text = columnList[4].name
        tv_me.setTextColor(resources.getColor(R.color.main_tab_normal))
        Glide.with(this@MainTabActivity)
                .load(UrlConstant.BASE_URL + columnList[0].imgPath)
                .into(iv_lianjin)
        Glide.with(this@MainTabActivity)
                .load(UrlConstant.BASE_URL + columnList[1].imgPath)
                .into(iv_cangjin)
        Glide.with(this@MainTabActivity)
                .load(UrlConstant.BASE_URL + columnList[2].imgPath)
                .into(iv_logo)
        Glide.with(this@MainTabActivity)
                .load(UrlConstant.BASE_URL + columnList[3].imgPath)
                .into(iv_juejin)
        Glide.with(this@MainTabActivity)
                .load(UrlConstant.BASE_URL + columnList[4].imgPath)
                .into(iv_me)
        when (selectIndex) {
            0 -> {
                tv_lianjin.setTextColor(resources.getColor(R.color.main_tab_act))
                Glide.with(this@MainTabActivity)
                        .load(UrlConstant.BASE_URL + columnList[0].checkImgPath)
                        .into(iv_lianjin)
            }
            1 -> {
                tv_cangjin.setTextColor(resources.getColor(R.color.main_tab_act))
                Glide.with(this@MainTabActivity)
                        .load(UrlConstant.BASE_URL + columnList[1].checkImgPath)
                        .into(iv_cangjin)
            }
            3 -> {
                tv_juejin.setTextColor(resources.getColor(R.color.main_tab_act))
                Glide.with(this@MainTabActivity)
                        .load(UrlConstant.BASE_URL + columnList[3].checkImgPath)
                        .into(iv_juejin)
            }
            4 -> {
                tv_me.setTextColor(resources.getColor(R.color.main_tab_act))
                Glide.with(this@MainTabActivity)
                        .load(UrlConstant.BASE_URL + columnList[4].checkImgPath)
                        .into(iv_me)
            }
        }
    }

    /**
     * 切换Fragment
     *
     * @param from 当前fragment
     * @param to   切换fragment
     */
    private fun switchContent(from: Fragment?, to: Fragment) {
        if (mContent == null || mContent != to) {
            val transaction = supportFragmentManager.beginTransaction()
            if (mContent != null) {
                mContent!!.onPause()
            }
            if (to.isAdded) {
                to.onResume()
            } else {
                transaction.add(R.id.container, to)
            }
            if (from != null) {
                transaction.hide(from)// 隐藏当前的fragment
            }
            transaction.show(to).commitAllowingStateLoss()// 显示下一个
            mContent = to
        }
    }


    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.layout_lianjin -> {
                setTabResource(0)
                switchContent(mContent, mFragment[0])
            }
            R.id.layout_cangjin -> {
                setTabResource(1)
                switchContent(mContent, mFragment[1])
            }
            R.id.layout_logo -> startActivity(Intent(this@MainTabActivity, LogoActivity::class.java))
            R.id.layout_juejin -> {
                setTabResource(3)
                switchContent(mContent, mFragment[2])
            }
            R.id.layout_me -> {
                setTabResource(4)
                switchContent(mContent, mFragment[3])
            }
        }
    }


    private var exitTime: Long = 0
    override fun onBackPressed() {
        if (System.currentTimeMillis() - exitTime > 2000) {
            ToastUtil.show(this, getString(R.string.toast_exit_app))
            exitTime = System.currentTimeMillis()
        } else {
            super.onBackPressed()
            CookiesManager.clearAllCookies()
            App.exit()
        }
    }

}