package com.bytedance.jstu.demo.lesson7

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.bytedance.jstu.demo.R
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.drawable.ScalingUtils
import com.facebook.drawee.interfaces.DraweeController
import com.facebook.drawee.view.SimpleDraweeView
import com.facebook.imagepipeline.request.ImageRequestBuilder
import java.io.File
import java.util.*

class FrescoImageActivity : AppCompatActivity() {

    private val pages: MutableList<View> = ArrayList()
    lateinit var viewPager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Fresco.initialize(this)
        setContentView(R.layout.activity_image)
        viewPager = findViewById(R.id.media_view_pager)
        addImage("res:/" + R.drawable.drawableimage)



        //path 是相对路径，url 是绝对路径。url还可以表示网页资源地址

        //uri是另一种身份标识

        //先打开一个文件file()
        //从打开的这个文件获取一个uri类
        val sdcardUri = Uri.fromFile(File("/sdcard/fileimage.jpg")) // For files on device
        addImage(sdcardUri)
        addImage("asset:/assetsimage.jpg")
        addRawImage(R.raw.rawimage)
        addImage("https://img.zcool.cn/community/01accf5d6dd08fa801211f9ea41c60.gif")
        val adapter = ViewAdapter()
        adapter.setDatas(pages)
        viewPager.adapter = adapter
    }

    private fun addImage(uri: Uri) {
        //要用fresco自己的item类
        val imageView =
            layoutInflater.inflate(R.layout.activity_fresco_item, null) as SimpleDraweeView
        //使⽤图⽚的URI地址设置 ImageView显示的图⽚
        imageView.setImageURI(uri)
        //设置中心展示
        imageView.hierarchy.actualImageScaleType = ScalingUtils.ScaleType.FIT_CENTER
        pages.add(imageView)
    }

    private fun addImage(path: String) {
        //parse方法返回的是一个URI类型，通过这个URI可以访问一个网络上或者是本地的资源F
        addImage(Uri.parse(path))
    }

    private fun addRawImage(resId: Int) {
        //根据传入的资源id建立一个图片请求
        val imageRequest = ImageRequestBuilder.newBuilderWithResourceId(resId).build()
        //DraweeController 负责和image pipeline交互，
        //实现对所要显示的图⽚做更多的控制，例如视图层级等
        val controller: DraweeController = Fresco.newDraweeControllerBuilder()
            //从上文建立的imageRequest找到uri
            .setUri(imageRequest.sourceUri)
            //设置是否自动播放
            //实现动图展示
            .setAutoPlayAnimations(true)
            .build()
        val imageView =
            //只能用它自己封装好了的一个组件
            layoutInflater.inflate(R.layout.activity_fresco_item, null) as SimpleDraweeView
        imageView.hierarchy.actualImageScaleType = ScalingUtils.ScaleType.FIT_CENTER
        //实现图片控制
        imageView.controller = controller
        pages.add(imageView)
    }
}