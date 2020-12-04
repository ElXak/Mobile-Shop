package com.ebo.mobileshop.ui.shared

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.ebo.mobileshop.repository.ItemRepository

// Asks repository for data without knowing from where it comes from and passes to User Interface
// put val before variable name for lasting variable longer
class ItemViewModel(val app: Application) : AndroidViewModel(app) {

    private val dataRepository = ItemRepository(app)
    val data = dataRepository.data
//    val selectedData = dataRepository.selectedData


    fun refreshData() {
        dataRepository.refreshFromWeb()
    }

/*
    fun selectData(data: SelectedItem) {
        dataRepository.selectData(data)
    }
*/
}