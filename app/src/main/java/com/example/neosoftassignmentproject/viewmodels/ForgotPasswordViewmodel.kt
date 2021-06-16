package com.example.neosoftassignmentproject.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.neosoftassignmentproject.model.ForgotPassword
import com.example.neosoftassignmentproject.model.UserLoginData
import com.example.neosoftassignmentproject.repository.UserRepository
import kotlinx.coroutines.launch

class ForgotPasswordViewmodel(val repository: UserRepository) : ViewModel() {

        val email = MutableLiveData<String>()
    private val forgotPwd=MutableLiveData<ForgotPassword>()
    val _forgotPwd:LiveData<ForgotPassword>
    get() = forgotPwd













    fun forgotPwd() {

        var response:UserLoginData?=null
        viewModelScope.launch{
            //  val response=repository.userRegister(first_name,last_name,email,password,confirm_password,gender,phone_no)


            try {
                val response=repository.forgotPwd(email.value!!)

                forgotPwd.value=response
            }
            catch (ex: Exception) {
                Log.i("LoginViewModel",ex.message.toString())
            }


        }


    }

}