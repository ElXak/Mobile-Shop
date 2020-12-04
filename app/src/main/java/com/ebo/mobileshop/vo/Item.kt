package com.ebo.mobileshop.vo

import android.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ebo.mobileshop.WEB_SERVICE_URL

// Data Class is very good for keeping data. it has many functions like toString, get, set
// in addition to representing data entities that can be stored in RAM this class can also be used
// to define sql lite database

// Annotation from androidx.room. If you don't add tableName attribute,
// it will be calculated automatically by room
// you need to create Entity for each table
@Entity(tableName = "items")
data class Item(
    //  Primary key for database table by annotation from room
    //  if variable name doesn't match json name use annotation. It comes from Moshi library
    //  @Json(name = "sectionName") val name: String,
    @PrimaryKey
    val id: Int,
    val code: String? = null,
    val sectionId: Int? = null,
    val sectionCode: String? = null,
    val name: String,
    val sorting: Int? = null,
    val previewText: String? = null,
    val detailText: String? = null,
//    val previewPicture: String? = null,
    val detailPicture: String? = null,
    val price: Double? = null,
    val discountPrice: Double? = null,
    val discountDiff: Double? = null,
    val discountDiffPercent: Double? = null,
    val rating: Double? = null,
    val vendor: Int? = null,
//    val vendorName: String? = null,
//    val samples: List<Int>? = null,
    val material: Int? = null,
//    val materialName: String? = null,
//    val materialDesc: String? = null,
    val country: Int? = null,
//    val countryName: String? = null,
    val specialOffer: String? = null,
    val newProduct: String? = null,
    val saleLeader: String? = null,
    val style: String? = null,
//    val color: Color? = null,
//    val morePictures: List<String>? = null
) {

/*
    // read-only property
    val pictureUrl
        // explicit getter fun which calculates picture url
        get() = "$WEB_SERVICE_URL/$previewPicture"
*/
    // read-only property
    val detailPictureUrl
        // explicit getter fun
        get() = "$WEB_SERVICE_URL/$detailPicture"

}