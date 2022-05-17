package com.bytedance.jstu.demo

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bytedance.jstu.demo.lesson8.MultimediaActivity

import com.bytedance.jstu.demo.lesson8.mCameraActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        addLesson("第一讲 Android简介", BasicUIDemoActivity::class.java)
//        addLesson("第二讲 基本用户界面开发", BasicUIDemoActivity::class.java)
//        addLesson("第三讲 UI开发进阶", Lesson3DemoActivity::class.java)MultimediaActivity
//        addLesson("第四讲 复杂应用组件", LessonListActivity::class.java)
//        addLesson("第五讲 网络", BasicNetActivity::class.java)
//        addLesson("第六讲 存储", StorageActivity::class.java)
//        addLesson("第七讲 多媒体基础", BaseMultimediaActivity::class.java)
        addLesson("我的自定义相机", MultimediaActivity::class.java)
//        addLesson("第九讲 新技术趋势", BasicUIDemoActivity::class.java)
    }

    private fun addLesson(text: String, activityClass: Class<*>) {
        val btn = AppCompatButton(this)
        btn.text = text
        btn.isAllCaps = false
        findViewById<ViewGroup>(R.id.container).addView(btn)
        btn.setOnClickListener {
            startActivity(Intent().apply {
                setClass(this@MainActivity, activityClass)
            })
        }
    }
//    companion object{
//        private const val PERMISSION_REQUEST_CODE = 1001
//    }
//
////    override fun onCreate(savedInstanceState: Bundle?) {
////        super.onCreate(savedInstanceState)
////        setContentView(R.layout.activity_multimedia)
////    }
//
//
//    fun mCamera(view: View) {
//        requestPermissionOfMine()
//    }
//
//
//    private fun mVideo() {
//        mCameraActivity.startUI(this)
//    }
//
//
//    private fun requestPermissionOfMine() {
//        //在自定义相机中检查麦克风和摄像头的权限
//        //下面两个变量都是bool形变量 判断及时获取的权限信息和权限同意的常量是否一致
//
//        //要检查用户是否已向您的应用授予特定权限，请将该权限传递给 ContextCompat.checkSelfPermission() 方法。
//        // 此方法返回 PERMISSION_GRANTED 或PERMISSION_DENIED，具体取决于您的应用是否具有权限。
//        //第一个参数需要传入Context
//        //第二个参数需要传入需要检测的权限
//        val hasCameraPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
//        val hasAudioPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED
//        if (hasCameraPermission && hasAudioPermission) {
//            mVideo()
//        } else {
//            //若没有权限 就把对应权限加入到一个集合里 最后统一获取权限
//            val permission: MutableList<String> = ArrayList()
//            if (!hasCameraPermission) {
//                permission.add(Manifest.permission.CAMERA)
//            }
//            if (!hasAudioPermission) {
//                permission.add(Manifest.permission.RECORD_AUDIO)
//            }
//            //用于申请相应的权限
//            //三个参数，分别是Activity的实例、String数组、请求码
//            //字符串数组中的内容是Manifest.permission.CAMERA Manifest.permission.RECORD_AUDIO
//            //它们也是一个字符串变量
//            ActivityCompat.requestPermissions(this, permission.toTypedArray(), PERMISSION_REQUEST_CODE)
//        }
//    }
}