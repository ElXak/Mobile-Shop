package com.ebo.mobileshop.ui.item

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

// We can pass arguments in viewmodel through viewmodel factory.
// Without factory, we can not pass arguments in viewmodel.
class ViewModelFactory(private val app: Application, private val params: Map<String, Int>) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(PageViewModel::class.java))
            PageViewModel(app, params) as T
        else
            throw IllegalArgumentException("ViewModel Not Found")
    }

}