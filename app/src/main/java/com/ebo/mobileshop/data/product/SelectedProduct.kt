package com.ebo.mobileshop.data.product

import com.ebo.mobileshop.WEB_SERVICE_URL

// Data Class is very good for keeping data. it has many functions like toString, get, set

data class SelectedProduct(
    //  if variable name doesn't match json name use annotation. It comes from Moshi library
    //  @Json(name = "sectionName") val name: String,
    val id: Int,
    val name: String,
    val sorting: Int? = null,
    val previewText: String? = null,
    val previewPicture: String? = null,
    val price: Double? = null,
    val discountPrice: Double? = null,
    val rating: Double? = null,
    val specialOffer: String? = null,
    val newProduct: String? = null,
    val saleLeader: String? = null,
) {

    // read-only property
    val pictureUrl
        // explicit getter fun which calculates picture url
        get() = "$WEB_SERVICE_URL/$previewPicture"

}