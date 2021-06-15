package com.example.neosoftassignmentproject.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.neosoftassignmentproject.model.AddAddress
import com.example.neosoftassignmentproject.model.PlaceOrder
import com.example.neosoftassignmentproject.repository.UserRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class AddAdressViewModel(val repository: UserRepository):ViewModel() {
    val addres=MutableLiveData<String>()
    val city=MutableLiveData<String>()
    val landmark=MutableLiveData<String>()
    val state=MutableLiveData<String>()
    val zip_code=MutableLiveData<String>()
    val country=MutableLiveData<String>()
    val getAddress=MutableLiveData<ArrayList<AddAddress>>()
    val _getAddress:LiveData<ArrayList<AddAddress>>
    get() = getAddress

    val arrayList= arrayListOf<AddAddress>()
    var job=Job



    private val placeOrder=MutableLiveData<PlaceOrder>()
    val _placeOrder:LiveData<PlaceOrder>
        get() = placeOrder


     fun saveAddress(){
        val _address=addres.value!!
        val _landmark=landmark.value!!
        val _state=state.value!!
        val _zip_code=zip_code.value!!
        val _country=country.value!!
        val _city=city.value!!
        val response= AddAddress(_address,_landmark,_city,_state,_zip_code,_country)

         arrayList.add(response)
         getAddress.value=arrayList
         Log.i("addressList","${getAddress.value}")
    }





 suspend fun placeOrder(access_token:String,address:String){
    val job=   viewModelScope.launch {
            try {
                val response=repository.placeOrder(access_token,address)
                placeOrder.value=response
            }
            catch (ex:Exception){
                Log.i("HomeViewModel","${ex.message.toString()}")

            }

        }
     job.join()
    }

}