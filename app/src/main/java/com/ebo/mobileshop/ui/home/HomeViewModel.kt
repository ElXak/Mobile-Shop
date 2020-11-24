package com.ebo.mobileshop.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ebo.mobileshop.data.category.CategoryRepository
import com.ebo.mobileshop.data.category.TopLevelCategory

// Asks repository for data without knowing from where it comes from and passes to User Interface
// put val before variable name for lasting variable longer
class HomeViewModel(val app: Application) : AndroidViewModel(app) {

    private val dataRepository = CategoryRepository(app)
    val topLevelCategoryData = dataRepository.topLevelCategoryData

    // for passing data to layout
    val _selectedCategory = MutableLiveData<TopLevelCategory>()
    val selectedCategory: LiveData<TopLevelCategory> = _selectedCategory

/*
    private val _text = MutableLiveData<String>().apply {
        // app.getString(R.string.large_text) - gets string resource
        value = topLevelCategoryData.toString()
    }
    val text: LiveData<String> = _text
*/

    fun refreshData() {
        dataRepository.refreshDataFromWeb()
    }
}