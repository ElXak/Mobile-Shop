package com.ebo.mobileshop.ui.shared

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.ebo.mobileshop.repository.SectionRepository
import com.ebo.mobileshop.vo.SelectedSection

// Asks repository for data without knowing from where it comes from and passes to User Interface
// put val before variable name for lasting variable longer
class SectionViewModel(val app: Application) : AndroidViewModel(app) {

    private val dataRepository = SectionRepository(app)
    val data = dataRepository.data
    val selectedData = dataRepository.selectedData


    fun refreshData() {
        dataRepository.refreshFromWeb()
    }

    fun selectData(data: SelectedSection) {
        dataRepository.selectData(data)
    }
}