package com.example.neosoftassignmentproject.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.neosoftassignmentproject.model.GetUserData
import com.example.neosoftassignmentproject.model.ProductCategory
import com.example.neosoftassignmentproject.repository.UserRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class HomeViewModel(val repository: UserRepository):ViewModel() {
    private val product=MutableLiveData<ArrayList<ProductCategory>>()
    val getProduct:LiveData<ArrayList<ProductCategory>>
    get() = product


    fun getProduct(access_token:String){
        viewModelScope.launch {
            try {
                val response=repository.getUser(access_token)
                product.value.add(response)response!!
            }catch (ex:Exception){
                Log.i("HomeViewModel","${ex.message.toString()}")

            }
        }
    }


}