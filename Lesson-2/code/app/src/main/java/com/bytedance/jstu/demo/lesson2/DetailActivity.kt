package com.bytedance.jstu.demo.lesson2
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.widget.TextView
import com.bytedance.jstu.demo.R

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val position = intent.extras?.getInt("position")
        questionMap[position]?.let {
           findViewById<TextView>(R.id.name_detail).text = it.second.first
            findViewById<TextView>(R.id.info_detail).text = "题解 ${it.first.first};通过率 ${it.first.second}%"
            findViewById<TextView>(R.id.description).text = it.third
        }
    }
}