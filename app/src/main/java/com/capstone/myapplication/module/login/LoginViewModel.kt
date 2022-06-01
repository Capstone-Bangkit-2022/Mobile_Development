package com.capstone.myapplication.module.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.capstone.myapplication.base.BaseViewModel
import com.google.gson.GsonBuilder
import com.capstone.myapplication.shared.api.ServiceGenerator
import com.capstone.myapplication.shared.database.UserPreferences
import com.capstone.myapplication.shared.model.User
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(private val pref: UserPreferences) : BaseViewModel() {
    val user: LiveData<String?> = pref.getUser().asLiveData()

    fun useToken(stringUser: String) {
        val convertedUser: User? = GsonBuilder().create().fromJson(stringUser, User::class.java)

        Log.d("Tes GSON", stringUser)
        if(convertedUser != null)
            ServiceGenerator.setToken(convertedUser.token)
        else
            ServiceGenerator.setToken(null)
    }

    fun authenticate(email: String, password: String) {
        val service = ServiceGenerator.createService(LoginService::class.java)

        _isLoading.value = true
        service.login(email, password).enqueue( object : Callback<LoginResponse?> {
            override fun onResponse(
                call: Call<LoginResponse?>,
                response: Response<LoginResponse?>
            ) {
                _isLoading.value = false
                response.body()?.user?.let {
                    saveUser(it)
                }
            }

            override fun onFailure(call: Call<LoginResponse?>, t: Throwable) {
                _isLoading.value = false
            }
        })
    }

    private fun saveUser(user: User) {
        viewModelScope.launch {
            pref.saveUser(user)
        }
    }
}