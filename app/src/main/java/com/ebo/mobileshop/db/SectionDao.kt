package com.ebo.mobileshop.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ebo.mobileshop.vo.Section
import com.ebo.mobileshop.vo.SelectedSection

// DAO (Data Access Object) Interface: Typically you have 1 DAO for each entity, but you can design
// however you like. The purpose of DAO is to define database operations,
// queries - insert, update and delete operations and so on

@Dao
interface SectionDao {

    // Define each of operations you want to support
    // Query operations
    @Query("SELECT * FROM sections")
    fun getAll(): List<Section>

    // Query top-level sections
    @Query(
        """
        SELECT id, code, name, sorting, description, count, picture
        FROM sections WHERE depthLevel = 1"""
    )
    fun getRoot(): List<SelectedSection>

    // Query by parent id
    @Query("""
        SELECT id, code, name, sorting, description, count, picture
        FROM sections WHERE parentId = :parentId"""
    )
    fun getBySectionId(parentId: Int): List<SelectedSection>

    // Insert operation, all operation except SELECT will have keyword suspend so that they
    // designed to work with coroutines
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(section: Section)

    // Bulk insert
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(sections: List<Section>)

    @Query("DELETE from sections")
    suspend fun deleteAll()

}