package com.ebo.mobileshop.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ebo.mobileshop.vo.Image
import com.ebo.mobileshop.vo.SelectedImage

@Dao
interface ImageDao {

    // Define each of operations you want to support
    // Query operations
    @Query("SELECT * FROM images")
    fun getAll(): List<Image>

    // Query by itemId
    @Query("SELECT text, image FROM images WHERE itemId = :itemId")
    fun getByItemId(itemId: Int): List<SelectedImage>

    // Query by itemtCode
    @Query("SELECT text, image FROM images WHERE itemCode = :itemCode")
    fun getByItemCode(itemCode: String): List<SelectedImage>

    // Insert operation, all operation except SELECT will have keyword suspend so that they
    // designed to work with coroutines
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(image: Image)

    // Bulk insert
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(images: List<Image>)

    @Query("DELETE from images")
    suspend fun deleteAll()

}