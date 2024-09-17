package com.bytedance.jstu.demo.lesson7

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import com.bytedance.jstu.demo.R

class CustomView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //继承View，并引入图片资源
        //BitmapFactory当中的decode一个方法
        //参数（资源目录，资源id）此处没有option配置项，采用系统默认
        val iconbit = BitmapFactory.decodeResource(resources, R.drawable.image2)
        //把加载的bitmap传过来 画出图片
        canvas.drawBitmap(iconbit, 20f, 20f, null)

    }
}