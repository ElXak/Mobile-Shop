package com.ebo.mobileshop.repository

import android.app.Application
import android.widget.Toast
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ebo.mobileshop.WEB_SERVICE_URL
import com.ebo.mobileshop.api.LamodeService
import com.ebo.mobileshop.db.MobileShopDb
import com.ebo.mobileshop.utilities.Network
import com.ebo.mobileshop.vo.Image
import com.ebo.mobileshop.vo.SelectedImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class ImageRepository(val app: Application, private val itemId: Int) {

    private val _data = MutableLiveData<List<SelectedImage>>()
    val data: LiveData<List<SelectedImage>> = _data

    private val dao = MobileShopDb.getDatabase(app).imageDao()

    init {
        CoroutineScope(Dispatchers.IO).launch {
            val databaseData = dao.getByItemId(itemId)
            if (databaseData.isEmpty()) {
                webService()
            } else {
                _data.postValue(databaseData)
            }
        }
    }

    @WorkerThread
    suspend fun webService() {
        if (Network.isAvailable(app)) {
            withContext(Dispatchers.Main) {
                Toast.makeText(app, "Synchronized", Toast.LENGTH_SHORT).show()
            }

            val retrofit = Retrofit.Builder()
                .baseUrl(WEB_SERVICE_URL)
                // integrate moshi lib for parsing the json and no need to create an adapter
                .addConverterFactory(MoshiConverterFactory.create())
                .build()

            val service = retrofit.create(LamodeService::class.java)
            val serviceData = service.getImages().body() ?: emptyList()

            dao.insertAll(serviceData)

            val data = dao.getByItemId(itemId)
            _data.postValue(data)
        }
    }

    fun refreshFromWeb() {
        CoroutineScope(Dispatchers.IO).launch {
            dao.deleteAll()
            webService()
        }
    }

}