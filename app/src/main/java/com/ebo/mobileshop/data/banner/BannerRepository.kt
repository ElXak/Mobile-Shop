package com.ebo.mobileshop.data.banner

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

class BannerRepository(val app: Application) {

    private val _data = MutableLiveData<List<SelectedBanner>>()
    val data: LiveData<List<SelectedBanner>> = _data

    private val bannerDao = SqlDatabase.getDatabase(app).bannerDao()

    init {
        CoroutineScope(Dispatchers.IO).launch {
            val databaseData = bannerDao.getSection(0)
            if (databaseData.isEmpty()) {
                callWebService()
            } else {
                _data.postValue(databaseData)
            }
        }
    }

    @WorkerThread
    suspend fun callWebService() {
        if (Network.isAvailable(app)) {
            withContext(Dispatchers.Main) {
                Toast.makeText(app, "Synchronized", Toast.LENGTH_SHORT).show()
            }

            val retrofit = Retrofit.Builder()
                .baseUrl(WEB_SERVICE_URL)
                // integrate moshi lib for parsing the json and no need to create an adapter
                .addConverterFactory(MoshiConverterFactory.create())
                .build()

            val service = retrofit.create(BannerService::class.java)
            val seviceData = service.getBannersData().body() ?: emptyList()

            bannerDao.deleteAll()
            bannerDao.insertAll(seviceData)

            val data = bannerDao.getSection(0)
            _data.postValue(data)
        }
    }

    fun refreshFromWeb() {
        CoroutineScope(Dispatchers.IO).launch {
            callWebService()
        }
    }

}