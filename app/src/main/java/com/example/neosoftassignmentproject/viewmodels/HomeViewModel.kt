package com.example.neosoftassignmentproject.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.neosoftassignmentproject.constants.UserPreferences
import com.example.neosoftassignmentproject.model.ChangePwd
import com.example.neosoftassignmentproject.model.GetProductCategiryData
import com.example.neosoftassignmentproject.model.UpdateProfile
import com.example.neosoftassignmentproject.repository.UserRepository
import kotlinx.coroutines.launch

class HomeViewModel(val repository: UserRepository):ViewModel() {
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


        try {
            val response=repository.changePwd(access_token,old_password,password,password)

            changePwd.value=response
        }catch (ex:Exception){
            Log.i("HomeViewModel","${ex.message.toString()}")

        }

         }
     }


}