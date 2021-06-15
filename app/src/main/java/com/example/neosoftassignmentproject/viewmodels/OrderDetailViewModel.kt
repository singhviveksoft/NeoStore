package com.example.neosoftassignmentproject.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.neosoftassignmentproject.model.GetProductList
import com.example.neosoftassignmentproject.model.MyOrder
import com.example.neosoftassignmentproject.repository.UserRepository
import kotlinx.coroutines.launch

class MyOrderListViewModel(val repository: UserRepository):ViewModel() {
    private val myOrder=MutableLiveData<MyOrder>()
    val _myOrder:LiveData<MyOrder>
    get() = myOrder


    fun myOrder(access_token:String){
        viewModelScope.launch {
            try {
                val response=repository.myOder(access_token)
                myOrder.value=response
              //  val value= response.data.product_categories[1]
            }catch (ex:Exception){
                Log.i("HomeViewModel","${ex.message.toString()}")

            }
        }
    }


}