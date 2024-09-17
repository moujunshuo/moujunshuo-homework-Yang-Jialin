package com.bytedance.jstu.demo.lesson7

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bytedance.jstu.demo.R
import java.util.ArrayList

class GlideActivity : AppCompatActivity() {
    private val pages: MutableList<View> = ArrayList()
    lateinit var viewPager: ViewPager
    @SuppressLint("SdCardPath")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)
        viewPager = findViewById(R.id.media_view_pager)

        addImage(R.drawable.drawableimage)
        addImage(R.drawable.ic_markunread)

        //无SD卡 展示默认的错误图片
        addImage("/sdcard/fileimage.jpg")
        addImage("file:///android_asset/assetsimage.jpg")
        addImage(R.raw.rawimage)

        addImage("https://t7.baidu.com/it/u=4162611394,4275913936&fm=193&f=GIF")

        //放入一个滑动显示的adapter当中
        //同样的流程：设置adapter，adapter当中设置一个想要展示的内容，
        // 把adapter给viewpager
        val adapter = ViewAdapter()
        adapter.setDatas(pages)
        viewPager.adapter = adapter
    }

    //资源是apk内部当中的
    private fun addImage(resId: Int) {
        val imageView =
            layoutInflater.inflate(R.layout.activity_base_multimedia_image_item, null) as ImageView
        //常规流程
        Glide.with(this)
            //加载资源
            .load(resId)
            //错误的时候显示一个error的图片
            .error(R.drawable.error)
            //传一个显示这个图片的载体进去
            .into(imageView)
        pages.add(imageView)
    }

    //资源是一个外部链接给的
    private fun addImage(path: String) {
        val imageView =
            layoutInflater.inflate(R.layout.activity_base_multimedia_image_item, null) as ImageView
        Glide.with(this)
            .load(path)
            //加了缓存策略：圆角？圆形？
            //circleCrop()圆形展示图片
            //DiskCacheStrategy.ALL ：表示既缓存原始图片，也缓存转换过后的图片。
            .apply(RequestOptions().circleCrop().diskCacheStrategy(DiskCacheStrategy.ALL))
            .error(R.drawable.error)
            .into(imageView)
        pages.add(imageView)
    }
}