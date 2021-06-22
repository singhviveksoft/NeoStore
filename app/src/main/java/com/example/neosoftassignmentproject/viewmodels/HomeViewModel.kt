package com.example.neosoftassignmentproject.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.neosoftassignmentproject.constants.UserPreferences
import com.example.neosoftassignmentproject.constants.utils.ApiResult
import com.example.neosoftassignmentproject.model.*
import com.example.neosoftassignmentproject.repository.CategoryRepository
import com.example.neosoftassignmentproject.repository.UserRepository
import kotlinx.coroutines.launch

class HomeViewModel(val repository: CategoryRepository):ViewModel() {
  val getCategory=repository.getCatrgy

   //data observer for back press
    var isDataAvailable=MutableLiveData<Boolean?>()
    val _isDataAvailable:LiveData<Boolean?>
        get() = isDataAvailable


    private val apiResult=MutableLiveData<ApiResult>()
    val _apiResult:LiveData<ApiResult>
        get() = apiResult


    private val product=MutableLiveData<GetProductCategiryData>()
    val getProduct:LiveData<GetProductCategiryData>
    get() = product

    private val updateProfile=MutableLiveData<UpdateProfile>()
    val _updateProfile:LiveData<UpdateProfile>
        get() = updateProfile


    private val changePwd=MutableLiveData<ChangePwd>()
    val _changePwd:LiveData<ChangePwd>
        get() = changePwd


     val _access_token=MutableLiveData<String>()


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




    fun updateProfile(access_token:String,
                      first_name:String,
                      last_name:String,
                      email:String,
                      dob:String,
                      phone_no:String,
                      profile_pic:String){
        viewModelScope.launch {
            try {
                val response=repository.updateProfile(access_token,first_name,last_name,email,dob,phone_no,profile_pic)
                updateProfile.value=response
                getProduct(_access_token.value!!)

            }catch (ex:Exception){
                Log.i("HomeViewModel","${ex.message.toString()}")

            }
        }

    }


     fun changePwd(access_token:String,
                          old_password:String,
                          password:String,
                          confirm_password:String){
         viewModelScope.launch {

             apiResult.value=ApiResult.Loading
        try {
            val response=repository.changePwd(access_token,old_password,password,confirm_password)
            apiResult.value=ApiResult.Success(response.user_msg)
            changePwd.value=response
        }catch (ex:Exception){
            Log.i("HomeViewModel","${ex.message.toString()}")
            apiResult.value=ApiResult.Error(ex.message.toString())

        }

         }
     }


    fun saveCategory(cartgy:List<ProductCategory>){
        viewModelScope.launch {
            apiResult.value=ApiResult.Loading

            try {
              val response= repository.insertCatrgy(cartgy)
                apiResult.value=ApiResult.Success("category added")

            } catch (ex:Exception)
          {
              apiResult.value=ApiResult.Error(ex.message.toString())

          }
        }
    }


}