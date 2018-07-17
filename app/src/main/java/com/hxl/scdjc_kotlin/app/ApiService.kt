package com.hxl.scdjc_kotlin.app

import com.hxl.scdjc_kotlin.bean.*
import com.hxl.scdjc_kotlin.bean.base.RspDto
import com.hxl.scdjc_kotlin.net.response.BaseRsp
import retrofit2.http.*
import io.reactivex.Observable

/**
 * Created by Administrator
 * on 2018/6/27 星期三.
 */
interface ApiService {

    /**
     * 获取广告页面
     *
     * @return
     */
    @POST("djc/v/index")
     fun getAdvertising(): Observable<BaseRsp<AdvertisingBean>>

    /**
     * 用户登录
     *
     * @param username 用户名
     * @param password 密码
     * @return 登录数据
     */
    @POST("djc/v/login")
    @FormUrlEncoded
    fun login(@Field("username") username: String, @Field("password") password: String): Observable<BaseRsp<LoginBean>>


    /**
     * 获取文章列表
     *
     * @param columnId 栏目id
     * @param page     页数
     * @param pageSize 每页个数
     * @return 文章列表
     */
    @GET("djc/column/v/article/{columnId}")
    fun getArticleList(@Path("columnId") columnId: Int, @Query("page") page: Int, @Query("pageSize") pageSize: Int): Observable<BaseRsp<ArticleBean>>


    /**
     * 获取视频列表
     *
     * @param columnId 栏目id
     * @param page     页数
     * @param pageSize 每页个数
     * @return 视频列表
     */
    @GET("djc/column/v/video/{columnId}")
    fun getVideoList(@Path("columnId") columnId: Int, @Query("page") page: Int, @Query("pageSize") pageSize: Int): Observable<BaseRsp<VideoBean>>

    /**
     * 获取文章详情内容
     *
     * @param id 文章id
     * @return
     */
    @POST("djc/article/v/article")
    @FormUrlEncoded
    fun getArticleDetail(@Field("id") id: Int): Observable<BaseRsp<ArticleDetailBean>>


    /**
     * 文章点赞
     *
     * @param id 文章id
     * @return
     */
    @POST("djc/article/thumbUp")
    @FormUrlEncoded
    fun thumbUp(@Field("id") id: Int): Observable<BaseRsp<String>>


    /**
     * 搜索
     *
     * @param searchKey 搜索关键词
     * @param page      页数
     * @param pageSize  每页条数
     * @return
     */
    @GET("djc/article/v/query")
     fun search(@Query("q") searchKey: String, @Query("page") page: Int, @Query("pageSize") pageSize: Int): Observable<BaseRsp<ArticleBean>>

}