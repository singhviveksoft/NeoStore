package com.example.neosoftassignmentproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.preferences.protobuf.Api
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.neosoftassignmentproject.adapter.AddressAdapter
import com.example.neosoftassignmentproject.constants.utils.ApiResult
import com.example.neosoftassignmentproject.databinding.FragmentStoreLocatorBinding
import com.example.neosoftassignmentproject.db.DataBase
import com.example.neosoftassignmentproject.model.AddAddress
import com.example.neosoftassignmentproject.repository.AddressRepository
import com.example.neosoftassignmentproject.viewModelFactory.DBViewModelFactory
import com.example.neosoftassignmentproject.viewmodels.AddAdressViewModel
import com.google.android.material.snackbar.Snackbar


class StoreLocatorFragment : Fragment(),AddressAdapter.clickItem {
private lateinit var binding:FragmentStoreLocatorBinding
private lateinit var viewModel:AddAdressViewModel
private val api=com.example.neosoftassignmentproject.constants.interfaces.Api.getInstance()
    private lateinit var db:DataBase
    private lateinit var myAdapter:AddressAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db= DataBase.getInstance(requireContext())
        viewModel=ViewModelProvider(this,DBViewModelFactory(AddressRepository(api,db))).get(AddAdressViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentStoreLocatorBinding.inflate(inflater, container, false)
        myAdapter= AddressAdapter(this)
        binding.rvStoreLocator.adapter=myAdapter
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel._apiResult.observe(viewLifecycleOwner, Observer {
            when(it){
                is ApiResult.Success->{
                    Snackbar.make(
                        binding.root,
                        "${it.msg}",
                        Snackbar.LENGTH_LONG
                    ).show()
                    //  viewModel._apiResult.removeObservers(viewLifecycleOwner)

/*

                    if (status){

                        status=false
                    }
*/


                    //  binding.progressBar.isVisible = false
                }
                is ApiResult.Error->{
                    Snackbar.make(
                        binding.root,
                        "${it.message}",
                        Snackbar.LENGTH_LONG
                    ).show()
                    //  binding.progressBar.isVisible = false
                    //   viewModel._apiResult.removeObservers(viewLifecycleOwner)

                }
                is ApiResult.Loading->{
                    Snackbar.make(
                        binding.root,
                        "Loading",
                        Snackbar.LENGTH_LONG
                    ).show()
                    //  binding.progressBar.isVisible = false
                    //  viewModel._apiResult.removeObservers(viewLifecycleOwner)

                }

            }

        })



        viewModel.getAddressList.observe(viewLifecycleOwner, Observer {

            myAdapter.addProduct(it)
        })
    }

    override fun onClick(address: AddAddress) {
        viewModel.deleteAddress(address)
    }

}