package com.hxl.scdjc_kotlin.bean.base

import java.io.Serializable

/**
 * Created by Administrator
 * on 2018/7/5 星期四.
 */
class RspDto {

    /**
     * banner信息
     */
    data class Advertising(var id: Int, var adPath: String, var imgPath: String) : Serializable


    /**
     * 栏目图标信息
     */
    data class Column(var name: String,
                      var imgPath: String,
                      var checkImgPath: String,
                      var url: String,
                      var nameRemark: String,
                      var templateName: String,
                      var id: Int,
                      var isRead:Int,   //0未读，1已读
                      var childrenColumn: List<Column>,
                      var advertisingList: List<Advertising>) : Serializable

    /**
     * 老师信息
     */
    data class Employee(var department: Any,
                        var newPassword: Any,
                        var roleStrs: Any,
                        var columnStrs: Any,
                        var id: Any,
                        var userName: Any,
                        var headImg: String,
                        var quickMark: Any,
                        var phoneNum: Any,
                        var realName: String,
                        var passWord: Any,
                        var inputTime: Any,
                        var readingQuantity: Int,
                        var thumbUpQuantity: Int,
                        var practiceNum: String,
                        var riskDisclosure: Any,
                        var disclaimer: String,
                        var state: Any,
                        var employeeDetails: String,
                        var roles: List<*>,
                        var columns: List<*>,
                        var departments: List<*>,
                        var employeeRoles: List<*>) : Serializable

    /**
     * 文章信息
     */
    data class Article(var employee: Employee,
                       var column: Any,
                       var systemDictionaryItem: Any,
                       var createTimeStr: String,
                       var id: Int,
                       var teacherId: Int,
                       var title: String,
                       var titleImg: String,
                       var tagId: Any,
                       var source: String,
                       var creationTime: Long,
                       var updateTime: Long,
                       var thumbUpQuantity: Int,
                       var readingQuantity: Int,
                       var top: Int,
                       var parentId: Any,
                       var columnId: Int,
                       var articleIntro: String,
                       var url: String,
                       var urlTitle: String,
                       var voiceTitle: String,
                       var auditId: Any,
                       var auditTime: Any,
                       var auditRemark: Any,
                       var stocks: Any,
                       var state: Int,
                       var articleIntroDetails: String,
                       var tag: String,
                       var videoPath: String,
                       var videoDuration: String,
                       var thumbnailPath: String,
                       var voicePath: String,
                       var today: Boolean,
                       var children: List<Article>?) : Serializable

    /**
     * 视频信息
     */
    data class Video(var employee: Employee,
                     var column: Column,
                     var auditMan: Any,
                     var createTimeStr: String,
                     var id: Int,
                     var teacherId: Int,
                     var title: String,
                     var thumbnailPath: String,
                     var videoDuration: String,
                     var videoPath: String,
                     var videoIntroDetails: String,
                     var creationTime: Long,
                     var columnId: Int,
                     var top: Int,
                     var playQuantity: Int,
                     var thumbUpQuantity: Int,
                     var tag: String,
                     var auditId: Any,
                     var auditRemark: Any,
                     var auditTime: Any,
                     var state: Int) : Serializable

}