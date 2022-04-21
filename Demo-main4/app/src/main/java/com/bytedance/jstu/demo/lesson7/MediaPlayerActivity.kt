package com.bytedance.jstu.demo.lesson7

import android.graphics.PixelFormat
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.bytedance.jstu.demo.R
import java.io.IOException

class MediaPlayerActivity : AppCompatActivity() {
    //设置一个player
    //进入idle状态
    private val player: MediaPlayer by lazy {
        MediaPlayer()
    }
    private var holder: SurfaceHolder? = null

    private lateinit var surfaceView: SurfaceView
    private lateinit var buttonPlay: Button
    private lateinit var buttonPause: Button

    @RequiresApi(api = Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "MediaPlayer"
        setContentView(R.layout.layout_media_player)

        surfaceView = findViewById(R.id.surfaceView)
        buttonPlay = findViewById(R.id.buttonPlay)
        buttonPause = findViewById(R.id.buttonPause)

        try {
            //加载视频资源
            player.setDataSource(resources.openRawResourceFd(R.raw.big_buck_bunny))
            holder = surfaceView.holder
            //将surfaceView设置为背景透明
            holder?.setFormat(PixelFormat.TRANSPARENT)
            //添加一个回调函数
            holder?.addCallback(PlayerCallBack())
            //主线程准备
            player.prepare()
            //准备过程的监听 准备完成自动播放
            player.setOnPreparedListener { // 自动播放
                player.start()
                //播放完后重新播放
                player.isLooping = true
            }
            //网络流媒体的缓冲变化时回调
            player.setOnBufferingUpdateListener { mp, percent -> println(percent) }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        //暂停 播放的实现
        buttonPlay.setOnClickListener { player.start() }
        buttonPause.setOnClickListener { player.pause() }
    }

    override fun onPause() {
        super.onPause()
        //停止播放
        player.stop()
        //释放资源
        player.release()
    }

    //回调
    private inner class PlayerCallBack : SurfaceHolder.Callback {
        //创建
        override fun surfaceCreated(holder: SurfaceHolder) {
            player.setDisplay(holder)
        }

        //调整格式
        override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {}
        //销毁view 释放和回溯资源
        override fun surfaceDestroyed(holder: SurfaceHolder) {}
    }
}