package com.ebo.mobileshop.vo

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ebo.mobileshop.WEB_SERVICE_URL

@Entity(tableName = "banners")
data class Banner(
    @PrimaryKey
    val id: Int,
    val name: String,
    val sectionId: String? = null,
    val sorting: Int? = null,
    val text: String? = null,
    val picture: String? = null,
    val linkId: Int? = null,
    val linkType: String
) {

    // read-only property
    val pictureUrl
        // explicit getter fun which calculates picture url
        get() = "$WEB_SERVICE_URL/$picture"

}