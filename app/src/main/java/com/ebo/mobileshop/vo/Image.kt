package com.ebo.mobileshop.vo

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ebo.mobileshop.WEB_SERVICE_URL

@Entity(tableName = "images")
data class Image(
    @PrimaryKey
    val id: Int,
    val itemId: Int,
    val itemCode: String? = null,
    val text: String? = null,
    val image: String
) {

    val imageUrl
        get() = "$WEB_SERVICE_URL/$image"
}