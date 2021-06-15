package com.example.neosoftassignmentproject.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.neosoftassignmentproject.model.GetProductList
import com.example.neosoftassignmentproject.repository.UserRepository
import kotlinx.coroutines.launch

class ProductListViewModel(val repository: UserRepository):ViewModel() {
    private val product=MutableLiveData<GetProductList>()
    val ProductList:LiveData<GetProductList>
    get() = product


    fun getProductList(product_category_id:String){
        viewModelScope.launch {
            try {
                val response=repository.getProduct(product_category_id)
               product.value=response
              //  val value= response.data.product_categories[1]
            }catch (ex:Exception){
                Log.i("HomeViewModel","${ex.message.toString()}")

            }
        }
    }


}