package com.capstone.myapplication.shared.api

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class JWTInterceptor(private val token: String?) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original: Request = chain.request()
        val builder: Request.Builder = original.newBuilder()
            .header("Authorization", "Bearer $token")
        val request: Request = builder.build()
        return chain.proceed(request)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is JWTInterceptor) return false
        return token == other.token
    }

    override fun hashCode(): Int {
        return token.hashCode()
    }
}