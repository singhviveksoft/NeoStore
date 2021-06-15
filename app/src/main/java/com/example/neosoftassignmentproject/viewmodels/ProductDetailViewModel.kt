package com.example.neosoftassignmentproject.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.neosoftassignmentproject.model.GetProductDetail
import com.example.neosoftassignmentproject.model.GetProductList
import com.example.neosoftassignmentproject.model.GetRating
import com.example.neosoftassignmentproject.repository.UserRepository
import kotlinx.coroutines.launch

class ProductDetailViewModel(val repository: UserRepository):ViewModel() {
    private val product=MutableLiveData<GetProductDetail>()
    val productDetail:LiveData<GetProductDetail>
    get() = product

    private val product_rating=MutableLiveData<GetRating>()
    val _rating:LiveData<GetRating>
        get() = product_rating


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
            try {
             val response=   repository.addRating(product_id,rating)
                product_rating.value=response
                Log.i("Rating","${response.toString()}")

            }catch (ex:Exception){
                Log.i("Rating","${ex.message}")

            }
        }
    }

    fun addToCart(access_token:String,product_id:Number,quantity:Number){
        viewModelScope.launch {
            try {
            val response=repository.addToCart(access_token,product_id,quantity)
                Log.i("addTocart","${response.toString()}")

            }catch (ex:Exception){
                Log.i("addTocart","${ex.message}")

            }
        }
    }


}