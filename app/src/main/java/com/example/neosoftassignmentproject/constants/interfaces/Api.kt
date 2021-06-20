package com.example.neosoftassignmentproject.constants.interfaces


import android.icu.util.TimeUnit
import com.example.neosoftassignmentproject.model.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

const  val BASE_URL="http://staging.php-dev.in:8844/trainingapp/api/"
const val TAG="TAG"
interface Api {
    @FormUrlEncoded

    @POST("users/register")
    suspend fun userRegister(
            @Field("first_name")first_name:String,
            @Field("last_name")last_name:String,
            @Field("email")email:String,
            @Field("password")password:String,
            @Field("confirm_password")confirm_password:String,
            @Field("gender")gender:String,
            @Field("phone_no")phone_no: Long
    ): UserRegisterData







    @FormUrlEncoded
    @POST("users/login")
    suspend fun userLogin(
        @Field("email")email:String,
        @Field("password")password:String
    ):UserLoginData









    @GET("users/getUserData")
    suspend fun getUser(
        @Header("access_token")access_token:String
    ): GetProductCategiryData










  @GET("products/getList")
  suspend fun getProduct(
      @Query("product_category_id")product_category_id:String
  ): GetProductList










    @GET("products/getDetail")
    suspend fun getProductDetail(
        @Query("product_id")product_id:String
    ):GetProductDetail






    @FormUrlEncoded
    @POST("products/setRating")
    suspend fun addRating(
    @Field("product_id")product_id:String,
    @Field("rating")rating:Number
    ):GetRating







    @FormUrlEncoded
    @POST("addToCart")
    suspend fun addToCart(
        @Header("access_token")access_token:String,
        @Field("product_id")product_id:Number,
        @Field("quantity")quantity:Number
    ):AddToCart






    @GET("cart")
    suspend fun myCart(
        @Header("access_token")access_token:String
    ):MyCard



    @FormUrlEncoded
   @POST("deleteCart")
   suspend fun deleteCartItem(
       @Header("access_token")access_token:String,
       @Field("product_id")product_id:Number
   ):Deleteitem


@FormUrlEncoded
   @POST("editCart")
   suspend fun changeQuantity(
       @Header("access_token")access_token:String,
       @Field("product_id")product_id:Number,
       @Field("quantity")quantity:Number
   ):EditCart


   @FormUrlEncoded
   @POST("order")
   suspend fun placeOrder(
       @Header("access_token")access_token:String,
       @Field("address")address:String
   ):PlaceOrder


    @GET("orderList")
    suspend fun myOder(
        @Header("access_token")access_token:String
    ):MyOrder


    @GET("orderDetail")
    suspend fun orderDetail(
        @Header("access_token")access_token:String,
        @Query("order_id")order_id:Number
    ):OrderDetail


    @FormUrlEncoded
    @POST("users/forgot")
    suspend fun forgotPwd(

        @Field("email")email:String
    ):ForgotPassword

    @FormUrlEncoded
   @POST("users/update")
   suspend fun updateProfile(
       @Header("access_token")access_token:String,
       @Field("first_name")first_name:String,
       @Field("last_name")last_name:String,
       @Field("email")email:String,
       @Field("dob")dob:String,
       @Field("phone_no")phone_no:String,
       @Field("profile_pic")profile_pic:String
   ):UpdateProfile


    @FormUrlEncoded
    @POST("users/change")
    suspend fun changePwd(
        @Header("access_token")access_token:String,
        @Field("old_password")old_password:String,
        @Field("password")password:String,
        @Field("confirm_password")confirm_password:String
    ):ChangePwd





    companion object{
    val interceptor=HttpLoggingInterceptor().apply {
        this.level=HttpLoggingInterceptor.Level.BODY
    }
        val client=OkHttpClient.Builder().apply {


            this.connectTimeout(50,java.util.concurrent.TimeUnit.SECONDS)
            this.readTimeout(50,java.util.concurrent.TimeUnit.SECONDS)
            this.writeTimeout(50,java.util.concurrent.TimeUnit.SECONDS)
            this.addInterceptor(interceptor)
        }.build()



        var retrofitService:Api?=null
        fun getInstance() : Api {

            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                        .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(Api::class.java)
            }
            return retrofitService!!
        }
    }
}