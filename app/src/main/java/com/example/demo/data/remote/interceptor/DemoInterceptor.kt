package com.example.demo.data.remote.interceptor

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class DemoInterceptor(private val headers: Map<String, String>) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val original: Request = chain.request()

        // Build a new request with the headers
        val requestBuilder: Request.Builder = original.newBuilder()
        for ((key, value) in headers) {
            requestBuilder.addHeader(key, value)
        }

        val request: Request = requestBuilder.build()
        return chain.proceed(request)
    }
}
