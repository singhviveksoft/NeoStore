package com.example.neosoftassignmentproject.viewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.neosoftassignmentproject.repository.AddressRepository
import com.example.neosoftassignmentproject.repository.UserRepository
import com.example.neosoftassignmentproject.viewmodels.AddAdressViewModel

class DBViewModelFactory(val repository: AddressRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddAdressViewModel::class.java)){
            return AddAdressViewModel(repository) as T
        }
        throw IllegalArgumentException("DBViewModelFactory")
    }
}