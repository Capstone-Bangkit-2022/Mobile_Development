package com.capstone.myapplication.module.login

import com.google.gson.annotations.SerializedName
import com.capstone.myapplication.shared.api.APIResponse
import com.capstone.myapplication.shared.model.User

class LoginResponse(
    error: Boolean,
    message: String,
    @SerializedName("loginResult")
    val user: User?
) : APIResponse(error, message) {
}