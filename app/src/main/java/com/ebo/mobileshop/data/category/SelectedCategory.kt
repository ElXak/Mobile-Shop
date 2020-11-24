package com.ebo.mobileshop.data.category

import com.ebo.mobileshop.WEB_SERVICE_URL

// Data Class is very good for keeping data. it has many functions like toString, get, set

data class SelectedCategory(
    //  if variable name doesn't match json name use annotation. It comes from Moshi library
    //  @Json(name = "sectionName") val name: String,
    val id: Int,
    val name: String,
    val code: String? = null,
    val sorting: Int? = null,
    val description: String? = null,
    val count: Int? = 0,
    val picture: String? = null,
) {

    // read-only property
    val pictureUrl
        // explicit getter fun which calculates picture url
        get() = "$WEB_SERVICE_URL/$picture"

}