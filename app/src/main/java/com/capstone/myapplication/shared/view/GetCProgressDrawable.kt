package com.storyapp.dicoding.shared.view

import android.content.Context
import androidx.swiperefreshlayout.widget.CircularProgressDrawable

fun getCircularProgressDrawable(context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 5f
        centerRadius = 30f
        start()
    }
}