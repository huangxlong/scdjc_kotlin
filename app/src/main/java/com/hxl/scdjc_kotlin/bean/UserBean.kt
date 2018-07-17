package com.hxl.scdjc_kotlin.bean

import com.hxl.scdjc_kotlin.bean.base.RspDto
import java.io.Serializable

/**
 * Created by Administrator
 * on 2018/7/17 星期二.
 */
data class UserBean(var serviceMan: ServiceMan, var num: Int, var customer: Customer, var columnList: List<ColumnList>) : Serializable {

    data class ServiceMan(var realName: String, var quickMark: String, var phoneNum: String, var practiceNum: String) : Serializable

    data class Customer(var realName: String, var customerName: String, var wxHeadImg: String) : Serializable

    data class ColumnList(var startTimeStr: String, var endTimeStr: String, var columnInfoVO: ColumnInfoVO) : Serializable {

        data class ColumnInfoVO(var name: String, var imgPath: String) : Serializable
    }
}