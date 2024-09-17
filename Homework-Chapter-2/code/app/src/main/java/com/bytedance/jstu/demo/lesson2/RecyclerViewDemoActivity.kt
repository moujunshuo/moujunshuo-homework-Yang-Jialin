package com.bytedance.jstu.demo.lesson2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bytedance.jstu.demo.R

class RecyclerViewDemoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view_demo)
        //findViewById用于把XML布局构建成一个View，遍历XML属性，View需要什么属性就反馈给View。
        //效率不高，成熟方案是编译成那个 APK 的时候，把XML生成一些代码，然后我们去走的是那些代码
        //recycler_view是布局文件中的一个id，用于实现循环滚动
        //activity_recycler_view_demo中的RecyclerView部分的id叫做recycler_view
        val rv = findViewById<RecyclerView>(R.id.recycler_view)
        //用于布局，是竖着排列的
        rv.layoutManager = LinearLayoutManager(this)
        //adapter必须在设置manager之后，且是继承自recyclerView的adapter
        //见SearchItemAdapter，创建了一个SearchItemAdapter类
        val adapter = SearchItemAdapter()

        val items=arrayListOf<Pair<Pair<Int, Double>, Pair<String,String>>>()
        //设置行项目的数据
        for(i in 1..(questionMap.size)){
            val difficulty= questionMap[i]?.second?.second
            val name= questionMap[i]?.second?.first
            val answer= questionMap[i]?.first?.first
            val rate= questionMap[i]?.first?.second
            items.add(
                Pair(
                    Pair(
                        answer,
                        rate
                    ),
                    Pair(
                        name,
                        difficulty
                    )
                //用于保证上面的内容的类型符合要求
                ) as Pair<Pair<Int, Double>, Pair<String, String>>
            )
        }
        //val data = (1..100).map { "这是第${it}行" }


        //adapter是SearchItemAdapter类，该函数也是在SearchItemAdapter类中定义的
        adapter.setContentList(items)

        //更新列表当中的数据
        rv.adapter = adapter
        //以上部分，说明recyclerView数据已经绑在了adapter上面了
        adapter.setOnItemClickListener(object : SearchItemAdapter.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                // position here is the index in List "items", so we need to add 1 to it to get the key in dogMap
                println("click $position item")
                activityIntent(position + 1)
            }
        })


        //activity_recycler_view_demo中的EditText部分的id叫做words_et
        //监听
        val et = findViewById<EditText>(R.id.words_et)
        et.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                //搜索过滤
                adapter.setFilter(p0.toString())
            }
        })


    }

    fun activityIntent(position: Int) {
//        Toast.makeText(this, "hhhhhh", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("position", position)
        startActivity(intent)
    }
}