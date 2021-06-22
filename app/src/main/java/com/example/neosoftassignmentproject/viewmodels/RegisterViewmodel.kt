package com.example.neosoftassignmentproject.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.neosoftassignmentproject.constants.utils.ApiResult
import com.example.neosoftassignmentproject.model.UserRegisterData
import com.example.neosoftassignmentproject.repository.UserRepository
import kotlinx.coroutines.launch

class RegisterViewmodel(val repository: UserRepository) : ViewModel() {

    private val apiResult=MutableLiveData<ApiResult>()
    val _apiResult:LiveData<ApiResult>
        get() = apiResult


    val fName = MutableLiveData<String>()
    val lname = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val pwd = MutableLiveData<String>()
    val confPwd = MutableLiveData<String>()
    val gender = MutableLiveData<String>()
    val phoneNo = MutableLiveData<Long>()
    val mob: LiveData<Long>
        get() = phoneNo
 private   val userRegr=MutableLiveData<UserRegisterData>()?: null
   val _userRegr: LiveData<UserRegisterData>?
   get() = userRegr





    fun userRegister() {

     //   var response:UserRegisterData?=null

        viewModelScope.launch {
            //  val response=repository.userRegister(first_name,last_name,email,password,confirm_password,gender,phone_no)
            apiResult.value=ApiResult.Loading

            try {
              val  response = repository.userRegister(
                    fName.value!!,
                    lname.value!!,
                    email.value!!,
                    pwd.value!!,
                    confPwd.value!!,
                    gender.value!!,
                    mob.value!!
                )
                userRegr?.value=response
                apiResult.value=ApiResult.Success(response.user_msg)


            } catch (ex: Exception) {
                Log.i("RegisterViewModel",ex.message.toString())
                apiResult.value=ApiResult.Error(ex.message.toString())
            }


        }



    }

}