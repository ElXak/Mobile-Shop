package com.ebo.mobileshop.ui.item

import android.app.Application
import androidx.lifecycle.*
import com.ebo.mobileshop.repository.ItemDetailsRepository

class PageViewModel(val app: Application, val params: Map<String, Int>) : AndroidViewModel(app) {

    private val dataRepository = ItemDetailsRepository(app, params)
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

    private val _index = MutableLiveData<Int>()
    val text: LiveData<String> = Transformations.map(_index) {
        "Hello world from section: $it"
    }

    fun setIndex(index: Int) {
        _index.value = index
    }

}