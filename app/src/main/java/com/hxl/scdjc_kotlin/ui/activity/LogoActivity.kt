package com.hxl.scdjc_kotlin.ui.activity

import com.gyf.barlibrary.ImmersionBar
import com.hxl.scdjc_kotlin.R
import com.hxl.scdjc_kotlin.base.BaseActivity
import kotlinx.android.synthetic.main.activity_logo.*

/**
 * Created by Administrator
 * on 2018/7/17 星期二.
 */
class LogoActivity : BaseActivity() {
    private lateinit var immersionBar: ImmersionBar

    override fun getLayout(): Int = R.layout.activity_logo

    override fun initView() {
        immersionBar = ImmersionBar.with(this)
        immersionBar.init()
        tv_back.setOnClickListener { onBackPressed() }
    }

    override fun loadData() {
    }

    override fun onDestroy() {
        super.onDestroy()
        immersionBar.destroy()
    }
}