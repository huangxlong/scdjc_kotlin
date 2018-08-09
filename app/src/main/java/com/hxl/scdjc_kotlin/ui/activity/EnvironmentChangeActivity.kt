package com.hxl.scdjc_kotlin.ui.activity

import android.view.View
import com.hxl.scdjc_kotlin.R
import com.hxl.scdjc_kotlin.app.AppConstant
import com.hxl.scdjc_kotlin.app.UrlConstant
import com.hxl.scdjc_kotlin.base.BaseActivity
import com.hxl.scdjc_kotlin.util.SPUtils
import com.hxl.scdjc_kotlin.util.ToastUtil
import kotlinx.android.synthetic.main.activity_environment_change.*

class EnvironmentChangeActivity : BaseActivity(), View.OnClickListener {

    override fun getLayout(): Int = R.layout.activity_environment_change

    override fun initView() {
        val serviceUrl = SPUtils.instance.getString(AppConstant.SERVICE_URL, "")
        if (serviceUrl == UrlConstant.ONLINE_URL) {
            check_online.isChecked = true
            check_test.isChecked = false
        } else {
            check_online.isChecked = false
            check_test.isChecked = true
        }
        btn_sure.setOnClickListener(this)
        check_test.setOnCheckedChangeListener { _, _ ->
            check_test.isChecked = check_test.isChecked
            check_online.isChecked = !check_test.isChecked
        }
        check_online.setOnCheckedChangeListener { _, _ ->
            check_online.isChecked = check_online.isChecked
            check_test.isChecked = !check_online.isChecked
        }
    }

    override fun loadData() {
    }

    override fun onClick(p0: View?) {
        if (check_online.isChecked) {
            SPUtils.instance.put(AppConstant.SERVICE_URL, UrlConstant.ONLINE_URL)
        }
        if (check_test.isChecked) {
            SPUtils.instance.put(AppConstant.SERVICE_URL, UrlConstant.TEST_URL)
        }
        ToastUtil.show(this@EnvironmentChangeActivity, "修改成功,重启生效")
        System.exit(0)


    }

}