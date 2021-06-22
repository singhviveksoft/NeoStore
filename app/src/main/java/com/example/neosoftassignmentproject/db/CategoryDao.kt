package com.example.neosoftassignmentproject.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.neosoftassignmentproject.model.AddAddress
import com.example.neosoftassignmentproject.model.ProductCategory
@Dao
interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCatrgy(category: List<ProductCategory>)

    @Query("SELECT * FROM ProductCategory")
    fun getCatrgy(): LiveData<List<ProductCategory>>
}