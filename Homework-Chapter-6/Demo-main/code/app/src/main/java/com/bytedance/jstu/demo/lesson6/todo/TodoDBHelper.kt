package com.bytedance.jstu.demo.lesson6.todo

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

class TodoDBHelper(val context: Context, name: String, version: Int): SQLiteOpenHelper(context, name, null, version) {

    //第一行 创建命令（create table）+创建的表的名称（session）
    //此处并不需要自己建立的session类
    private val createSessionList = "create table things(" +
            //列名（id）+数据类型（integer）+声明这是主键（primary key autoincrement）
            "id integer primary key autoincrement," +
            "num text," +
            "task text," +
            "day text," +
            "month text," +
            "year text,"+
            "state text)"

    private val createMessageList = "create table things(" +
            "id integer primary key autoincrement," +
            "num text," +
            "task text," +
            "day text," +
            "month text," +
            "year text,"+
            "state text)"
    private val createUserList = "create table things(" +
            "id integer primary key autoincrement," +
            "num text," +
            "task text," +
            "day text," +
            "month text," +
            "year text,"+
            "state text)"
    override fun onCreate(db: SQLiteDatabase?) {
        //使用EXEC执行动态sql语句，其中括号当中的内容是字符串，也是SQLite的命令
        db?.execSQL(createSessionList)
//        db?.execSQL(createMessageList)
        Toast.makeText(context, "create things db success", Toast.LENGTH_SHORT).show()
    }


    //更新数据库
    //在demo当中没有实现
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (oldVersion <= 1) {
            db?.execSQL(createMessageList)
        }
        if (oldVersion <= 2) {
            db?.execSQL(createUserList)
        }
    }
}