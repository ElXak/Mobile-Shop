package com.ebo.mobileshop.ui.shared

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.ebo.mobileshop.repository.BannerRepository

class BannerViewModel(val app: Application): AndroidViewModel(app) {

    private val dataRepository = BannerRepository(app)
    val data = dataRepository.data

    fun refreshData() {
        dataRepository.refreshFromWeb()
    }

}