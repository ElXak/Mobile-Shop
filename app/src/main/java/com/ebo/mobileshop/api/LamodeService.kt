package com.ebo.mobileshop.api

import com.ebo.mobileshop.vo.Banner
import com.ebo.mobileshop.vo.Image
import com.ebo.mobileshop.vo.Item
import com.ebo.mobileshop.vo.Section
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

// Web service interface
interface LamodeService {

    // Annotation member of retrofit2.http
    @GET("/json/index.php")
    // suspend means this fun is designed to be called within a coroutine
    suspend fun getTopItems(): Response<List<Item>>

    @GET("/json/detail.php")
    // passing arguments to the url
    suspend fun getItemDetails(@Query("ID") id: Int, @Query("SECTION_ID") sectionId: Int): Response<Item>

    @GET("/json/images.php")
    suspend fun getImages(@Query("elmid") itemCode: String, @Query("secid") sectionCode: String): Response<List<Image>>

    // Annotation member of retrofit2.http
    @GET("/json/categories.php")
    // suspend means this fun is designed to be called within a coroutine
    suspend fun getSections(): Response<List<Section>>

    @GET("/json/banners.php")
    suspend fun getBanners(): Response<List<Banner>>

}