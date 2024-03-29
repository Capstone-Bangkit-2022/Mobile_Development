package com.capstone.myapplication.module.login

import com.capstone.myapplication.module.login.LoginResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST


interface LoginService {
    @FormUrlEncoded
    @POST("login")
    fun login(@Field("email") email: String, @Field("password") password: String)
        : Call<LoginResponse>
}