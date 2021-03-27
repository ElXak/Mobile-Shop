package com.ebo.mobileshop.ui.shared

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.ebo.mobileshop.repository.ImageRepository

class ImageViewModel(val app: Application, val params: Map<String, String>): AndroidViewModel(app) {

    private val dataRepository = ImageRepository(app, params)
    val data = dataRepository.data

    fun refreshData() {
        dataRepository.refreshFromWeb()
    }

}