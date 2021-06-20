package com.example.neosoftassignmentproject.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.neosoftassignmentproject.constants.utils.ApiResponse
import com.example.neosoftassignmentproject.constants.utils.ApiResult
import com.example.neosoftassignmentproject.model.AddToCart
import com.example.neosoftassignmentproject.model.GetProductDetail
import com.example.neosoftassignmentproject.model.GetProductList
import com.example.neosoftassignmentproject.model.GetRating
import com.example.neosoftassignmentproject.repository.UserRepository
import kotlinx.coroutines.launch

class ProductDetailViewModel(val repository: UserRepository):ViewModel() {

    val oneTimeCall=MutableLiveData<Boolean?>()
    val product_id=MutableLiveData<String?>()


    private val apiResult=MutableLiveData<ApiResult>()
    val _apiResult:LiveData<ApiResult>
        get() = apiResult


    private val product=MutableLiveData<GetProductDetail>()
    val productDetail:LiveData<GetProductDetail>
    get() = product

    private val product_rating=MutableLiveData<GetRating>()
    val _rating:LiveData<GetRating>
        get() = product_rating


    private val addToCart=MutableLiveData<AddToCart>()
    val _addToCart:LiveData<AddToCart>
        get() = addToCart


    fun getProductDetail(product_id:String){
        viewModelScope.launch {
            try {
                val response=repository.getProductDetail(product_id)

               product.value=response
              //  val value= response.data.product_categories[1]
            }catch (ex:Exception){
                Log.i("ProductDetailViewModel","${ex.message.toString()}")

            }
        }
    }




    fun addRating(product_id:String,rating:Number){
        viewModelScope.launch {
            apiResult.value=ApiResult.Loading
            try {
             val response=   repository.addRating(product_id,rating)
                oneTimeCall.value=null
                apiResult.value=ApiResult.Success(response.user_msg)
                product_rating.value=response

               // Log.i("Rating","${response.toString()}")
                getProductDetail(product_id)
            }catch (ex:Exception){
                apiResult.value=ApiResult.Error(ex.message.toString())

                Log.i("Rating","${ex.message}")

            }
        }
    }

    fun addToCart(access_token:String,product_id:Number,quantity:Number){
        viewModelScope.launch {
            apiResult.value=ApiResult.Loading
            try {
            val response=repository.addToCart(access_token,product_id,quantity)
                oneTimeCall.value=null
                apiResult.value=ApiResult.Success(response.user_msg)
                addToCart.value=response
                ApiResponse.success(data =response)

            }catch (ex:Exception){
                Log.i("addTocart","${ex.message}")
                ApiResponse.error(data = null, message = ex.message ?: "Error Occurred!")


            }
        }
    }


}