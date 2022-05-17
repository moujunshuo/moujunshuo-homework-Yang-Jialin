package com.bytedance.jstu.demo.lesson2

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bytedance.jstu.demo.R

class QuestionRelativeLayoutHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.layout_question, parent, false)
) {
    var question: RelativeLayout = itemView.findViewById(R.id.question)

    //<题解数、准确率>、<题目、难度>
    fun update(questionInfoPair: Pair<Pair<Int, Double>, Pair<String,String>>) {
        //itemView.findViewById<ImageView>(R.id.difficulty).setImageResource(questionInfoPair.second.second)
        itemView.findViewById<TextView>(R.id.difficulty).text = questionInfoPair.second.second
        itemView.findViewById<TextView>(R.id.name).text = questionInfoPair.second.first
        itemView.findViewById<TextView>(R.id.info).text =
            "题解 ${questionInfoPair.first.first};通过率 ${questionInfoPair.first.second}%"
    }
}
