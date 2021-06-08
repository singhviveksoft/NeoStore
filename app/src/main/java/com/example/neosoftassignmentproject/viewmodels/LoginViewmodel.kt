package com.example.neosoftassignmentproject.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.neosoftassignmentproject.model.UserLoginData
import com.example.neosoftassignmentproject.repository.UserRepository
import kotlinx.coroutines.launch

class LoginViewmodel(val repository: UserRepository) : ViewModel() {

        val email = MutableLiveData<String>()
       val pwd = MutableLiveData<String>()
    val userLogin = MutableLiveData<UserLoginData>()?: null
        val _userLogin:LiveData<UserLoginData>?
            get() =userLogin




    //fun userRegister(first_name:String,last_name:String,email:String,password:String,confirm_password:String,gender:String,phone_no:Int){
/*
    fun userLogin() {

        var response:UserLoginData?=null
        viewModelScope.launch{
            //  val response=repository.userRegister(first_name,last_name,email,password,confirm_password,gender,phone_no)


            try {
                 response = repository.userLogin(
                    email.value!!,
                    pwd.value!!
                )





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
        userLogin?.value=response

        ResponseResult.Sucess(_userLogin)
    }
*/


    fun userLogin() {

        var response:UserLoginData?=null
        viewModelScope.launch{
            //  val response=repository.userRegister(first_name,last_name,email,password,confirm_password,gender,phone_no)


            try {
                response = repository.userLogin(
                    email.value!!,
                    pwd.value!!
                )


                userLogin?.value=response


            }
            catch (ex: Exception) {
                Log.i("LoginViewModel",ex.message.toString())
            }


        }


    }

}