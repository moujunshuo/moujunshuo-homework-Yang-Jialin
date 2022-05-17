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

class MyPictureActivity : AppCompatActivity() {

    private val pages: MutableList<View> = ArrayList()
    lateinit var viewPager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Fresco.initialize(this)
        setContentView(R.layout.activity_image)
        viewPager = findViewById(R.id.media_view_pager)
        addImage("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fwww.gxdmw.com%2Fdata%2Fattachment%2Fportal%2F202101%2F08%2F083118ynsxfait2eviimmq.jpg&refer=http%3A%2F%2Fwww.gxdmw.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1652442609&t=4abbdb8e39d6d3dbb4d5211d9531d2c7")
        //path 是相对路径，url 是绝对路径。url还可以表示网页资源地址

        //uri是另一种身份标识

        //先打开一个文件file()
        //从打开的这个文件获取一个uri类
        //val sdcardUri = Uri.fromFile(File("/sdcard/fileimage.jpg")) // For files on device
        //addImage(sdcardUri)
        addImage("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fdesk-fd.zol-img.com.cn%2Ft_s960x600c5%2Fg2%2FM00%2F00%2F0B%2FChMlWl6yKqyILFoCACn-5rom2uIAAO4DgEODxAAKf7-298.jpg&refer=http%3A%2F%2Fdesk-fd.zol-img.com.cn&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1652442465&t=916c4eaf14ec65eb4c14d4d9e14b20b7")
        //addRawImage(R.raw.rawimage)
        addImage("https://img.zcool.cn/community/01accf5d6dd08fa801211f9ea41c60.gif")
        addImage("https://tse1-mm.cn.bing.net/th/id/R-C.4d24728b5a1ddd5bf47b1654911bafc2?rik=hPXwOP3TCsnmjw&riu=http%3a%2f%2fn.sinaimg.cn%2fcomic%2fgif_image%2fw600h336%2f20171130%2f20HD-fypikws9109683.gif&ehk=LbXFdHLOg%2bWP3wsJVhdMS5yPo9IV3Y7trMRDR5hiVaE%3d&risl=&pid=ImgRaw&r=0")
        val adapter = ViewAdapter()
        adapter.setDatas(pages)
        viewPager.adapter = adapter
    }

    private fun addImage(path: String) {
        //要用fresco自己的item类
        var uri=Uri.parse(path)
        val imageView =
            layoutInflater.inflate(R.layout.activity_fresco_item, null) as SimpleDraweeView
        //使⽤图⽚的URI地址设置 ImageView显示的图⽚
        //imageView.setImageURI(uri)
        val controller: DraweeController = Fresco.newDraweeControllerBuilder()
            //从上文建立的imageRequest找到uri
            .setUri(uri)
            //实现动图展示
            //需要添加一个依赖类
                //    implementation 'com.facebook.fresco:animated-gif:0.12.0'
                //版本号和freco的版本号相同
            .setAutoPlayAnimations(true)
            .build()
        //设置中心展示
        imageView.hierarchy.actualImageScaleType = ScalingUtils.ScaleType.FIT_CENTER
        imageView.controller = controller

        pages.add(imageView)
    }

}