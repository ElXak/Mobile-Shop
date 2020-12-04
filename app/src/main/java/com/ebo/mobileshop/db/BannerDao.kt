package com.ebo.mobileshop.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ebo.mobileshop.vo.Banner
import com.ebo.mobileshop.vo.SelectedBanner

@Dao
interface BannerDao {

    // Define each of operations you want to support
    // Query operations
    @Query("SELECT * FROM banners")
    fun getAll(): List<Banner>

    // Query top-level sections
    @Query(
        """
        SELECT id, name, sorting, text, picture, linkId, linkType
        FROM banners WHERE sectionId = :sectionId"""
    )
    fun getBySectionId(sectionId: Int): List<SelectedBanner>

    // Insert operation, all operation except SELECT will have keyword suspend so that they
    // designed to work with coroutines
//    @Insert(onConflict = OnConflictStrategy.IGNORE)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(banner: Banner)

    // Bulk insert
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(banners: List<Banner>)

    @Query("DELETE from banners")
    suspend fun deleteAll()

}