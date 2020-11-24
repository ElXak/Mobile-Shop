package com.ebo.mobileshop.data.category

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

// DAO (Data Access Object) Interface: Typically you have 1 DAO for each entity, but you can design
// however you like. The purpose of DAO is to define database operations,
// queries - insert, update and delete operations and so on

@Dao
interface CategoryDao {

    // Define each of operations you want to support
    // Query operations
    @Query("SELECT * from categories")
    fun getAll(): List<Category>

    // Query top-level categories
    @Query("SELECT id, name, code, sorting, description, count, picture from categories WHERE depthLevel=1")
    fun getTopLevelCategories(): List<TopLevelCategory>

    // Insert operation, all operation except SELECT will have keyword suspend so that they
    // designed to work with coroutines
    @Insert
    suspend fun insertCategory(category: Category)

    // Bulk insert
    @Insert
    suspend fun insertCategories(categories: List<Category>)

    @Query("DELETE from categories")
    suspend fun deleteAll()
}