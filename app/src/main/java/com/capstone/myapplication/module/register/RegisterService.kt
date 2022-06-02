package com.capstone.myapplication.module.register

import com.capstone.myapplication.shared.api.APIResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface RegisterService {
    @POST("register")
    @FormUrlEncoded
    fun register(
        @Field("email") email: String,
        @Field("name") name: String,
        @Field("password") password: String,
        @Field("password_confirmation") passwordConfirmation: String,
    ): Call<APIResponse>
}