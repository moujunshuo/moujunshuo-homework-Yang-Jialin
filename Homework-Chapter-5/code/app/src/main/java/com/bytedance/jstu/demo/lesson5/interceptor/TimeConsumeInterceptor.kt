package com.bytedance.jstu.demo.lesson5.interceptor

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
//拦截器
class TimeConsumeInterceptor : Interceptor {
    //Interceptor.Chain拦截器链
    override fun intercept(chain: Interceptor.Chain): Response {
        //System.nanoTime()---android系统开机到当前的时间。单位：纳秒
        val startTime = System.nanoTime()
        //调用chain.process(request)是每个拦截器实现的关键部分，该方法是所有HTTP请求产生响应的地方
        //request是用户构建好的
        //resp作用大概是构建一个响应来返回
        val resp = chain.proceed(chain.request())
        val endTime = System.nanoTime()
        val url = chain.request().url.toString()
        Log.d(
            "Lesson05/TimeConsume",
            "request:$url, cost time: ${(endTime - startTime) / 1000000} ms."
        )
        return resp
    }
}