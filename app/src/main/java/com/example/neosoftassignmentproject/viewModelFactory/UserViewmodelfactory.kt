package com.example.neosoftassignmentproject.viewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.neosoftassignmentproject.adapter.UserProductAdapter
import com.example.neosoftassignmentproject.repository.UserRepository
import com.example.neosoftassignmentproject.viewmodels.*
import com.example.neosoftassignmentproject.views.RegisterFragment
import java.lang.IllegalArgumentException

class UserViewmodelfactory(val repository: UserRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterViewmodel::class.java)){
            return RegisterViewmodel(repository) as T
        }
        else  if (modelClass.isAssignableFrom(LoginViewmodel::class.java)){
            return LoginViewmodel(repository) as T
        }

        else if (modelClass.isAssignableFrom(ProductListViewModel::class.java)){
            return ProductListViewModel(repository) as T
        }
        else if (modelClass.isAssignableFrom(ProductDetailViewModel::class.java)){
            return ProductDetailViewModel(repository) as T
        }

        else if (modelClass.isAssignableFrom(MyCartViewModel::class.java)){
            return MyCartViewModel(repository) as T
        }


        else if (modelClass.isAssignableFrom(MyOrderListViewModel::class.java)){
            return MyOrderListViewModel(repository) as T
        }
        else if (modelClass.isAssignableFrom(OrderDetailViewModel::class.java)){
            return OrderDetailViewModel(repository) as T
        }

        else if (modelClass.isAssignableFrom(ForgotPasswordViewmodel::class.java)){
            return ForgotPasswordViewmodel(repository) as T
        }


        throw IllegalArgumentException("Viewmodelfactory")
    }
}