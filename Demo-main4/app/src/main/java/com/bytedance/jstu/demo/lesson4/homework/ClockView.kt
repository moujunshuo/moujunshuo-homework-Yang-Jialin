package com.bytedance.jstu.demo.lesson4.homework

import android.app.Notification
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Handler
import android.os.Message
import android.util.AttributeSet
import android.view.View
import com.bytedance.jstu.demo.MainActivity
import com.google.android.material.internal.ViewUtils
import java.lang.ref.WeakReference
import java.util.*
import kotlin.concurrent.timerTask
import kotlin.math.cos
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sin

/**
 *  author : neo
 *  time   : 2021/10/25
 *  desc   :
 */
class ClockView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    /**inner class MyHandler(private var activity: WeakReference<MainActivity>) : Handler() {
    override fun handleMessage(msg: Message) {
    super.handleMessage(msg)
    if (msg.what == 1) {

    handler.sendEmptyMessageDelayed(1,1000)
    }

    }
    }
     */
    val textArray = arrayOf(12,1, 2, 3,4,5,6,7,8,9,10,11)

    //建立画笔
    private var paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var textPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)


    var hour:Int=0
    var minute:Int=0
    var second:Int=0
    var hourText:String=""
    var minuteText:String=""
    var secondText:String=""
    var timeText:String=""



    //var calendar = Calendar.getInstance()
    init {
        //需要执行的任务
        //hour = calendar.get(Calendar.HOUR_OF_DAY)
        //minute = calendar.get(Calendar.MINUTE)
        //second = calendar.get(Calendar.SECOND)
        //invalidate()
        textPaint.color =  Color.WHITE
        textPaint.textSize = 60f
        doInvalidate()

    }

    fun numToString(){
        hourText=hour.toString()
        minuteText=minute.toString()
        secondText=second.toString()

        //$用于连接不同的字符串使之组成一个新字符串
        timeText="$hourText : $minuteText : $secondText"
    }
    fun doInvalidate() {
        Calendar.getInstance().run {
            hour = get(Calendar.HOUR_OF_DAY)
            minute = get(Calendar.MINUTE)
            second = get(Calendar.SECOND)


            //这里将三个角度与实际时间关联起来，当前几点几分几秒，就把相应的圈逆时针旋转多少
//            mHourDeg = -360 / 12f * (hour - 1)
//            mMinuteDeg = -360 / 60f * (minute - 1)
//            mSecondDeg = -360 / 60f * (second - 1)
            numToString()
            invalidate()
        }
    }





    //Triple用于同时返回多个数值
    /**public fun getTime(): Triple<Int, Int,Int> {
    Calendar.getInstance();
    var hour=Calendar.HOUR;
    var minute=Calendar.MINUTE;
    var second=Calendar.SECOND;
    return Triple(
    hour,minute,second
    );
    }
     */

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        //绘制表盘
        paint.color = Color.WHITE
        paint.style=Paint.Style.STROKE
        //外圈钟表
        paint.strokeWidth=8f
        canvas?.drawCircle(width.toFloat()/2, width.toFloat()/2, width.toFloat()/2-20f, paint)
        //内圈钟表
        paint.strokeWidth=4f
        canvas?.drawCircle(width.toFloat()/2, width.toFloat()/2, width.toFloat()/2-35f, paint)
        //中心点
        paint.style=Paint.Style.FILL
        canvas?.drawCircle(width.toFloat()/2, width.toFloat()/2, 15f, paint)
        //绘制刻度 利用旋转画布实现
        paint.strokeWidth=8f
        for(i in 0..11){
            canvas?.save();
            canvas?.rotate(360f*i/12,width.toFloat()/2,width.toFloat()/2)


            canvas?.drawLine(width.toFloat()/2,60f,width.toFloat()/2,40f,paint)

            canvas?.restore()

            //绘制文字的起始坐标9
            val startX = (width.toFloat() / 2 + (width.toFloat()/2-100f) * Math.sin(Math.PI / 6 * i) - textPaint.measureText(textArray[i].toString()) / 2).toFloat()
            val startY = (width.toFloat() / 2 -( width.toFloat()/2-100f) * Math.cos(Math.PI / 6 * i) + textPaint.measureText(textArray[i].toString()) / 2).toFloat()
            canvas?.drawText(textArray[i].toString(), startX, startY, textPaint)

        }
        //绘制指针
        paint.strokeWidth=8f
        //旋转画布实现
        canvas?.save();
        canvas?.rotate(30f*(hour)+0.5f*minute,width.toFloat()/2,width.toFloat()/2)
        canvas?.drawLine(width.toFloat()/2,width.toFloat()/2,width.toFloat()/2,width.toFloat()/3,paint)
        canvas?.restore()

        paint.strokeWidth=5f
        canvas?.save();
        canvas?.rotate(6f*minute,width.toFloat()/2,width.toFloat()/2)
        canvas?.drawLine(width.toFloat()/2,width.toFloat()/2,width.toFloat()/2,width.toFloat()/5,paint)
        canvas?.restore()

        paint.strokeWidth=3f
        canvas?.save();
        canvas?.rotate(6f*second,width.toFloat()/2,width.toFloat()/2)
        canvas?.drawLine(width.toFloat()/2,width.toFloat()/2,width.toFloat()/2,width.toFloat()/6,paint)
        canvas?.restore()

        //电子钟
        canvas?.drawText(timeText,width.toFloat()/2-(textPaint.measureText(timeText)/ 2).toFloat(), 1000f, textPaint)

    }

    //整体为原型，宽高相同
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val specWidth = MeasureSpec.getSize(widthMeasureSpec)
        val specHeight = MeasureSpec.getSize(heightMeasureSpec)

        //通过测量规则获取测量模式
        val specWidthMode = MeasureSpec.getMode(widthMeasureSpec)
        val specHeightMode = MeasureSpec.getMode(heightMeasureSpec)

        //var Result = 0

        var width=0
        var height=0


        //是下文的一个函数

        //当宽度在布局使用时是设定为精确数值
        if (specWidthMode == MeasureSpec.EXACTLY&&specHeightMode == MeasureSpec.EXACTLY) {
            /**Result = if (specHeightMode == MeasureSpec.EXACTLY) {
            min(specHeight, specWidth);
            } else {
            specWidth;
            }
            }

            //当宽度wrap_content
            else{
            Result=if (specHeightMode == MeasureSpec.EXACTLY) {
            specWidth;
            } else {
            800;
            }*/
            width=specWidth
            height=specHeight
        }
        else{
            width=800
            height=1200
        }
        setMeasuredDimension(width, height)
    }




}