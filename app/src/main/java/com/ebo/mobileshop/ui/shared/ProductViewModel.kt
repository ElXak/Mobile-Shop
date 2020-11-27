package com.ebo.mobileshop.ui.shared

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.ebo.mobileshop.data.product.ProductRepository
import com.ebo.mobileshop.data.product.SelectedProduct

// Asks repository for data without knowing from where it comes from and passes to User Interface
// put val before variable name for lasting variable longer
class ProductViewModel(val app: Application) : AndroidViewModel(app) {

    private val dataRepository = ProductRepository(app)
    val data = dataRepository.data
    val selectedData = dataRepository.selectedData


    fun refreshData() {
        dataRepository.refreshFromWeb()
    }

    fun selectData(data: SelectedProduct) {
        dataRepository.selectData(data)
    }
}