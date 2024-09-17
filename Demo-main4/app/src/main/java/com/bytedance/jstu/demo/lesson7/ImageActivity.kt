package com.bytedance.jstu.demo.lesson7

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.DrawableCompat
import androidx.viewpager.widget.ViewPager
import com.bytedance.jstu.demo.R
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.util.*

class ImageActivity : AppCompatActivity() {
    //lateinit延迟初始化
    private lateinit var viewPager: ViewPager

    //建立了一个可变大小的元素集合
    private val pages: MutableList<View> = ArrayList()

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)
        viewPager = findViewById(R.id.media_view_pager)
        //加载
        //（五张图片？？？？？
        viewPager.post {

            //六种方法加载

            //tp图
            //1
            addImage(
                //从apk资源中加载一个bitmap
                decodeBitmapFromResource(
                    resources,
                    R.drawable.large,
                    200,
                    400
                )
            )

            //邮箱
            //2
            //先通过decodeBitmapFromVectorResource获取一个bitmap
            //再用addImage往page当中加入新的imageview
            //问号前的东西不为null的条件下执行let函数体内的东西
            decodeBitmapFromVectorResource(R.drawable.ic_markunread)?.let { addImage(it) }

            //从SD卡加载图片 模拟手机当中没有
            //3
            // ReadFileTask(viewPager.width, viewPager.height).execute("/sdcard/fileimage.jpg")

            //目录Assets
            //execute()异步线程任务
            //4
            ReadAssetsTask(viewPager.width, viewPager.height).execute("assetsimage.jpg")

            //目录Raw
            //5
            ReadRawTask(viewPager.width, viewPager.height).execute(R.raw.rawimage)

            //6
            //加载网络上的图片
            loadNetImage(viewPager.width, viewPager.height)

            val adapter = ViewAdapter()
            adapter.setDatas(pages)
            viewPager.adapter = adapter
        }
    }

    private fun loadNetImage(width: Int, height: Int) {
        val imageView = layoutInflater.inflate(R.layout.activity_image_item, null) as ImageView
        pages.add(imageView)
        Thread {
            val bitmap = decodeBitmapFromNet(
                "https://t7.baidu.com/it/u=4162611394,4275913936&fm=193&f=GIF",
                width,
                height
            )
            //当需要更新UI的时候我们需要“返回”到主线程
            runOnUiThread { addImageAsyn(imageView, bitmap) }
        }.start()
    }

    //从网络获取信息
    //同样是先子线程再主线程
    private fun decodeBitmapFromNet(url: String, reqWidth: Int, reqHeight: Int): Bitmap? {
        var input: InputStream? = null
        var data: ByteArray? = null
        try {
            val imgUrl = URL(url)//一个URL类
            //建立一个HttpURLConnection类
            //建立网络连接
            val conn = imgUrl.openConnection() as HttpURLConnection
            //如果打算使用 URL 连接进行输入，则将 DoInput 标志设置为 true
            conn.doInput = true
            conn.connect()
            input = conn.inputStream
            data = inputStreamToByteArray(input)
        } catch (e: MalformedURLException) {
            e.printStackTrace()
            return null
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        } finally {
            try {
                input?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        //若有数据传回来则建立bitmap
        return if (data != null) {
            val options = BitmapFactory.Options()

            options.inJustDecodeBounds = true
            BitmapFactory.decodeByteArray(data, 0, data.size, options)
            options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight)
            options.inJustDecodeBounds = false
            BitmapFactory.decodeByteArray(data, 0, data.size, options)
        } else {
            null
        }
    }

    companion object {
        fun inputStreamToByteArray(input: InputStream?): ByteArray {
            //字节数组输出流在内存中创建一个字节数组缓冲区，所有发送到输出流的数据保存在该字节数组缓冲区中。
            //为该函数输出内容
            val outputStream = ByteArrayOutputStream()
            input ?: return outputStream.toByteArray()
            val buffer = ByteArray(1024)
            var len: Int
            try {
                while (input.read(buffer).also { len = it } != -1) {
                    outputStream.write(buffer, 0, len)
                }
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                try {
                    input.close()
                    outputStream.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            return outputStream.toByteArray()
        }
    }

    private fun addImage(bitmap: Bitmap) {
        //在RecyclerView的Adapter中为item添加布局
        //第一个参数：想要添加的布局
        //第二个参数：想要添加到哪个布局上面
        //null和有值的区别 null时第一个参数中最外层的布局大小无效，有值的时候最外层的布局大小有效）
        val imageView = layoutInflater.inflate(R.layout.activity_image_item, null) as ImageView
        //使⽤Bitmap对象设置 ImageView显示的图⽚
        imageView.setImageBitmap(bitmap)
        //加入到page中去
        pages.add(imageView)
    }

    private fun decodeBitmapFromResource(
        res: Resources,
        resId: Int,
        reqWidth: Int,
        reqHeight: Int
    ): Bitmap {
        //新建一个option对象，包括处理的一些操作
        val options = BitmapFactory.Options()
        //inJustDecodeBounds为true，不返回bitmap，只返回这个bitmap的尺⼨
        options.inJustDecodeBounds = true
        //只返回尺寸
        BitmapFactory.decodeResource(res, resId, options)
        //利⽤返回的原图⽚的宽⾼，我们就可以计算出缩放⽐inSampleSize(只能是2的整数次幂)
        //若不是 则自动向下取整为二次幂的倍数
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight)
        options.inJustDecodeBounds = false
        //此时返回的是bitmap
        return BitmapFactory.decodeResource(res, resId, options)
    }

    private fun calculateInSampleSize(
        options: BitmapFactory.Options,
        reqWidth: Int,
        reqHeight: Int
    ): Int {
        /**
         * todo calculate sampleSize
         */
        return 2
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    //返回一个bitmap
    private fun decodeBitmapFromVectorResource(resId: Int): Bitmap? {
        //获取drawable————一些可以绘制在 Canvas 上的对象
        var drawable = getDrawable(resId)
        // Build.VERSION      获取android系统的版本信息。
        //版本号   Build.VERSION.SDK_INT

        // Build.VERSION_CODES 这个类封装了已经存在的SDK框架及android版本。(历史）
        // LOLLIPOP是一个固定的int类型 值为21
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            //在v4支持库中引入了DrawableCompat类，它介绍了前Lollipop设备的色彩功能。
                //因此需要对版本号进行判断
            //wrap：在SDK版本 >= 23时直接返回了原来的Drawable实例，其他情况返回了DrawableWrapperApi14的一个新实例，
                // 为其生成一个DrawableWrapperState，并且移除了该新实例关联过的ColorFilter，
                // 将该新实例的绘制范围和原始Drawable实例保持一致。
            //mutate:获取Drawable初始实例时候如果不使用mutate()，那么我们对这个Drawable进行着色，
                // 不仅改变了当前Drawable实例的颜色，以后任何通过这个图片获取到的Drawable实例，
                // 都会具有之前设置的颜色。
            drawable = drawable?.let { DrawableCompat.wrap(it).mutate() }
        }
        val bitmap = drawable?.let {
            //根据参数创建新位图
            //int width    The width of the bitmap
            //int height   The height of the bitmap
            //config   The bitmap config to create
            Bitmap.createBitmap(
                it.intrinsicWidth,
                drawable.intrinsicHeight, Bitmap.Config.ARGB_8888
            )
        }
        val canvas = bitmap?.let { Canvas(it) }
        if (canvas != null) {
            //Drawable的setBounds方法有四个参数，
            // setBounds(int left, int top, int right, int bottom),
            // 这个四参数指的是drawable将在被绘制在canvas的哪个矩形区域内。
            drawable?.setBounds(0, 0, canvas.width, canvas.height)
        }
        if (canvas != null) {
            drawable?.draw(canvas)
        }
        return bitmap
    }

    @SuppressLint("StaticFieldLeak")
    //内部类
    private inner class ReadFileTask(val width: Int, val height: Int) :
        AsyncTask<String?, Void?, Bitmap>() {
        private val imageView: ImageView =
            layoutInflater.inflate(R.layout.activity_image_item, null) as ImageView

        //前后台都会进行
        //后台操作
        override fun doInBackground(vararg strings: String?): Bitmap? {
            //查找字符串第一个字符 若为空串则返回null
            return strings.firstOrNull()?.let {
                decodeBitmapFromFile(
                    it,
                    width,
                    height
                )
            }
        }

        //前台操作
        override fun onPostExecute(bitmap: Bitmap) {
            addImageAsyn(imageView, bitmap)
        }

        init {
            pages.add(imageView)
        }
    }

    private fun decodeBitmapFromFile(path: String, reqWidth: Int, reqHeight: Int): Bitmap {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeFile(path, options)
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight)
        options.inJustDecodeBounds = false
        return BitmapFactory.decodeFile(path, options)
    }

    private fun addImageAsyn(imageView: ImageView, bitmap: Bitmap?) {
        imageView.setImageBitmap(bitmap)
    }

    private inner class ReadAssetsTask(val width: Int, val height: Int) :
        AsyncTask<String?, Void?, Bitmap?>() {

        private val imageView: ImageView =
            layoutInflater.inflate(R.layout.activity_image_item, null) as ImageView

        override fun doInBackground(vararg strings: String?): Bitmap? {
            return strings.firstOrNull()?.let {
                decodeBitmapFromAssets(
                    this@ImageActivity,
                    it,
                    width,
                    height
                )
            }
        }

        override fun onPostExecute(bitmap: Bitmap?) {
            addImageAsyn(imageView, bitmap)
        }

        init {
            pages.add(imageView)
        }
    }

    private fun decodeBitmapFromAssets(
        context: Context,
        fileName: String,
        reqWidth: Int,
        reqHeight: Int
    ): Bitmap? {
        val asset = context.assets
        //通过传入的地址尝试打开文件
        val input = try {
            asset.open(fileName)
        } catch (e: IOException) {
            return null
        }
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeStream(input, null, options)
        try {
            //重置文件设置
            input.reset()
        } catch (e: IOException) {
            return null
        }
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight)
        options.inJustDecodeBounds = false
        val bitmap = BitmapFactory.decodeStream(input, null, options)
        try {
            input.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return bitmap
    }

    //因为耗时，所以先放在后台子线程当中去做，再放到主线程当中展示
    //继承AsyncTask这样的父类
    private inner class ReadRawTask(val width: Int, val height: Int) :
        AsyncTask<Int, Void?, Bitmap?>() {
        private val imageView: ImageView =
            layoutInflater.inflate(R.layout.activity_image_item, null) as ImageView

        //后台操作完后放到主线程展示
        override fun onPostExecute(bitmap: Bitmap?) {
            addImageAsyn(imageView, bitmap)
        }

        init {
            pages.add(imageView)
        }

        //后台的操作
        override fun doInBackground(vararg params: Int?): Bitmap? {
            return params.firstOrNull()?.let {
                //一个函数 decode资源
                decodeBitmapFromRaw(this@ImageActivity.resources, it, width, height)
            }
        }
    }

    private fun decodeBitmapFromRaw(
        res: Resources,
        resId: Int,
        reqWidth: Int,
        reqHeight: Int
    ): Bitmap? {
        val input = res.openRawResource(resId)
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeStream(input, null, options)
        try {
            input.reset()
        } catch (e: IOException) {
            return null
        }
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight)
        options.inJustDecodeBounds = false
        val bitmap = BitmapFactory.decodeStream(input, null, options)
        //异常处理
        try {
            input.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return bitmap
    }

}