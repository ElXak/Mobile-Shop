package com.ebo.mobileshop.ui.shared

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ImageViewModelFactory(private val app: Application, private val params: Map<String, String>) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(ImageViewModel::class.java))
            ImageViewModel(app, params) as T
        else
            throw IllegalArgumentException("ViewModel Not Found")
    }

}