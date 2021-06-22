package com.example.neosoftassignmentproject.viewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.neosoftassignmentproject.repository.AddressRepository
import com.example.neosoftassignmentproject.repository.CategoryRepository
import com.example.neosoftassignmentproject.repository.UserRepository
import com.example.neosoftassignmentproject.viewmodels.AddAdressViewModel
import com.example.neosoftassignmentproject.viewmodels.HomeViewModel

class CateyDBViewModelFactory(val repository: CategoryRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)){
            return HomeViewModel(repository) as T
        }
        throw IllegalArgumentException("DBViewModelFactory")
    }
}