package com.ebo.mobileshop.ui.shared

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.ebo.mobileshop.data.category.CategoryRepository
import com.ebo.mobileshop.data.category.SelectedCategory

// Asks repository for data without knowing from where it comes from and passes to User Interface
// put val before variable name for lasting variable longer
class CategoryViewModel(val app: Application) : AndroidViewModel(app) {

    private val dataRepository = CategoryRepository(app)
    val data = dataRepository.data
    val selectedData = dataRepository.selectedData


    fun refreshData() {
        dataRepository.refreshFromWeb()
    }

    fun selectData(data: SelectedCategory) {
        dataRepository.selectData(data)
    }
}