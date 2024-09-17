package com.bytedance.jstu.demo.lesson6.db

import android.annotation.SuppressLint
import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bytedance.jstu.demo.R
import com.bytedance.jstu.demo.lesson6.todo.ThingsToDo
import com.bytedance.jstu.demo.lesson6.todo.TodoDBHelper

class TodoActivity: AppCompatActivity() {



    private val add: Button by lazy {
        findViewById(R.id.add)
    }

    private val query: Button by lazy {
        findViewById(R.id.query)
    }

    private val update: Button by lazy {
        findViewById(R.id.update)
    }

    private val delete: Button by lazy {
        findViewById(R.id.delete)
    }

    private val showComplete: Button by lazy {
        findViewById(R.id.show_complete)
    }

    private val showIncomplete: Button by lazy {
        findViewById(R.id.show_incomplete)
    }

    private val queryResults: TextView by lazy {
        findViewById(R.id.query_results)
    }

    private val taskComplete: Button by lazy {
        findViewById(R.id.complete)
    }
    private val taskCIncomplete: Button by lazy {
        findViewById(R.id.incomplete)
    }

    private val clearAll: Button by lazy {
        findViewById(R.id.clear_all)
    }

    private val thing: EditText by lazy {
        findViewById(R.id.thing)
    }

    private val day: EditText by lazy {
        findViewById(R.id.day)
    }
    private val month: EditText by lazy {
        findViewById(R.id.month)
    }
    private val year: EditText by lazy {
        findViewById(R.id.year)
    }
    private val selectedId: EditText by lazy {
        findViewById(R.id.selected)
    }


    //找出最大的id值 便于清空
    @SuppressLint("Range")
    private fun getMaxID(): Int {
        var maxId = 0
        val cursor = (db ?: dbHelper.writableDatabase).query(
            "things",
            null,
            null,
            null,
            null,
            null,
            null,
            null
        )
        //MutableList是一个接口和通用的元素集合。
        //此处是元素为session类的集合
        //此处只接受session类的数据
        //遍历cursor
        //通过判断cursor.moveToFirst()的值为true或false来确定查询结果是否为空
        if (cursor.moveToFirst()) {

            do {
                maxId = cursor.getInt(cursor.getColumnIndex("id"))

            } while (cursor.moveToNext())
        }

        //记得关闭！
        cursor.close()
        return maxId
    }



