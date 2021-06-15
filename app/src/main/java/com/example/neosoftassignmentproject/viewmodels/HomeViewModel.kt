package com.example.neosoftassignmentproject.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.neosoftassignmentproject.model.GetProductCategiryData
import com.example.neosoftassignmentproject.repository.UserRepository
import kotlinx.coroutines.launch

class HomeViewModel(val repository: UserRepository):ViewModel() {
    private val product=MutableLiveData<GetProductCategiryData>()
    val getProduct:LiveData<GetProductCategiryData>
    get() = product


    fun getProduct(access_token:String){
        viewModelScope.launch {
            try {
                val response=repository.getUser(access_token)
               product.value=response
              //  val value= response.data.product_categories[1]
           }catch (ex:Exception){
                Log.i("HomeViewModel","${ex.message.toString()}")

            }
        }
    }


}