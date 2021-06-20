package com.example.neosoftassignmentproject.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.neosoftassignmentproject.model.AddAddress

@Dao
interface AddressDao {
    @Insert(onConflict =OnConflictStrategy.IGNORE)
    suspend fun insertAddress(address: List<AddAddress>)

    @Query("SELECT * FROM AddAddress")
     fun getAddress():LiveData<List<AddAddress>>

     @Delete()
     suspend fun deleteAddress(address:AddAddress)
}