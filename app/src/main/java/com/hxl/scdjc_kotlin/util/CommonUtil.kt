package com.hxl.scdjc_kotlin.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import com.hxl.scdjc_kotlin.R
import com.hxl.scdjc_kotlin.app.UrlConstant
import org.jsoup.Jsoup


import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Calendar

/**
 * Created by Administrator
 * on 2018/5/18 星期五.
 */
object CommonUtil {


    /**
     * 返回当前时间+星期数
     *
     * @return
     */
    fun getSystemTimeAndWeek(): String {
        val systemTimeFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return systemTimeFormat.format(System.currentTimeMillis()) + "     " + CommonUtil.getCurrentWeek()
    }

    /**
     * 返回当前是星期几
     *
     * @return
     */
    fun getCurrentWeek(): String {
        val i = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
        var week = ""
        when (i) {
            1 -> week = "星期日"
            2 -> week = "星期一"
            3 -> week = "星期二"
            4 -> week = "星期三"
            5 -> week = "星期四"
            6 -> week = "星期五"
            7 -> week = "星期六"
        }
        return week
    }

    fun FormatTime(time: String): String {
        val appTime = time.split("-".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val systemTimeFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val systemTime = systemTimeFormat.format(System.currentTimeMillis()).split("-".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        return if (appTime[0] == systemTime[0]) {
            time.substring(5, time.length)
        } else {
            time
        }
    }


    /**
     * 从html中获取所有img标签的value
     *
     * @param html
     * @return
     */
    fun getImgFromHtml(html: String): Array<String>? {
        var imgList: Array<String>? = null
        var str = ""
        val document = Jsoup.parse(html)
        val imgs = document.getElementsByTag("img")//取得所有Img标签的值
        for (element in imgs) {
            val tempSelected = UrlConstant.BASE_URL + element.attr("src")
            if ("" == str) {
                str = tempSelected
            } else {
                val temp = tempSelected
                str = "$str,$temp"
            }
        }
        if ("" != str) {
            imgList = str.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        }
        return imgList
    }


    /**
     * 日期相同只返回时秒分
     *
     * @param time
     * @return
     */
    fun FormatTimeOnlyTime(time: String): String {
        val appTime = time.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val systemTimeFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val systemTime = systemTimeFormat.format(System.currentTimeMillis()).split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        return if (appTime[0] == systemTime[0]) {
            time.substring(11, time.length)
        } else {
            time
        }
    }

    fun startActivity(activity: Activity, templateName: String) {

    }

    fun getLayout(templateName: String): Int {
        var layout = 0
        //        if (templateName.equals(AppConstant.TYPE_IMG_MEDIUM)) {
        layout = R.layout.item_news_custom
        //        }

        //        else if (templateName.equals(AppConstant.TYPE_IMG_SMALL)) {
        //            layout = R.layout.item_news_column;
        //        }

        return layout
    }


    fun saveImageToGallery(context: Context, bitmap: Bitmap?) {
        if (bitmap == null) {
            ToastUtil.show(context, "保存出错了...")
            return
        }
        // 首先保存图片
        val appDir = File(Environment.getExternalStorageDirectory(), "djc")
        if (!appDir.exists()) {
            appDir.mkdir()
        }
        val fileName = "wx_code" + ".jpg"
        val file = File(appDir, fileName)
        try {
            if (!file.exists()) {
                val fos = FileOutputStream(file)
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
                fos.flush()
                fos.close()

                // 其次把文件插入到系统图库
                try {
                    MediaStore.Images.Media.insertImage(context.contentResolver, file.absolutePath, fileName, null)
                } catch (e: FileNotFoundException) {
                    e.printStackTrace()
                }

                //最后通知图库更新
                val intent = Intent()
                intent.action = Intent.ACTION_MEDIA_SCANNER_SCAN_FILE//扫描单个文件
                intent.data = Uri.fromFile(file)//给图片的绝对路径
                context.sendBroadcast(intent)

                ToastUtil.show(context, "保存成功")
            } else {
                ToastUtil.show(context, "保存成功")
            }
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

}
