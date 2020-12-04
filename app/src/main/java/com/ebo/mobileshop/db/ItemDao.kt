package com.ebo.mobileshop.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ebo.mobileshop.vo.Item
import com.ebo.mobileshop.vo.SelectedItem

// DAO (Data Access Object) Interface: Typically you have 1 DAO for each entity, but you can design
// however you like. The purpose of DAO is to define database operations,
// queries - insert, update and delete operations and so on

@Dao
interface ItemDao {

    // Define each of operations you want to support
    // Query operations
    @Query("SELECT * FROM items")
    fun getAll(): List<Item>

    // Query top items
    @Query(
        """
        SELECT id, code, sectionId, sectionCode, name, sorting, previewText, detailPicture, price,
        discountPrice, discountDiff, discountDiffPercent, rating, specialOffer, newProduct, saleLeader
        FROM items""")
    fun getTop(): List<SelectedItem>

    // Query by Section id
    @Query(
        """
        SELECT id, code, sectionId, sectionCode, name, sorting, previewText, detailPicture, price,
        discountPrice, discountDiff, discountDiffPercent, rating, specialOffer, newProduct, saleLeader
        FROM items WHERE sectionId = :sectionId"""
    )
    fun getBySectionId(sectionId: Int): List<SelectedItem>

    // Query by Section code
    @Query(
        """
        SELECT id, code, sectionId, sectionCode, name, sorting, previewText, detailPicture, price,
        discountPrice, discountDiff, discountDiffPercent, rating, specialOffer, newProduct, saleLeader
        FROM items WHERE sectionCode = :sectionCode"""
    )
    fun getBySectionCode(sectionCode: String): List<SelectedItem>

    // Query by id
    @Query("SELECT * FROM items WHERE id = :id AND sectionId = :sectionId")
    fun getById(id: Int, sectionId: Int): Item

    // Query by code
    @Query("SELECT * FROM items WHERE code = :code AND sectionCode = :sectionCode")
    fun getByCode(code: String, sectionCode: String): Item

    // Insert operation, all operation except SELECT will have keyword suspend so that they
    // designed to work with coroutines
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: Item)

    // Bulk insert
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<Item>)

    @Query("DELETE from items")
    suspend fun deleteAll()

}