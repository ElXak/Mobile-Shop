package com.ebo.mobileshop.data.banner

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface BannerDao {

    // Define each of operations you want to support
    // Query operations
    @Query("SELECT * FROM banners")
    fun getAll(): List<Banner>

    // Query top-level categories
    @Query("SELECT id, name, sorting, text, picture, linkId, linkType FROM banners WHERE sectionId = :sectionId")
    fun getSection(sectionId: Int): List<SelectedBanner>

    // Insert operation, all operation except SELECT will have keyword suspend so that they
    // designed to work with coroutines
    @Insert
    suspend fun insert(banner: Banner)

    // Bulk insert
    @Insert
    suspend fun insertAll(banners: List<Banner>)

    @Query("DELETE from banners")
    suspend fun deleteAll()

}