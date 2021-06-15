package com.example.neosoftassignmentproject.viewmodels

import android.app.AlertDialog
import android.util.Log
import androidx.lifecycle.*
import com.example.neosoftassignmentproject.constants.UserPreferences
import com.example.neosoftassignmentproject.model.*
import com.example.neosoftassignmentproject.repository.UserRepository
import kotlinx.coroutines.launch

class MyCartViewModel(val repository: UserRepository):ViewModel() {
    private val cart=MutableLiveData<MyCard>()
    val _cart:LiveData<MyCard>
    get() = cart

    private val iteamDelete=MutableLiveData<Deleteitem>()
    val _iteamDelete:LiveData<Deleteitem>
        get() = iteamDelete


    private val changeQuantity=MutableLiveData<EditCart>()
    val _changeQuantity:LiveData<EditCart>
        get() = changeQuantity


    val accessToken=MutableLiveData<String>()






//my cart list item
    fun getMyCart(access_token:String){
        viewModelScope.launch {
            try {
                val response=repository.myCart(access_token)
                cart.value=response
                Log.i("HomeViewModel","${cart.value.toString()}")

                //  val value= response.data.product_categories[1]
            }catch (ex:Exception){
                Log.i("HomeViewModel","${ex.message.toString()}")

            }
        }
    }

  //delete cart item
    fun deleteCartItem(access_token:String,product_id:Number){
        viewModelScope.launch {
            try {
                val response=repository.deleteCartItem(access_token,product_id)
                iteamDelete.value=response
                getMyCart(accessToken.value!!)
            }
            catch (ex:Exception){
                Log.i("HomeViewModel","${ex.message.toString()}")

            }
        }
    }


    //edit cart item quantity
    fun changeQuantity(access_token:String,product_id:Number,quantity:Number){
        viewModelScope.launch {
            try {
                val response=repository.changeQuantity(access_token,product_id,quantity)
                changeQuantity.value=response
                getMyCart(accessToken.value!!)

            }
            catch (ex:Exception){
                Log.i("HomeViewModel","${ex.message.toString()}")

            }
        }
    }






}