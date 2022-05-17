package com.bytedance.jstu.demo.lesson4.homework

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.bytedance.jstu.demo.R
import java.util.*
import kotlin.concurrent.timer


import android.os.Looper
import android.os.Message
import android.util.Log
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*

/**
 *  author : neo
 *  time   : 2021/10/30
 *  desc   :
 */
class ClockActivity : Activity() {

    companion object {
        const val STATUS_ONE_SECOND = 0
    }

    //初始化变量，去查找控件并使用
    //问号前的是该activity连接的xml文件当中的控件
    //xml文件中有一个类型为Clockview的控件，其id名为clock
    //private var numberClockTextView: TextView? = null
    var clockView: ClockView? = null

    //private fun getTime(): String? {
    //    val format = SimpleDateFormat("HH:mm:ss", Locale.CHINA)
    //    return format.format(Date())
    //}

    //学会如何在kotlin当中转换java代码
    //如何在activity当中应用view当中的函数
    private val handler = Handler(Looper.getMainLooper()) { msg ->
        when (msg.what) {
            //如果msg是STATUS_ONE_SECOND的话执行下列代码？
            STATUS_ONE_SECOND -> {
                //numberClockTextView?.text = getTime()
                clockView?.doInvalidate()
            }
        }
        true
    }

    //可以当作程序当中的main函数
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clock)
        //numberClockTextView = findViewById<TextView>(R.id.number_clock)
        clockView = findViewById<ClockView>(R.id.clock)
        var updateTimeThread = UpdateTimeThread()
        updateTimeThread.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
    }


    inner class UpdateTimeThread : Thread() {
        override fun run() {
            super.run()
            updateSecond()
        }

        private fun updateSecond() {
            while (true) {
                //写入和输出日志
                Log.d("clockupdate", "abababa")
                //创建msg
                val msg = Message.obtain()
                msg.what = STATUS_ONE_SECOND
                handler.sendMessageDelayed(msg, 500)
            }
        }
    }
}