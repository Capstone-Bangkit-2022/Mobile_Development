package com.capstone.myapplication.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.capstone.myapplication.shared.view.LoadingDialog

abstract class BaseActivity<T: ViewDataBinding>(private val layoutId: Int)
    : AppCompatActivity()
{
    protected lateinit var binding: T
    private lateinit var loadingDialog: LoadingDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutId)
        supportActionBar?.hide()
    }

    fun startLoading() {
        loadingDialog = LoadingDialog()

        loadingDialog.show(supportFragmentManager, "Loading..")
    }

    fun stopLoading() {
        loadingDialog.dismiss()
    }
}