package com.example.neosoftassignmentproject.repository

import androidx.lifecycle.LiveData
import com.example.neosoftassignmentproject.constants.interfaces.Api
import com.example.neosoftassignmentproject.db.DataBase
import com.example.neosoftassignmentproject.model.AddAddress
import com.example.neosoftassignmentproject.model.ProductCategory

class CategoryRepository(val api: Api, val db:DataBase) {
    suspend fun insertCatrgy(category: List<ProductCategory>)=db.productCategoryDao.insertCatrgy(category)

    val getCatrgy:LiveData<List<ProductCategory>> =db.productCategoryDao.getCatrgy()

    suspend fun getUser(access_token:String)=api.getUser(access_token)


    suspend fun updateProfile(
        access_token:String,
        first_name:String,
        last_name:String,
        email:String,
        dob:String,
        phone_no:String,
        profile_pic:String)  =api.updateProfile(access_token,first_name,last_name,email,dob,phone_no,profile_pic)

    suspend fun changePwd(access_token:String,
                          old_password:String,
                          password:String,
                          confirm_password:String)  =api.changePwd(access_token,old_password,password,confirm_password)


}