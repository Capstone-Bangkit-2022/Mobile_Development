package com.capstone.myapplication.module.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.capstone.myapplication.base.BaseViewModel
import com.capstone.myapplication.shared.api.APIResponse
import com.capstone.myapplication.shared.api.ServiceGenerator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel : BaseViewModel() {
    private val _apiResponse = MutableLiveData<APIResponse>()
    val apiResponse: LiveData<APIResponse> = _apiResponse

    fun register(name: String, email: String, password: String, passwordConfirmation: String) {
        _isLoading.value = true

        ServiceGenerator.createService(RegisterService::class.java)
            .register(email, name, password, passwordConfirmation)
            .enqueue(object : Callback<APIResponse> {
                override fun onResponse(call: Call<APIResponse>, response: Response<APIResponse>) {
                    _isLoading.value = false
                    if(response.body() == null){
                        val converter = ServiceGenerator.retrofit.responseBodyConverter<APIResponse>(APIResponse::class.java, arrayOf())
                        _apiResponse.value = converter.convert(response.errorBody())
                    }else
                        _apiResponse.value = response.body()
                }

                override fun onFailure(call: Call<APIResponse>, t: Throwable) {
                    _isLoading.value = false
                }
            })
    }
}