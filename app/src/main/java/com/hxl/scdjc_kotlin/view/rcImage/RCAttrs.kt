package com.hxl.scdjc_kotlin.view.rcImage

/**
 * Created by Administrator
 * on 2018/5/29 星期二.
 */
interface RCAttrs {

    var isClipBackground: Boolean

    var isRoundAsCircle: Boolean

    val topLeftRadius: Float

    val topRightRadius: Float

    val bottomLeftRadius: Float

    val bottomRightRadius: Float

    var strokeWidth: Int

    var strokeColor: Int

    fun setRadius(radius: Int)

    fun setTopLeftRadius(topLeftRadius: Int)

    fun setTopRightRadius(topRightRadius: Int)

    fun setBottomLeftRadius(bottomLeftRadius: Int)

    fun setBottomRightRadius(bottomRightRadius: Int)
}