package com.example.notecalendar.freamwork.datasource.network.implementation

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class HeadersInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        builder.addHeader("apikey",BaseApi.KEY)
        builder.addHeader("Authorization", BaseApi.KEY)
        builder.addHeader("Content-Type","application/json")
        return chain.proceed(builder.build())
    }
}
