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

    suspend fun getProduct(product_category_id:String)=api.getProduct(product_category_id)

    suspend fun getProductDetail(product_id:String)=api.getProductDetail(product_id)

    suspend fun addRating(product_id:String,rating:Number)=api.addRating(product_id,rating)

    suspend fun addToCart(access_token:String,product_id:Number,quantity:Number)=api.addToCart(access_token,product_id,quantity)

    suspend fun myCart(access_token:String)=api.myCart(access_token)

    suspend fun deleteCartItem(access_token:String,product_id:Number)=api.deleteCartItem(access_token,product_id)

    suspend fun changeQuantity(access_token:String,product_id:Number,quantity:Number)=api.changeQuantity(access_token,product_id,quantity)

    suspend fun placeOrder(access_token:String,address:String)=api.placeOrder(access_token,address)


    suspend fun myOder(access_token:String)=api.myOder(access_token)


    suspend fun orderDetail(access_token:String,order_id:Number)=api.orderDetail(access_token,order_id)


    suspend fun forgotPwd(email:String)=api.forgotPwd(email)

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