package com.example.neosoftassignmentproject.viewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.neosoftassignmentproject.adapter.UserProductAdapter
import com.example.neosoftassignmentproject.repository.UserRepository
import com.example.neosoftassignmentproject.viewmodels.HomeViewModel
import com.example.neosoftassignmentproject.viewmodels.LoginViewmodel
import com.example.neosoftassignmentproject.viewmodels.RegisterViewmodel
import com.example.neosoftassignmentproject.views.RegisterFragment
import java.lang.IllegalArgumentException

class RegisterViewmodelfactory(val repository: UserRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterViewmodel::class.java)){
            return RegisterViewmodel(repository) as T
        }
        else  if (modelClass.isAssignableFrom(LoginViewmodel::class.java)){
            return LoginViewmodel(repository) as T
        }
        else  if (modelClass.isAssignableFrom(HomeViewModel::class.java)){
            return HomeViewModel(repository) as T
        }
        throw IllegalArgumentException("Viewmodelfactory")
    }
}