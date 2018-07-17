package com.hxl.scdjc_kotlin.db

import android.content.ContentValues
import android.content.Context
import java.util.*

/**
 * Created by Administrator
 * on 2018/7/17 星期二.
 */
class SearchHistoryDb(val context: Context) {
    private val tableName = "building"
    private val sqliteHelper by lazy { SqliteHelper(context) }

    /**
     * 添加
     */
    fun addRecords(record: String) {
        if (isHasRecords(record)) {
            deleteByRecords(record)
        }
        val database = sqliteHelper.readableDatabase
        val values = ContentValues()
        values.put("name", record)
        database.insert(tableName, null, values)
        database.close()
    }

    /**
     * 删除指定条目
     */
    fun deleteByRecords(record: String) {
        val array: Array<Any> = arrayOf(record)
        val database = sqliteHelper.writableDatabase
        database.execSQL(
                "delete from $tableName where name=?",
                array
        )
        database.close()
    }

    /**
     * 判断是否含有该记录
     */
    private fun isHasRecords(record: String): Boolean {
        var isHas = false
        val database = sqliteHelper.readableDatabase
        val cursor = database.query(tableName, null, null, null, null, null, null)
        while (cursor.moveToNext()) {
            if (record == cursor.getString(cursor.getColumnIndexOrThrow("name"))) {
                isHas = true
                break
            }
        }
        cursor.close()
        database.close()
        return isHas
    }

    /**
     * 获取所有搜索记录
     */
    fun getAllRecords(): List<String> {
        val list = mutableListOf<String>()
        val database = sqliteHelper.readableDatabase
        val cursor = database.query(tableName, null, null, null, null, null, "_id desc")
        while (cursor.moveToNext()) {
            val name = cursor.getString(cursor.getColumnIndexOrThrow("name"))
            list.add(name)
        }
        cursor.close()
        database.close()
        return list
    }

    /**
     * 清空搜索记录
     */
    fun deleteAllRecords() {
        val database = sqliteHelper.writableDatabase
        database.execSQL("delete from " + tableName)
        database.close()
    }

}