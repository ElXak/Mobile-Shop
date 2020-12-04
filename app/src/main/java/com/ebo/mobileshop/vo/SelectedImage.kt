package com.ebo.mobileshop.vo

import com.ebo.mobileshop.WEB_SERVICE_URL

data class SelectedImage (
    val text: String? = null,
    val image: String
    ) {

    val imageUrl
        get() = "$WEB_SERVICE_URL/$image"
}