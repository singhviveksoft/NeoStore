package com.example.neosoftassignmentproject

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.neosoftassignmentproject.adapter.AddressAdapter
import com.example.neosoftassignmentproject.constants.UserPreferences
import com.example.neosoftassignmentproject.constants.interfaces.Api
import com.example.neosoftassignmentproject.constants.utils.ApiResult
import com.example.neosoftassignmentproject.databinding.FragmentAddresslistBinding
import com.example.neosoftassignmentproject.db.DataBase
import com.example.neosoftassignmentproject.model.AddAddress
import com.example.neosoftassignmentproject.repository.AddressRepository
import com.example.neosoftassignmentproject.repository.UserRepository
import com.example.neosoftassignmentproject.viewModelFactory.DBViewModelFactory
import com.example.neosoftassignmentproject.viewModelFactory.UserViewmodelfactory
import com.example.neosoftassignmentproject.viewmodels.AddAdressViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.*


class AddresslistFragment : Fragment(),AddressAdapter.clickItem {
    private lateinit var binding:FragmentAddresslistBinding
    private lateinit var viewModel:AddAdressViewModel
    private lateinit var myAdapter:AddressAdapter
    private lateinit var db:DataBase
    private val api= Api.getInstance()
    val arrayList=ArrayList<AddAddress>()
    var accessToken:String?=null
    var address:String?=null
    var status:Boolean?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentAddresslistBinding.inflate(inflater, container, false)
        db=DataBase.getInstance(requireContext())
        viewModel=ViewModelProvider(this,DBViewModelFactory(AddressRepository(api,db))).get(AddAdressViewModel::class.java)

        myAdapter= AddressAdapter(this)
        binding.rvAddress.adapter=myAdapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.isDataAvailable.observe(viewLifecycleOwner){
            status=it
        }

        binding.placeOrderBtn.isVisible=false

        viewModel._apiResult.observe(viewLifecycleOwner, Observer {
            if (status == true) {
                when (it) {
                    is ApiResult.Success -> {
                        Snackbar.make(
                            binding.root,
                            "${it.msg}",
                            Snackbar.LENGTH_SHORT
                        ).show()
                        viewModel.isDataAvailable.value = null
                        findNavController().navigate(R.id.action_addresslistFragment_to_myOrderFragment)

                        //  viewModel._apiResult.removeObservers(viewLifecycleOwner)

/*

                    if (status){

                        status=false
                    }
*/


                        //  binding.progressBar.isVisible = false
                    }
                    is ApiResult.Error -> {
                        Snackbar.make(
                            binding.root,
                            "${it.message}",
                            Snackbar.LENGTH_SHORT
                        ).show()
                        //  binding.progressBar.isVisible = false
                        //   viewModel._apiResult.removeObservers(viewLifecycleOwner)

                    }
                    is ApiResult.Loading -> {
                        Snackbar.make(
                            binding.root,
                            "Loading",
                            Snackbar.LENGTH_SHORT
                        ).show()
                        //  binding.progressBar.isVisible = false
                        //  viewModel._apiResult.removeObservers(viewLifecycleOwner)

                    }

                }
            }
        })





        UserPreferences(requireContext()).getAccessToken.asLiveData().observe(viewLifecycleOwner){
            accessToken=it
        }
        UserPreferences(requireContext()).getAddress.asLiveData().observe(viewLifecycleOwner){
            address=it
        }



        lifecycleScope.launch {
            UserPreferences(requireContext()).removeAddress()

        }




        viewModel.getAddressList.observe(viewLifecycleOwner, Observer {
        Log.i("addressList","${it.toString()}")
            if (it!=null) {
                if (it.size!=0){
                    arrayList.addAll(it)
                    binding.placeOrderBtn.isVisible=true

                }else{
                    binding.placeOrderBtn.isVisible=false

                }

                myAdapter.addProduct(it)
            }
            else{
                binding.placeOrderBtn.isVisible=false

            }

        })


        binding.placeOrderBtn.setOnClickListener {
            if (address!="0") {
                CoroutineScope(Dispatchers.Main).launch {

                    viewModel.placeOrder(accessToken!!, address!!)

                 //   findNavController().navigate(R.id.action_addresslistFragment_to_myOrderFragment)
                    //   Toast.makeText(requireContext(), "runBlocking", Toast.LENGTH_SHORT).show()


                }

                //  findNavController().navigate(R.id.action_addresslistFragment_to_myOrderFragment)
            }else{
                Snackbar.make(binding.rvAddress,"Select your address",Snackbar.LENGTH_SHORT).show()
            }

        }
/*
        viewModel._placeOrder.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireContext(), "${it.user_msg}", Toast.LENGTH_SHORT).show()
        })
*/

    }

    override fun onClick(address: AddAddress) {
  viewModel.deleteAddress(address)
    }
}
