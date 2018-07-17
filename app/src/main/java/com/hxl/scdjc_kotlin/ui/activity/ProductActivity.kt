package com.hxl.scdjc_kotlin.ui.activity

import android.support.v7.widget.LinearLayoutManager
import com.hxl.scdjc_kotlin.R
import com.hxl.scdjc_kotlin.app.AppConstant
import com.hxl.scdjc_kotlin.base.BaseActivity
import com.hxl.scdjc_kotlin.bean.UserBean
import com.hxl.scdjc_kotlin.ui.adapter.ProductAdapter
import kotlinx.android.synthetic.main.activity_product.*
import kotlinx.android.synthetic.main.layout_title.*

/**
 * Created by Administrator
 * on 2018/7/17 星期二.
 */
class ProductActivity : BaseActivity() {
    private var user: UserBean? = null
    private val productAdapter by lazy { ProductAdapter(user!!.columnList) }

    override fun getLayout(): Int = R.layout.activity_product

    override fun initView() {
        mLayoutStatusView = multipleStatusView
        tv_title.setText(R.string.text_product_title)
        user = intent.extras.get(AppConstant.PRODUCT) as UserBean?
        if (user == null || user!!.columnList.isEmpty()) {
            mLayoutStatusView!!.showEmpty()
        } else {
            mLayoutStatusView!!.showContent()
            initRecycler()
        }
    }

    private fun initRecycler() {
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = productAdapter
    }

    override fun loadData() {
    }
}