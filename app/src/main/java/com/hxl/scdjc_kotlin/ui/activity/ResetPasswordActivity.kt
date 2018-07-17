package com.hxl.scdjc_kotlin.ui.activity

import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.hxl.scdjc_kotlin.R
import com.hxl.scdjc_kotlin.app.App
import com.hxl.scdjc_kotlin.base.BaseActivity
import com.hxl.scdjc_kotlin.mvp.contract.ResetPsdContract
import com.hxl.scdjc_kotlin.mvp.presenter.ResetPsdPresenter
import com.hxl.scdjc_kotlin.net.cookie.CookiesManager
import com.hxl.scdjc_kotlin.util.ToastUtil
import kotlinx.android.synthetic.main.activity_reset_password.*
import kotlinx.android.synthetic.main.layout_title.*

/**
 * Created by Administrator
 * on 2018/7/17 星期二.
 */
class ResetPasswordActivity : BaseActivity(), ResetPsdContract.View {
    private val mPresenter by lazy { ResetPsdPresenter() }

    override fun getLayout(): Int = R.layout.activity_reset_password

    override fun initView() {
        mPresenter.attachView(this)
        iv_back.visibility = View.VISIBLE
        tv_title.setText(R.string.text_password_title)
        iv_back.setOnClickListener { onBackPressed() }
        btn_commit.setOnClickListener { commit() }
        val mTextWatch = MTextWatch()
        et_old_password.addTextChangedListener(mTextWatch)
        et_new_password.addTextChangedListener(mTextWatch)
        et_new_sure_password.addTextChangedListener(mTextWatch)
    }


    private inner class MTextWatch : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

        }

        override fun afterTextChanged(s: Editable) {
            et_old_password.error = null
            et_new_password.error = null
            et_new_sure_password.error = null
        }
    }

    override fun showLoading() {
        showLoadingDialog()
    }

    override fun dismissLoading() {
        hideLoadingDialog()
    }


    /**
     * 提交密码修改
     */
    private fun commit() {
        val oldPsd = et_old_password.text.toString().trim()
        val newPsd = et_new_password.text.toString().trim()
        val newSurePsd = et_new_sure_password.text.toString().trim()
        if (oldPsd.isEmpty()) {
            et_old_password.error = "请输入旧密码"
            et_old_password.requestFocus()
            return
        }
        if (newPsd.isEmpty()) {
            et_new_password.error = "请输入新密码"
            et_new_password.requestFocus()
            return
        }
        if (newSurePsd.isEmpty()) {
            et_new_sure_password.error = "请再次输入新密码"
            et_new_sure_password.requestFocus()
            return
        }
        if (newPsd != newSurePsd) {
            et_new_sure_password.error = "两次输入的密码不一致"
            et_new_sure_password.requestFocus()
            return
        }
        if (oldPsd == newPsd) {
            et_new_password.error = "新密码不能与旧密码一样"
            et_new_password.requestFocus()
            return
        }
        mPresenter.resetPsd(oldPsd, newPsd)
    }

    override fun showResetResult(result: String) {
        ToastUtil.show(this@ResetPasswordActivity, "密码修改成功,请重新登录!")
        closeKeyBord(et_old_password, this@ResetPasswordActivity)
        App.clearActivities()
        CookiesManager.clearAllCookies()
        startActivity(Intent(this@ResetPasswordActivity, LoginActivity::class.java))
    }

    override fun showError(errorMsg: String, errorCode: Int) {
        ToastUtil.show(this@ResetPasswordActivity, errorMsg)
    }

    override fun loadData() {}
}