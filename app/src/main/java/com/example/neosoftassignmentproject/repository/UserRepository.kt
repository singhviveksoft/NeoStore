package com.example.neosoftassignmentproject.repository

import com.example.neosoftassignmentproject.constants.interfaces.Api
import com.example.neosoftassignmentproject.model.UserRegisterData

class UserRepository(val api:Api) {
    suspend fun userRegister(first_name:String,last_name:String,
                             email:String,password:String,
                             confirm_password:String,gender:String,
                             phone_no:Long):UserRegisterData{
      return  api.userRegister(first_name,last_name,email,password,confirm_password,gender,phone_no)
    }


suspend fun userLogin(email: String,pwd:String)=api.userLogin(email,pwd)

    suspend fun getUser(access_token:String)=api.getUser(access_token)
}