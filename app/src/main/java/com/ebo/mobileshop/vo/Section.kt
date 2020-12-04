package com.ebo.mobileshop.vo

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ebo.mobileshop.WEB_SERVICE_URL

// Data Class is very good for keeping data. it has many functions like toString, get, set
// in addition to representing data entities that can be stored in RAM this class can also be used
// to define sql lite database

// Annotation from androidx.room. If you don't add tableName attribute,
// it will be calculated automatically by room
// you need to create Entity for each table
@Entity(tableName = "sections")
data class Section(
    //  Primary key for database table by annotation from room
    //  if variable name doesn't match json name use annotation. It comes from Moshi library
    //  @Json(name = "sectionName") val name: String,
    @PrimaryKey
    val id: Int,
    val code: String,
    val name: String,
    val parentId: String? = null,
    val sorting: Int? = null,
    val description: String? = null,
    val count: Int? = 0,
    val depthLevel: Int = 0,
    val picture: String? = null,
    val detailPicture: String? = null
) {

    // read-only property
    val pictureUrl
        // explicit getter fun which calculates picture url
        get() = "$WEB_SERVICE_URL/$picture"
    // read-only property
    val detailPictureUrl
        // explicit getter fun
        get() = "$WEB_SERVICE_URL/$detailPicture"

}