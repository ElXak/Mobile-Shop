package com.ebo.mobileshop.data.product

import retrofit2.Response
import retrofit2.http.GET

// Web service interface
interface ProductService {

    // Annotation member of retrofit2.http
    @GET("/json/index.php")
    // suspend means this fun is designed to be called within a coroutine
    suspend fun getProductsData(): Response<List<Product>>

}