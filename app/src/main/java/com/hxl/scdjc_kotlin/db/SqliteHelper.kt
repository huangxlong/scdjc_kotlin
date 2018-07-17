package com.hxl.scdjc_kotlin.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 * Created by Administrator
 * on 2018/7/17 星期二.
 */
class SqliteHelper(context: Context) : SQLiteOpenHelper(context, "search.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        val sqlStr = "CREATE TABLE IF NOT EXISTS building (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT);"//创建存储搜索楼盘的表
        db!!.execSQL(sqlStr)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }
}