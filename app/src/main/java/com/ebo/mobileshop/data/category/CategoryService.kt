package com.ebo.mobileshop.data.category

import retrofit2.Response
import retrofit2.http.GET

// Web service interface
interface CategoryService {

    // Annotation member of retrofit2.http
    @GET("/json/categories.php")
    // suspend means this fun is designed to be called within a coroutine
    suspend fun getCategoriesData(): Response<List<Category>>

}