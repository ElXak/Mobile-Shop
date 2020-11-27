package com.ebo.mobileshop.data.product

import android.app.Application
import android.widget.Toast
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ebo.mobileshop.WEB_SERVICE_URL
import com.ebo.mobileshop.data.SqlDatabase
import com.ebo.mobileshop.utilities.Network
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

// Encapsulates the logic of getting data. Decides whether getting data from web service, cache or file
// Once you defined Database with room you can use it from anywhere of your app,
// but we architect to access database from 1 place, repository
class ProductRepository(val app: Application) {

    // for publisher-subscriber pattern: in this pattern Repository acquires the data, but instead of returning data it publishes it
    // by using a component called LiveData. Any other component in the application can subscribe to handle changes to the data using
    // a pattern called a observer
    private val _data = MutableLiveData<List<SelectedProduct>>()
    val data: LiveData<List<SelectedProduct>> = _data

    // for passing data to layout
    private val _selectedData = MutableLiveData<SelectedProduct>()
    val selectedData: LiveData<SelectedProduct> = _selectedData

    // instance of DAO
    private val productDao = SqlDatabase.getDatabase(app).productDao()

    // runs on initialization
    init {
        // do all work in background thread
        CoroutineScope(Dispatchers.IO).launch {
            // try to get all data from database
            val databaseData = productDao.getTop()
            if (databaseData.isEmpty()) {
                // if data is empty read get it from WebService
                callWebService()
            } else {
                // else post it to live data
                _data.postValue(databaseData)
            }
        }
    }

    // @WorkerThread annotation is an indicator that this function will be called in a background thread
    @WorkerThread
    suspend fun callWebService() {
        if (Network.isAvailable(app)) {
            // Toast can not be called from background thread
            // Toast architecture is designed to be called from foreground thread
            // But we can switch thread on the fly
            withContext(Dispatchers.Main) {
                Toast.makeText(app, "Synchronized", Toast.LENGTH_LONG).show()
            }

            val retrofit = Retrofit.Builder()
                .baseUrl(WEB_SERVICE_URL)
                // integrate moshi lib for parsing the json and no need to create an adapter
                .addConverterFactory(MoshiConverterFactory.create())
                .build()

            // create service
            val service = retrofit.create(ProductService::class.java)

            // get service data
            val serviceData = service.getProductsData().body() ?: emptyList()

            // update data in sqlLite database
            productDao.deleteAll()
            productDao.insertAll(serviceData)

            val data = productDao.getTop()
            // we can't use .value or setValue(). Because they are can only be called from UI thread
            // instead we call postValue(). which is designed to be called from a background thread
            _data.postValue(data)
        }
    }

    fun refreshFromWeb() {
        // Dispatchers.IO means do it in the background thread
        // Dispatchers.Main means do it in the foreground thread
        CoroutineScope(Dispatchers.IO).launch {
            callWebService()
        }
    }

    fun selectData(data: SelectedProduct) {
        CoroutineScope(Dispatchers.IO).launch {
            _selectedData.postValue(data)
        }
    }

}