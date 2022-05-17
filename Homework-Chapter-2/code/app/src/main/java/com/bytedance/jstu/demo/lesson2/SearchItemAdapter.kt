package com.bytedance.jstu.demo.lesson2

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bytedance.jstu.demo.R

/**
 * Created by shenjun on 2/21/22.
 */

/**
 * viewHolder&adapter都在这个类里面
 * */
class SearchItemAdapter : RecyclerView.Adapter<QuestionRelativeLayoutHolder>() {
    //创建ItemView的ViewHolder，用于后续的数据绑定
    //mutableListOf<String>():用于创造一个可变的集合，类型为string
    //ListOf<String>()：不可变集合
    //contentList原始数据
    //private val items = arrayListOf<Pair<Pair<Int,String>, Triple<Int, String, String>>>()
    private val contentList = arrayListOf<Pair<Pair<Int, Double>, Pair<String,String>>>()
    //private val contentList:List<String> = mutableListOf()
    //filteredList是展现过滤后的List
    private val filteredList = arrayListOf<Pair<Pair<Int, Double>, Pair<String,String>>>()
    private lateinit var onItemClickListener: SearchItemAdapter.OnItemClickListener

    /**
     * 创建ViewHolder
     */
    fun setOnItemClickListener(listener: SearchItemAdapter.OnItemClickListener){
        this.onItemClickListener = listener
    }
    //override是重写父类已经存在的方法
    //创建本文件最下面的ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionRelativeLayoutHolder {
        //inflate：把布局资源文件实例化为一个对象
        //val v = View.inflate(parent.context, R.layout.layout_question, null)
        //运用了最下面一个生成类的函数
        return QuestionRelativeLayoutHolder(parent)
    }



    //item数据的绑定
    //第一个参数：前面创建的对象 第二个参数：目前绑定的数据是在列表上的第几个数据
    //本程序的数据源是一个叫做filteredList的列表
    //通过ViewHolder当中的Bind函数来绑定
    override fun onBindViewHolder(holder: QuestionRelativeLayoutHolder, position: Int) {
        holder.update(contentList[position])
        holder.question.setOnClickListener {
            onItemClickListener.onItemClick(holder.itemView, position)
        }
        //绑定图片资源
        //holder.v.item_image.set
    }



    //告诉RecyclerView列表上的item的条数
    override fun getItemCount(): Int = filteredList.size



    //设置当前集合
    //list: List<String>:一个性质为string的List，它的名字叫做list，这个集合作为该函数的参数
    //更新数据，当数据更新时，可从recyclerView上去直接更新，不需要通过UI更新了
    fun setContentList(list: List<Pair<Pair<Int, Double>, Pair<String,String>>>) {
        //清空集合
        contentList.clear()
        //把list当中所有元素加入当前可变集合
        contentList.addAll(list)
        filteredList.clear()
        filteredList.addAll(list)
        //notifyDataSetChanged方法通过一个外部的方法控制如果适配器的内容改变时需要强制调用getView来刷新每个Item的内容,可以实现动态的刷新列表的功能。
        notifyDataSetChanged()
    }



    //搜索过滤用
    //问号代表可空
    fun setFilter(keyword: String?) {
        filteredList.clear()
        //isNotEmpty : 字串长度大于0时返回true，只有非空串可调用。
        //"?.":语句“strB?.length”等价于“length_null = if (strB!=null) strB.length else null”
        if (keyword?.isNotEmpty() == true) {
            //集合过滤操作符filter{}：过滤出所有符合给定函数条件的元素。
            //it 用于 函数类型中： 函数只有一个参数 。 it表示 参数对象。
            //List.contains() function returns true if element is found in the list, else false.
            //List.filter()选取符合条件的条目作为一个新的列表
            //选取所有满足keyword搜索关键字的条目
            filteredList.addAll(contentList.filter { it.second?.first?.contains(keyword) })
        } else {
            //没有keyword，显示所有搜索条目
            filteredList.addAll(contentList)
        }
        notifyDataSetChanged()
    }




    //创建一个ViewHolder 必须继承自RecyclerView.ViewHolder。用于后续的数据绑定
    //ViewHolder的作用：持有recycler的几个列表项，并且响应数据的更新
    //应用于Adapter当中
//    class SearchItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//        //把对应的布局文件加载成了一个View
//        //更新item的文本
//        //private 内部访问
//        private val tv = view.findViewById<TextView>(R.id.search_item_tv)
//
//        fun bind(content: String) {
//            //text是文件search_item_tv.xml当中的TextView的内容
//            tv.text = content
//        }
//    }
    interface OnItemClickListener{
        fun onItemClick(view: View, position: Int)
    }

}