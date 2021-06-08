package com.example.neosoftassignmentproject.constants.interfaces


import com.example.neosoftassignmentproject.model.ProductCategory
import com.example.neosoftassignmentproject.model.UserLoginData
import com.example.neosoftassignmentproject.model.UserRegisterData
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
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
    ):Array<ProductCategory>?



  @GET("products/getList")
  suspend fun getProduct(
      @Query("product_category_id")product_category_id:String
  )











    companion object{
    val interceptor=HttpLoggingInterceptor().apply {
        this.level=HttpLoggingInterceptor.Level.BODY
    }
        val client=OkHttpClient.Builder().apply {
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