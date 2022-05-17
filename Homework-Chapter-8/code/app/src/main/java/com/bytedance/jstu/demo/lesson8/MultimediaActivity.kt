package com.bytedance.jstu.demo.lesson8

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bytedance.jstu.demo.R

class MultimediaActivity: AppCompatActivity() {

    companion object{
        private const val PERMISSION_REQUEST_CODE = 1001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_multimedia)
    }

//    fun systemTakePicture(view: View) {
//        SystemCameraActivity.startUI(this)
//    }

//    fun systemRecord(view: View) {
//        SystemRecordActivity.startUI(this)
//    }

//    fun customCamera(view: View) {
//        requestPermission()
//    }

//    private fun recordVideo() {
//        CustomCameraActivity.startUI(this)
//    }

//    private fun requestPermission() {
//        val hasCameraPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
//        val hasAudioPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED
//        if (hasCameraPermission && hasAudioPermission) {
//            recordVideo()
//        } else {
//            val permission: MutableList<String> = ArrayList()
//            if (!hasCameraPermission) {
//                permission.add(Manifest.permission.CAMERA)
//            }
//            if (!hasAudioPermission) {
//                permission.add(Manifest.permission.RECORD_AUDIO)
//            }
//            ActivityCompat.requestPermissions(this, permission.toTypedArray(), PERMISSION_REQUEST_CODE)
//        }
//    }
//
//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String?>, grantResults: IntArray) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        var hasPermission = true
//        for (grantResult in grantResults) {
//            if (grantResult != PackageManager.PERMISSION_GRANTED) {
//                hasPermission = false
//                break
//            }
//        }
//        if (hasPermission) {
//            recordVideo()
//        } else {
//            Toast.makeText(this, "权限获取失败", Toast.LENGTH_SHORT).show()
//        }
//    }

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_multimedia)
//    }


    fun mCamera(view: View) {
        requestPermissionOfMine()
    }


    private fun mVideo() {
        mCameraActivity.startUI(this)
    }


    private fun requestPermissionOfMine() {
        //在自定义相机中检查麦克风和摄像头的权限
        //下面两个变量都是bool形变量 判断及时获取的权限信息和权限同意的常量是否一致

        //要检查用户是否已向您的应用授予特定权限，请将该权限传递给 ContextCompat.checkSelfPermission() 方法。
        // 此方法返回 PERMISSION_GRANTED 或PERMISSION_DENIED，具体取决于您的应用是否具有权限。
        //第一个参数需要传入Context
        //第二个参数需要传入需要检测的权限
        val hasCameraPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
        val hasAudioPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED
        if (hasCameraPermission && hasAudioPermission) {
            mVideo()
        } else {
            //若没有权限 就把对应权限加入到一个集合里 最后统一获取权限
            val permission: MutableList<String> = ArrayList()
            if (!hasCameraPermission) {
                permission.add(Manifest.permission.CAMERA)
            }
            if (!hasAudioPermission) {
                permission.add(Manifest.permission.RECORD_AUDIO)
            }
            //用于申请相应的权限
            //三个参数，分别是Activity的实例、String数组、请求码
            //字符串数组中的内容是Manifest.permission.CAMERA Manifest.permission.RECORD_AUDIO
            //它们也是一个字符串变量
            ActivityCompat.requestPermissions(this, permission.toTypedArray(), PERMISSION_REQUEST_CODE)
        }
    }

}