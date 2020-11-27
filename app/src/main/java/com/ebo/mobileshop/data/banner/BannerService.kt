package com.ebo.mobileshop.data.banner

import retrofit2.Response
import retrofit2.http.GET

interface BannerService {

    @GET("/json/banners.php")
    suspend fun getBannersData(): Response<List<Banner>>

}