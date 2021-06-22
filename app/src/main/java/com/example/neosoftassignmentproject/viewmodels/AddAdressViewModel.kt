package com.example.neosoftassignmentproject.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.neosoftassignmentproject.constants.utils.ApiResult
import com.example.neosoftassignmentproject.model.AddAddress
import com.example.neosoftassignmentproject.model.PlaceOrder
import com.example.neosoftassignmentproject.repository.AddressRepository
import com.example.neosoftassignmentproject.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddAdressViewModel(val repository: AddressRepository):ViewModel() {

    val getAddressList=repository.getAddress

    private val apiResult=MutableLiveData<ApiResult>()
    val _apiResult:LiveData<ApiResult>
        get() = apiResult


    var isDataAvailable=MutableLiveData<Boolean?>()
    val _isDataAvailable:LiveData<Boolean?>
    get() = isDataAvailable




    val addres=MutableLiveData<String?>()

    val city=MutableLiveData<String?>()
    val landmark=MutableLiveData<String?>()
    val state=MutableLiveData<String?>()
    val zip_code=MutableLiveData<String?>()
    val country=MutableLiveData<String?>()
    val getAddress=MutableLiveData<ArrayList<AddAddress>>()
   /* val _getAddress:LiveData<ArrayList<AddAddress>>
    get() = getAddress*/

    val arrayList= arrayListOf<AddAddress>()
    var job=Job



    private val placeOrder=MutableLiveData<PlaceOrder>()
    val _placeOrder:LiveData<PlaceOrder>
        get() = placeOrder


     fun saveAddress() {
     viewModelScope.launch {

             if (!addres.value.isNullOrBlank() && !landmark.value.isNullOrBlank() && !state.value.isNullOrBlank() && !zip_code.value.isNullOrBlank() && !country.value.isNullOrBlank() && !city.value.isNullOrBlank()) {
                 val _address = addres.value!!
                 val _landmark = landmark.value!!
                 val _state = state.value!!
                 val _zip_code = zip_code.value!!
                 val _country = country.value!!
                 val _city = city.value!!

                 val response = AddAddress( id = 0,_address, _landmark, _city, _state, _zip_code, _country)

                        apiResult.value=ApiResult.Loading
                        try {
                            repository.insertAddress(listOf(response))
                            isDataAvailable.value=true
                            apiResult.value=ApiResult.Success("Address add successfull")

                            //   arrayList.add(response)
                            // getAddress.value = arrayList
                            //   Log.i("addressList", "${getAddress.value}")
                        } catch (ex: Exception) {
                            apiResult.value=ApiResult.Error(ex.message.toString())

                        }




             }

             else {
                 apiResult.value = ApiResult.Error("Enter your complete address")
             }
         }

     }


     fun deleteAddress(address:AddAddress){
        viewModelScope.launch {
            apiResult.value=ApiResult.Loading
            try {
                val response=repository.deleteItem(address)
                apiResult.value=ApiResult.Success("Address Deleted Successfull")
            }catch (ex:Exception){
                apiResult.value=ApiResult.Error(ex.message.toString())
            }
        }

    }




    suspend fun placeOrder(access_token:String,address:String){
    val job=   viewModelScope.launch {
        apiResult.value=ApiResult.Loading
            try {

                val response=repository.placeOrder(access_token,address)
                placeOrder.value=response
                isDataAvailable.value=true
                apiResult.value=ApiResult.Success("${response.user_msg}")

            }
            catch (ex:Exception){
                Log.i("HomeViewModel","${ex.message.toString()}")
                apiResult.value=ApiResult.Error(ex.message.toString())

            }

        }
     job.join()
    }





}