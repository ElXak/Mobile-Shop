package com.ebo.mobileshop.data.product

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

// DAO (Data Access Object) Interface: Typically you have 1 DAO for each entity, but you can design
// however you like. The purpose of DAO is to define database operations,
// queries - insert, update and delete operations and so on

@Dao
interface ProductDao {

    // Define each of operations you want to support
    // Query operations
    @Query("SELECT * FROM products")
    fun getAll(): List<Product>

    // Query top-level categories
    @Query("SELECT id, name, sorting, previewText, previewPicture, price, discountPrice, rating, specialOffer, newProduct, saleLeader FROM products")
    fun getTop(): List<SelectedProduct>

    // Query top-level categories
    @Query("SELECT id, name, sorting, previewText, previewPicture, price, discountPrice, rating, specialOffer, newProduct, saleLeader FROM products WHERE sectionId = :sectionId")
    fun getSection(sectionId: Int): List<SelectedProduct>

    // Insert operation, all operation except SELECT will have keyword suspend so that they
    // designed to work with coroutines
    @Insert
    suspend fun insert(product: Product)

    // Bulk insert
    @Insert
    suspend fun insertAll(products: List<Product>)

    @Query("DELETE from products")
    suspend fun deleteAll()

}