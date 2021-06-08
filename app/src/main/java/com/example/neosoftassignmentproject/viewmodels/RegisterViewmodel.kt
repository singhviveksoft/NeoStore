package com.example.neosoftassignmentproject.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.neosoftassignmentproject.model.UserRegisterData
import com.example.neosoftassignmentproject.repository.UserRepository
import kotlinx.coroutines.launch

class RegisterViewmodel(val repository: UserRepository) : ViewModel() {
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


    //fun userRegister(first_name:String,last_name:String,email:String,password:String,confirm_password:String,gender:String,phone_no:Int){
/*
    fun userRegister() {

        var response:UserRegisterData?=null

        viewModelScope.launch {
            //  val response=repository.userRegister(first_name,last_name,email,password,confirm_password,gender,phone_no)


            try {
                 response = repository.userRegister(
                    fName.value!!,
                    lname.value!!,
                    email.value!!,
                    pwd.value!!,
                    confPwd.value!!,
                    gender.value!!,
                    mob.value!!
                )
                userRegr?.value=response

            } catch (throwable: Throwable) {
                when (throwable) {
                    is HttpException -> {
                        ResponseResult.Failed<UserRegisterData>(
                            false,
                            throwable.code(),
                            throwable.response()?.errorBody()
                        )


                    }
                    else -> {
                        ResponseResult.Failed<UserRegisterData>(true, null, null)

                    }

                }

            }


        }



        ResponseResult.Sucess<UserRegisterData>(_userRegr)
    }
*/



    fun userRegister() {

        var response:UserRegisterData?=null

        viewModelScope.launch {
            //  val response=repository.userRegister(first_name,last_name,email,password,confirm_password,gender,phone_no)


            try {
                response = repository.userRegister(
                    fName.value!!,
                    lname.value!!,
                    email.value!!,
                    pwd.value!!,
                    confPwd.value!!,
                    gender.value!!,
                    mob.value!!
                )
                userRegr?.value=response


            } catch (ex: Exception) {
                Log.i("RegisterViewModel",ex.message.toString())
            }


        }



    }

}