    private val dbHelper = TodoDBHelper(this, "Todo.db", 1)
    private var db: SQLiteDatabase? = null
    var idNum=0



    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo_layout)
        db = dbHelper.writableDatabase


        add.setOnClickListener {
            db = dbHelper.writableDatabase
            if (thing.text.isEmpty() || day.text.isEmpty() || month.text.isEmpty() || year.text.isEmpty()) {
                Toast.makeText(this, "thing or deadline can not be empty", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }
            else {
                idNum++
                val values = ContentValues().apply {
                    //contentvalues当中的put函数赋值键值对
                    put("task", thing.text.toString())
                    put("day", day.text.toString())
                    put("month", month.text.toString())
                    put("year", year.text.toString())
                    put("state", "Incomplete")
                }
                //将contentvalues导入数据库
                db?.insert("things", null, values)
            }
        }

        update.setOnClickListener {
            if (selectedId.text.isEmpty()) {
                Toast.makeText(this, "you have to select an id", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            } else {
                val values = ContentValues().apply {
                    put("task", thing.text.toString())
                }
                //db.update(String table, ContentValues values, String whereClause, String[] whereArgs)
                //table：代表想要更新数据的表名。
                //values：代表想要更新的数据。
                //whereClause：满足该whereClause子句的记录将会被更新。对应的SQL语句的where部分(WHERE 子句用于指定从一个表或多个表中获取数据的条件
                //更新的条件，为一个字符串。如果为null，则所有行都将更新；
                //whereArgs：用于为whereArgs子句传递参数。arrayOf()方法是Kotlin提供的一种用于便捷创建数组的内置方法。
                //字符串数组，和whereClause配合使用。有两种用法，如果whereClause的条件已经直接给出，如“class = “ + num，num是传入的参数，则whereArgs可设为null。如果是”class = ？“，则？会被whereArgs这个数组中对应的值替换，whereArgs给出？代表的值，有多个？的，字符串数组里的值依次填入。
                //此处是指更新name为Tom的表中content一列内容
                db?.update("things", values, "id = ?", arrayOf(selectedId.text.toString()))
            }
        }


        delete.setOnClickListener {
            //val sql = "delete from address where id =selected_id.text.toString()"
            //db?.execSQL(sql)
            if (selectedId.text.isEmpty()) {
                Toast.makeText(this, "you have to select an id", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }else{
                db?.delete("things", "id = ?", arrayOf(selectedId.text.toString()))
            }
            //delete(db,selected_id.text.toString())
        }

        query.setOnClickListener {
            //public Cursor query (String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit)
            //table：表名；
            //culumns：需要返回的列的列表，如果为null，则返回全部的列；
            //selection：查询的条件，符合什么条件的行将返回。如果为null，则这个表里的所有行都将返回。其两种用法和update里的一样；
            //selection中的条件，不是用“,”分隔的，而是用“and”等逻辑关系词分隔的。
            //selectionArgs：用法和update里的一样。
            val cursor = (db ?: dbHelper.writableDatabase).query(
                "things",
                null,
                null,
                null,
                null,
                null,
                null,
                null
            )
            //MutableList是一个接口和通用的元素集合。
            //此处是元素为session类的集合
            //此处只接受session类的数据
            val thingsList = mutableListOf<ThingsToDo>()
            //遍历cursor
            //通过判断cursor.moveToFirst()的值为true或false来确定查询结果是否为空
            if (cursor.moveToFirst()) {
                do {
                    val id = cursor.getString(cursor.getColumnIndex("id"))
                    val task = cursor.getString(cursor.getColumnIndex("task"))
                    val day = cursor.getString(cursor.getColumnIndex("day"))
                    val month = cursor.getString(cursor.getColumnIndex("month"))
                    val year = cursor.getString(cursor.getColumnIndex("year"))
                    val state = cursor.getString(cursor.getColumnIndex("state"))
                    thingsList.add(ThingsToDo(id, task, day, month, year, state))
                } while (cursor.moveToNext())
            }

            //记得关闭！
            cursor.close()

            //输出内容
            if (thingsList.isNotEmpty()) {
                val queryResult = StringBuilder().append("Task:" + "\n")
                //val queryResultComplete = StringBuilder().append("Complete Task:" + "\n")
                //val queryResultIncomplete = StringBuilder().append("Incomplete Task:" + "\n")
                var completeNum = 0f
                var incompleteNum = 0f
                var rate: Float
                var num = 0
                for (thing in thingsList) {
                    num++
                    if (thing.state == "Complete") {
                        completeNum++
                    } else {
                        incompleteNum++
                    }
                    queryResult.append("ID:" + thing.id + "    " + "Deadline:" + thing.year + "." + thing.month + "." + thing.day + "        " + thing.state + "       " + thing.task + "\n")
                }
                rate = completeNum / (completeNum + incompleteNum) * 100f
                queryResult.append("\n\n"+"Completion Rate:" + rate.toString() + "%")

                queryResults.text = queryResult.toString()
            }
            else{
                val queryResult = StringBuilder().append("There Is No Task!" + "\n")
                queryResults.text = queryResult.toString()

            }
        }
        taskComplete.setOnClickListener {
            if (selectedId.text.isEmpty()) {
                Toast.makeText(this, "you have to select an id", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }else {
                val values = ContentValues().apply {
                    put("state", "Complete")
                }
                db?.update("things", values, "id = ?", arrayOf(selectedId.text.toString()))
            }
            }
        taskCIncomplete.setOnClickListener {
            if (selectedId.text.isEmpty()) {
                Toast.makeText(this, "you have to select an id", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }else {
                val values = ContentValues().apply {
                    put("state", "Incomplete")
                }
                db?.update("things", values, "id = ?", arrayOf(selectedId.text.toString()))
            }
            }
        showComplete.setOnClickListener {
            val cursor = (db ?: dbHelper.writableDatabase).query(
                "things",
                null,
                null,
                null,
                null,
                null,
                null,
                null
            )
            val thingsList = mutableListOf<ThingsToDo>()
            if (cursor.moveToFirst()) {
                do {
                    val id = cursor.getString(cursor.getColumnIndex("id"))
                    val task = cursor.getString(cursor.getColumnIndex("task"))
                    val day = cursor.getString(cursor.getColumnIndex("day"))
                    val month = cursor.getString(cursor.getColumnIndex("month"))
                    val year = cursor.getString(cursor.getColumnIndex("year"))
                    val state = cursor.getString(cursor.getColumnIndex("state"))
                    thingsList.add(ThingsToDo(id, task, day, month, year, state))
                } while (cursor.moveToNext())
            }

            //记得关闭！
            cursor.close()

            //输出内容
            if (thingsList.isNotEmpty()) {
                //val queryResult = StringBuilder().append("Task:" + "\n")
                val queryResultComplete = StringBuilder().append("Complete Task:" + "\n")
                // val queryResultIncomplete = StringBuilder().append("Incomplete Task:" + "\n")
                var completeNum = 0f
                var incompleteNum = 0f
                var rate: Float
                var num = 0
                for (thing in thingsList) {
                    num++
                    if (thing.state == "Complete") {
                        completeNum++
                        queryResultComplete.append("id:" + thing.id + "    " + "date:" + thing.year + "." + thing.month + "." + thing.day + "        " + thing.state + "       " + thing.task + "\n")
                    } else {
                        incompleteNum++
                    }
                }
                rate = completeNum / (completeNum + incompleteNum) * 100f

                queryResultComplete.append("Completion Rate:" + rate.toString() + "%")

                queryResults.text = queryResultComplete.toString()
            }

        }
        showIncomplete.setOnClickListener {
            val cursor = (db ?: dbHelper.writableDatabase).query(
                "things",
                null,
                null,
                null,
                null,
                null,
                null,
                null
            )
            val thingsList = mutableListOf<ThingsToDo>()
            if (cursor.moveToFirst()) {
                do {
                    val id = cursor.getString(cursor.getColumnIndex("id"))
                    val task = cursor.getString(cursor.getColumnIndex("task"))
                    val day = cursor.getString(cursor.getColumnIndex("day"))
                    val month = cursor.getString(cursor.getColumnIndex("month"))
                    val year = cursor.getString(cursor.getColumnIndex("year"))
                    val state = cursor.getString(cursor.getColumnIndex("state"))
                    //val num = cursor.getString(cursor.getColumnIndex("num"))

                    thingsList.add(ThingsToDo(id, task, day, month, year, state))
                } while (cursor.moveToNext())
            }

            //记得关闭！
            cursor.close()

            //输出内容
            if (thingsList.isNotEmpty()) {
                //val queryResult = StringBuilder().append("Task:" + "\n")
                //val queryResultComplete = StringBuilder().append("Complete Task:" + "\n")
                val queryResultIncomplete = StringBuilder().append("Incomplete Task:" + "\n")
                var completeNum = 0f
                var incompleteNum = 0f
                var rate: Float
                var num = 0
                for (thing in thingsList) {
                    num++
                    if (thing.state == "Incomplete") {
                        completeNum++
                        queryResultIncomplete.append("id:" + thing.id + "    " + "date:" + thing.year + "." + thing.month + "." + thing.day + "        " + thing.state + "       " + thing.task + "\n")
                    } else {
                        incompleteNum++
                    }
                }
                rate = completeNum / (completeNum + incompleteNum) * 100f

                queryResultIncomplete.append("Completion Rate:" + rate.toString() + "%")

                queryResults.text = queryResultIncomplete.toString()
            }

        }
        clearAll.setOnClickListener {

            var maxId=getMaxID()
            var i=0
            while(i<maxId){
                i++
                db?.delete("things", "id = ?", arrayOf(i.toString()))
            }
        }



        }
}