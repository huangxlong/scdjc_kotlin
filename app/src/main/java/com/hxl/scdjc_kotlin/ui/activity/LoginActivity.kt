package com.hxl.scdjc_kotlin.ui.activity

import android.content.Intent
import android.text.TextUtils
import android.view.View
import com.hxl.scdjc_kotlin.R
import com.hxl.scdjc_kotlin.app.AppConstant
import com.hxl.scdjc_kotlin.base.BaseActivity
import com.hxl.scdjc_kotlin.bean.LoginBean
import com.hxl.scdjc_kotlin.http.BaseSubscribe
import com.hxl.scdjc_kotlin.http.ResponseTransformer
import com.hxl.scdjc_kotlin.http.RetrofitManager
import com.hxl.scdjc_kotlin.util.RxUtil
import com.hxl.scdjc_kotlin.util.ToastUtil
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.input_layout.*

/**
 * Created by Administrator
 * on 2018/6/27 星期三.
 */
class LoginActivity : View.OnClickListener, BaseActivity() {
    override fun loadData() {

    }

    override fun getLayout(): Int {
        return R.layout.activity_main
    }

    override fun initView() {
        setWindowStatusBarColor(this, R.color.bg_white)

        et_account.setText("djct1")
        et_password.setText("111")

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
        if (TextUtils.isEmpty(et_account.text.toString().trim())) {
            et_account.error = "用户名不能为空"
            et_account.requestFocus()
            return
        }
        if (TextUtils.isEmpty(et_password.text.toString().trim())) {
            et_password.error = "密码不能为空"
            et_password.requestFocus()
            return
        }

        showLoadingDialog()
        RetrofitManager.service
                .login(et_account.text.toString().trim(), et_password.text.toString().trim())
                .compose(ResponseTransformer.handleResult())
                .compose(RxUtil.rxSchedulerHelper())
                .subscribe({ loginBean ->
                    hideLoadingDialog()
                    val intent = Intent().setClass(this@LoginActivity, MainTabActivity::class.java)
                    intent.putExtra(AppConstant.LOGIN_RSP, loginBean)
                    startActivity(intent)
                }, { throwable ->
                    hideLoadingDialog()
                    ToastUtil.show(this@LoginActivity, throwable.toString())
                })

//        RetrofitManager.service
//                .login(et_account.text.toString().trim(), et_password.text.toString().trim())
//                .compose(RxUtil.rxSchedulerHelper())
//                .subscribe(object : BaseSubscribe<LoginBean>() {
//                    override fun onSuccess(t: LoginBean?) {
//                        hideLoadingDialog()
//                        val intent = Intent().setClass(this@LoginActivity, MainTabActivity::class.java)
//                        intent.putExtra(AppConstant.LOGIN_RSP, t)
//                        startActivity(intent)
//                    }
//
//                    override fun onFail(errMsg: String?) {
//                        hideLoadingDialog()
//                        errMsg?.let { ToastUtil.show(this@LoginActivity, it) }
//                    }
//                })
    }
}