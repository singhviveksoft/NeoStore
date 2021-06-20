package com.example.neosoftassignmentproject.repository

import androidx.lifecycle.LiveData
import com.example.neosoftassignmentproject.constants.interfaces.Api
import com.example.neosoftassignmentproject.db.DataBase
import com.example.neosoftassignmentproject.model.AddAddress

class AddressRepository(val api: Api, val db:DataBase) {
    suspend fun insertAddress(address: List<AddAddress>)=db.addresDao.insertAddress(address)

    val getAddress:LiveData<List<AddAddress>> =db.addresDao.getAddress()

    suspend fun placeOrder(access_token:String,address:String)=api.placeOrder(access_token,address)

    suspend fun deleteItem(address:AddAddress)=db.addresDao.deleteAddress(address)


}