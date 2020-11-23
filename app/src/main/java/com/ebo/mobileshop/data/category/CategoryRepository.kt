package com.ebo.mobileshop.data.category

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.widget.Toast
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ebo.mobileshop.WEB_SERVICE_URL
import com.ebo.mobileshop.data.MobileShopDatabase
import com.squareup.moshi.Types
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

// Encapsulates the logic of getting data. Decides whether getting data from web service, cache or file
// Once you defined Database with room you can use it from anywhere of your app,
// but we architect to access database from 1 place, repository
class CategoryRepository(val app: Application) {

    // for publisher-subscriber pattern: in this pattern Repository acquires the data, but instead of returning data it publishes it
    // by using a component called LiveData. Any other component in the application can subscribe to handle changes to the data using
    // a pattern called a observer
    private val _categoryData = MutableLiveData<List<Category>>()
    val categoryData: LiveData<List<Category>> = _categoryData

    // instance of DAO
    private val categoryDao = MobileShopDatabase.getDatabase(app).categoryDao()

    // runs on initialization
    init {
        // do all work in background thread
        CoroutineScope(Dispatchers.IO).launch {
            // try to get all data from database
            val data = categoryDao.getAll()
            if (data.isEmpty()) {
                // if data is empty read get it from WebService
                callWebService()
            } else {
                // else post it to live data
                _categoryData.postValue(data)

                // Toast can not be called from background thread
                // Toast architecture is designed to be called from foreground thread
                // But we can switch thread on the fly
                withContext(Dispatchers.Main) {
                    Toast.makeText(app, "Using local data", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    // @WorkerThread annotation is an indicator that this function will be called in a background thread
    @WorkerThread
    suspend fun callWebService() {
        if (networkAvailable()) {
            // Toast can not be called from background thread
            // Toast architecture is designed to be called from foreground thread
            // But we can switch thread on the fly
            withContext(Dispatchers.Main) {
                Toast.makeText(app, "Using remotedata", Toast.LENGTH_LONG).show()
            }

            val retrofit = Retrofit.Builder()
                .baseUrl(WEB_SERVICE_URL)
                // integrate moshi lib for parsing the json and no need to create an adapter
                .addConverterFactory(MoshiConverterFactory.create())
                .build()

            // create service
            val service = retrofit.create(CategoryService::class.java)

            // get service data
            val serviceData = service.getCategoriesData().body() ?: emptyList()
            // we can't use .value or setValue(). Because they are can only be called from UI thread
            // instead we call postValue(). which is designed to be called from a background thread
            _categoryData.postValue(serviceData)

            // update data in sqlLite database
            categoryDao.deleteAll()
            categoryDao.insertCategories(serviceData)
        }
    }

    // used for suppressing deprecated functions can be found in intention actions
    // here we are suppressing everything inside networkAvailable()
    @Suppress("DEPRECATION")
    private fun networkAvailable(): Boolean {
        val connectivityManager = app.getSystemService(Context.CONNECTIVITY_SERVICE)
            // implicit type of object
            as ConnectivityManager

        val networkInfo = connectivityManager.activeNetworkInfo

        return networkInfo?.isConnectedOrConnecting ?: false
    }

    fun refreshDataFromWeb() {
        // Dispatchers.IO means do it in the background thread
        // Dispatchers.Main means do it in the foreground thread
        CoroutineScope(Dispatchers.IO).launch {
            callWebService()
        }
    }

}