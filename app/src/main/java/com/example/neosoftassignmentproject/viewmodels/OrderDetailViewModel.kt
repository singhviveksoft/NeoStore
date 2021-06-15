package com.example.neosoftassignmentproject.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.neosoftassignmentproject.model.GetProductList
import com.example.neosoftassignmentproject.model.MyOrder
import com.example.neosoftassignmentproject.model.OrderDetail
import com.example.neosoftassignmentproject.repository.UserRepository
import kotlinx.coroutines.launch

class OrderDetailViewModel(val repository: UserRepository):ViewModel() {
    private val orderdetail=MutableLiveData<OrderDetail>()
    val _orderdetail:LiveData<OrderDetail>
    get() = orderdetail


    fun orderDetail(access_token:String,order_id:Number){
        viewModelScope.launch {
            try {
                val response=repository.orderDetail(access_token,order_id)
                orderdetail.value=response
              //  val value= response.data.product_categories[1]
            }catch (ex:Exception){
                Log.i("HomeViewModel","${ex.message.toString()}")

            }
        }
    }


}