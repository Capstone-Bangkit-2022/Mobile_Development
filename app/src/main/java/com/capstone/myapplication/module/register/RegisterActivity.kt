package com.capstone.myapplication.module.register

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.capstone.myapplication.R
import com.capstone.myapplication.base.BaseActivity
import com.capstone.myapplication.databinding.RegisterActivityBinding
import com.storyapp.dicoding.shared.view.showToast

class RegisterActivity : BaseActivity<RegisterActivityBinding>(R.layout.register_activity) {
    private lateinit var viewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.apply {
            name = ""
            email = ""
            password = ""
            passwordConfirmation = ""
        }
        initializeViewModel()
        initializeOnClick()
    }

    private fun initializeOnClick() {
        binding.registerBtn.setOnClickListener {
//            binding.apply {
//                viewModel.register(name!!, email!!, password!!, passwordConfirmation!!)
//            }
        }
    }

    private fun initializeViewModel() {
        viewModel = ViewModelProvider(this)[RegisterViewModel::class.java].apply {
            apiResponse.observe(this@RegisterActivity) {
                if(it.error)
                    showToast(it.message)
                else
                    finish()
            }
            isLoading.observe(this@RegisterActivity) {
                if (it)
                    startLoading()
                else
                    stopLoading()
            }
        }
    }
}