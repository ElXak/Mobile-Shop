package com.ebo.mobileshop.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ebo.mobileshop.data.category.CategoryRepository
import com.ebo.mobileshop.data.category.SelectedCategory

// Asks repository for data without knowing from where it comes from and passes to User Interface
// put val before variable name for lasting variable longer
class HomeViewModel(val app: Application) : AndroidViewModel(app) {

    private val dataRepository = CategoryRepository(app)
    val data = dataRepository.data

    // for passing data to layout
    val _selectedCategory = MutableLiveData<SelectedCategory>()
    val selectedCategory: LiveData<SelectedCategory> = _selectedCategory

    fun refreshData() {
        dataRepository.refreshFromWeb()
    }
}