package com.bytedance.jstu.demo.lesson7

import android.graphics.PixelFormat
import android.os.Bundle
import android.widget.Button
import android.widget.MediaController
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import com.bytedance.jstu.demo.R


class MyVideoActivity : AppCompatActivity() {

    private lateinit var videoView: VideoView
    private lateinit var buttonPlay: Button
    private lateinit var buttonPause: Button
    private lateinit var buttonReplay: Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_myvideo)

        videoView = findViewById(R.id.videoView)
        buttonPause = findViewById(R.id.buttonPause)
        buttonPlay = findViewById(R.id.buttonPlay)
        buttonReplay= findViewById(R.id.buttonReplay)




        buttonPause.setOnClickListener { videoView.pause() }
        buttonPlay.setOnClickListener { videoView.start() }
        buttonReplay.setOnClickListener { videoView.resume() }

        //MediaController组件用于通过图形控制界面来控制视频的播放。
        val mc = MediaController(this)
        //设置绑定的主视图。
        mc.setAnchorView(videoView)
        videoView.setMediaController(mc) //设置VedioView与MediaController相关联


        videoView.holder.setFormat(PixelFormat.TRANSPARENT)
        //把SurfaceView置于Activity显示窗口的最顶层
        videoView.setZOrderOnTop(true)

        //设置资源路径 即可自动播放
        videoView.setVideoPath("http://poss.videocloud.cns.com.cn/oss/2020/07/19/chinanews/MEIZI_YUNSHI/onair/F1B171FB2ECB4319ADAC3FF2915C7E4B.mp4")
        videoView.requestFocus()
    }

    //资源id转化成path
    private fun getVideoPath(resId: Int): String {
        return "android.resource://" + this.packageName + "/" + resId
    }




}