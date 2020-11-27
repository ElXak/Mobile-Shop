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
    @Query("SELECT * FROM categories")
    fun getAll(): List<Category>

    // Query top-level categories
    @Query("SELECT id, name, sorting, description, count, picture FROM categories WHERE depthLevel = 1")
    fun getRoot(): List<SelectedCategory>

    // Query top-level categories
    @Query("SELECT id, name, sorting, description, count, picture FROM categories WHERE parentId = :parentId")
    fun getSection(parentId: Int): List<SelectedCategory>

    // Insert operation, all operation except SELECT will have keyword suspend so that they
    // designed to work with coroutines
    @Insert
    suspend fun insert(category: Category)

    // Bulk insert
    @Insert
    suspend fun insertAll(categories: List<Category>)

    @Query("DELETE from categories")
    suspend fun deleteAll()

}