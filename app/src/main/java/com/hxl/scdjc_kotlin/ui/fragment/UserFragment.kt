package com.hxl.scdjc_kotlin.ui.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.view.View
import com.bumptech.glide.Glide
import com.hxl.scdjc_kotlin.R
import com.hxl.scdjc_kotlin.app.App
import com.hxl.scdjc_kotlin.app.AppConstant
import com.hxl.scdjc_kotlin.app.UrlConstant
import com.hxl.scdjc_kotlin.base.BaseFragment
import com.hxl.scdjc_kotlin.bean.UserBean
import com.hxl.scdjc_kotlin.mvp.contract.UserContract
import com.hxl.scdjc_kotlin.mvp.presenter.UserPresenter
import com.hxl.scdjc_kotlin.net.cookie.CookiesManager
import com.hxl.scdjc_kotlin.ui.activity.*
import com.hxl.scdjc_kotlin.util.ToastUtil
import com.hxl.scdjc_kotlin.view.DescDialog
import kotlinx.android.synthetic.main.fragment_me.*
import kotlinx.android.synthetic.main.layout_title.*

/**
 * Created by Administrator
 * on 2018/7/17 星期二.
 */
class UserFragment : BaseFragment(), UserContract.View, View.OnClickListener {
    private val mPresenter by lazy { UserPresenter() }
    private var columnName: String? = null
    private var user: UserBean? = null

    override fun getLayout(): Int = R.layout.fragment_me

    companion object {
        fun getInstance(columnName: String): UserFragment {
            val userFragment = UserFragment()
            userFragment.columnName = columnName
            return userFragment
        }
    }

    override fun initView() {
        mPresenter.attachView(this)
        tv_title.setText(R.string.text_user_title);
        layout_service.setOnClickListener(this)
        layout_product.setOnClickListener(this)
        layout_suggest.setOnClickListener(this)
        layout_password.setOnClickListener(this)
        layout_logout.setOnClickListener(this)
        layout_us.setOnClickListener(this)
    }

    override fun lazyLoad() {
        mPresenter.getUserInfo(columnName!!)
    }

    @SuppressLint("SetTextI18n")
    override fun setUserInfo(userInfo: UserBean) {
        user = userInfo
        //用户基本信息
        Glide.with(mContext).load(userInfo.customer.wxHeadImg).into(iv_photo)
        tv_real_name.text = userInfo.customer.realName
        tv_username.text = userInfo.customer.customerName
        //建议消息提醒
        if (userInfo.num > 0) {
            tv_has_news.visibility = View.VISIBLE
            tv_has_news.text = userInfo.num.toString()
        } else {
            tv_has_news.visibility = View.GONE
        }
        //客服信息
        Glide.with(mContext).load(UrlConstant.BASE_URL + userInfo.serviceMan.quickMark).into(iv_code)
        tv_name.text = "姓名：${userInfo.serviceMan.realName}"
        tv_phone.text = "电话：${userInfo.serviceMan.phoneNum}"
        tv_num.text = "执业编号：${userInfo.serviceMan.practiceNum}"
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.layout_logout -> logout()
            R.id.layout_product -> startActivity(Intent(mContext, ProductActivity::class.java).putExtra(AppConstant.PRODUCT, user))
            R.id.layout_suggest -> startActivity(Intent(mContext, SuggestActivity::class.java))
            R.id.layout_password -> startActivity(Intent(mContext, ResetPasswordActivity::class.java))
            R.id.layout_us -> startActivity(Intent(mContext, WebActivity::class.java)
                    .putExtra(AppConstant.LINK_TITLE, "关于我们")
                    .putExtra(AppConstant.LINK_URL, UrlConstant.BASE_URL + "djc/advertising/article37"))
            R.id.layout_service -> {
                if (rl_service.visibility == View.VISIBLE) {
                    rl_service.visibility = View.GONE
                    Glide.with(mContext).load(R.drawable.into_btn_more).into(iv_open)
                } else {
                    rl_service.visibility = View.VISIBLE
                    Glide.with(mContext).load(R.drawable.arrow_down).into(iv_open)
                }
            }
        }
    }

    private fun logout() {
        val dialog = DescDialog(mContext, getString(R.string.text_dialog_logout), getString(R.string.text_dialog_logout_desc), getString(R.string.btn_text_cancel), getString(R.string.btn_text_sure))
        dialog.show()
        dialog.setLeftClick(View.OnClickListener { dialog.dismiss() })
        dialog.setRightClick(View.OnClickListener {
            App.clearActivities()
            CookiesManager.clearAllCookies()
            startActivity(Intent(mContext, LoginActivity::class.java))
            dialog.dismiss()
        })

    }


    override fun showLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun dismissLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override fun showError(errorMsg: String, errorCode: Int) {
        ToastUtil.show(mContext, errorMsg)
    }

    override fun onResume() {
        super.onResume()
        mPresenter.getUserInfo(columnName!!)
    }
}