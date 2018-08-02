package com.hxl.scdjc_kotlin.ui.activity

import android.content.Intent
import android.view.View
import com.gyf.barlibrary.ImmersionBar
import com.hxl.scdjc_kotlin.R
import com.hxl.scdjc_kotlin.app.AppConstant
import com.hxl.scdjc_kotlin.base.BaseActivity
import com.hxl.scdjc_kotlin.bean.LoginBean
import com.hxl.scdjc_kotlin.mvp.contract.LoginContract
import com.hxl.scdjc_kotlin.mvp.presenter.LoginPresenter
import com.hxl.scdjc_kotlin.util.ToastUtil
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.input_layout.*

/**
 * Created by Administrator
 * on 2018/6/27 星期三.
 */
class LoginActivity : View.OnClickListener, BaseActivity(), LoginContract.View {
    private val mPresenter by lazy { LoginPresenter() }
    private lateinit var immersionBar: ImmersionBar

    override fun getLayout(): Int = R.layout.activity_main

    override fun showLoading() {
        showLoadingDialog()
    }

    override fun dismissLoading() {
        hideLoadingDialog()
    }

    override fun loadData() {
    }

    override fun initView() {
        setWindowStatusBarColor(this, R.color.bg_white)
        mPresenter.attachView(this)
        immersionBar = ImmersionBar.with(this).statusBarDarkFont(true, 0.2f)
        immersionBar.init()

        et_account.setText("djc_android")
        et_password.setText("123123")
        et_account.setSelection("djc_android".length)

        main_btn_login.setOnClickListener(this)
        iv_code.setOnClickListener(this)
        layout_logo.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.main_btn_login -> login()
            R.id.iv_code -> {
            }
            R.id.layout_logo -> {
            }
        }
    }

    private fun login() {
        val userName = et_account.text.toString().trim()
        val password = et_password.text.toString().trim()

        if (userName.isEmpty()) {
            et_account.error = "用户名不能为空"
            et_account.requestFocus()
            return
        }
        if (password.isEmpty()) {
            et_password.error = "密码不能为空"
            et_password.requestFocus()
            return
        }
        mPresenter.login(userName, password)
    }

    override fun setLogin(loginBean: LoginBean) {
        val intent = Intent().setClass(this@LoginActivity, MainTabActivity::class.java)
        intent.putExtra(AppConstant.LOGIN_RSP, loginBean)
        startActivity(intent)
    }

    override fun showError(errorMsg: String, errorCode: Int) {
        ToastUtil.show(this@LoginActivity, errorMsg)
    }


    override fun onDestroy() {
        super.onDestroy()
        immersionBar.destroy()
    }
}