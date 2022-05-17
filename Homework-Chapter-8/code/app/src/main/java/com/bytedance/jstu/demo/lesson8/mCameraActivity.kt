package com.bytedance.jstu.demo.lesson8

import android.content.Context
import com.bytedance.jstu.demo.lesson8.PathUtils.rotateImage
import androidx.appcompat.app.AppCompatActivity
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.media.MediaRecorder
import android.os.Bundle
import com.bytedance.jstu.demo.R
import android.media.CamcorderProfile
import android.os.Environment
import android.hardware.Camera.PictureCallback
import android.graphics.BitmapFactory
import android.content.Intent
import android.graphics.ImageFormat
import android.hardware.Camera
import android.view.View
import android.widget.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.lang.Exception
import java.lang.IllegalStateException
import java.text.SimpleDateFormat
import java.util.*

class mCameraActivity : AppCompatActivity(), SurfaceHolder.Callback {
    private lateinit var surfaceView: SurfaceView
    private  var camera: Camera? = null
    private var mediaRecorder: MediaRecorder? = null
    private lateinit var holder: SurfaceHolder
    private lateinit var imageView: ImageView
    private lateinit var videoView: VideoView
    private lateinit var recordButton: Button
    private var isRecording = false
    private var mp4Path = ""
    private var video_text:TextView?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_m_camera)
        surfaceView = findViewById(R.id.surfaceview)
        imageView = findViewById(R.id.iv_img)
        videoView = findViewById(R.id.videoview)
        //recordButton = findViewById(R.id.bt_record)
        video_text=findViewById(R.id.text_video)
        holder = surfaceView.holder
        initCamera()
        holder.addCallback(this)
    }

    //初始化摄像机
    private fun initCamera() {
        camera = Camera.open()

        camera?.let {
            val parameters = it.parameters
            //照片格式
            parameters.pictureFormat = ImageFormat.JPEG
            //聚焦格式
            parameters.focusMode = Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE
            //闪光灯
            //需要获取权限
            parameters.flashMode = Camera.Parameters.FLASH_MODE_ON
            //横竖屏
            parameters["orientation"] = "portrait"
            //旋转角度
            parameters["rotation"] = 90
            it.parameters = parameters
            it.setDisplayOrientation(90)
            //Toast.makeText(this, "hhhhhh", Toast.LENGTH_SHORT).show()
        }
    }

    //准备工作
    private fun prepareVideoRecorder(): Boolean {
        val mediaRecorder = MediaRecorder()
        this.mediaRecorder = mediaRecorder

        // Step 1: Unlock and set camera to MediaRecorder
        camera?.unlock()
        mediaRecorder.setCamera(camera)

        // Step 2: Set sources
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER)
        mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA)

        // Step 3: Set a CamcorderProfile (requires API Level 8 or higher)
        mediaRecorder.setProfile(CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH))

        // Step 4: Set output file
        mp4Path = outputMediaPath
        mediaRecorder.setOutputFile(mp4Path)

        // Step 5: Set the preview output
        mediaRecorder.setPreviewDisplay(holder.surface)
        mediaRecorder.setOrientationHint(90)

        // Step 6: Prepare configured MediaRecorder
        try {
            mediaRecorder.prepare()
        } catch (e: IllegalStateException) {
            releaseMediaRecorder()
            return false
        } catch (e: IOException) {
            releaseMediaRecorder()
            return false
        }
        return true
    }

    //释放资源
    private fun releaseMediaRecorder() {
        mediaRecorder?.let { mediaRecorder->
            mediaRecorder.reset() // clear recorder configuration
            mediaRecorder.release() // release the recorder object
            this.mediaRecorder = null
            camera?.lock() // lock camera for later use
        }
    }

    private val outputMediaPath: String
        private get() {
            val mediaStorageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
            val mediaFile = File(mediaStorageDir, "IMG_$timeStamp.mp4")
            if (!mediaFile.exists()) {
                mediaFile.parentFile.mkdirs()
            }
            return mediaFile.absolutePath
        }

    fun takePhoto(view: View) {
        camera?.takePicture(null, null, pictureCallback)
    }

    //获取照片中的接口回调
    var pictureCallback = PictureCallback { data, camera ->
        var fos: FileOutputStream? = null
        val filePath = getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!.absolutePath + File.separator + "1.jpg"
        val file = File(filePath)
        try {
            fos = FileOutputStream(file)
            fos.write(data)
            //刷新缓冲区
            fos.flush()
            val bitmap = BitmapFactory.decodeFile(filePath)
            val rotateBitmap = rotateImage(bitmap, filePath)
            //imageView控件可见
            imageView.visibility = View.VISIBLE
            //videoView控件不可见 但这个View在ViewGroup中不保留位置，会重新layout，不再占用空间，那后面的view就会取代他的位置，
            videoView.visibility = View.GONE
            imageView.setImageBitmap(rotateBitmap)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            this.camera?.startPreview()
            if (fos != null) {
                try {
                    fos.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    //开始结束录制
    //Start MediaRecorder
    //❏ Stop MediaRecorder
    //❏ Reset MediaRecorder
    //❏ Release MediaRecorder
    //❏ Lock the Camera
    //❏ 播放视频
    fun record(view: View) {
        if (isRecording && mediaRecorder != null) {
            video_text!!.text = "录制"
            val mediaRecorder = this.mediaRecorder ?:return

            //设置监视器
            mediaRecorder.setOnErrorListener(null)
            mediaRecorder.setOnInfoListener(null)
            //绑定预览位置
            mediaRecorder.setPreviewDisplay(null)
            try {
                mediaRecorder.stop()
            } catch (e: Exception) {
                e.printStackTrace()
            }

            mediaRecorder.reset()
            mediaRecorder.release()
            this.mediaRecorder = null
            camera?.lock()
            videoView.visibility = View.VISIBLE
            imageView.visibility = View.GONE
            videoView.setVideoPath(mp4Path)
            videoView.start()
        } else {
            //如果准备函数返回了一个true值
            if (prepareVideoRecorder()) {
                video_text!!.text = "停止"
                mediaRecorder!!.start()
            }
        }
        isRecording = !isRecording
    }

    //摄像头数据实时显示
    //用surfaceview显示
    override fun surfaceCreated(holder: SurfaceHolder) {
        try {
            camera?.let {
                it.setPreviewDisplay(holder)
                //开始预览效果
                it.startPreview()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    //界面有切换 比如屏幕旋转
    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        if (holder.surface == null) {
            return
        }
        //停止预览效果
        camera?.stopPreview()
        //重新设置预览效果
        try {
            camera?.let {
                it.setPreviewDisplay(holder)
                it.startPreview()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        camera?.let {
            //停止预览
            it.stopPreview()
            //释放资源
            it.release()
        }
    }

    //生命周期
    override fun onResume() {
        super.onResume()
        if (camera == null) {
            initCamera()
        }
        camera?.startPreview()
    }

    override fun onPause() {
        super.onPause()
        camera?.stopPreview()
    }

    companion object {
        fun startUI(context: Context) {
            val intent = Intent(context, mCameraActivity::class.java)
            context.startActivity(intent)
        }
    }
}