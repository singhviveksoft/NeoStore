package com.example.neosoftassignmentproject

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.example.neosoftassignmentproject.adapter.AddressAdapter
import com.example.neosoftassignmentproject.constants.UserPreferences
import com.example.neosoftassignmentproject.constants.interfaces.Api
import com.example.neosoftassignmentproject.databinding.FragmentAddresslistBinding
import com.example.neosoftassignmentproject.model.AddAddress
import com.example.neosoftassignmentproject.repository.UserRepository
import com.example.neosoftassignmentproject.viewModelFactory.UserViewmodelfactory
import com.example.neosoftassignmentproject.viewmodels.AddAdressViewModel
import kotlinx.coroutines.*


class AddresslistFragment : Fragment(),AddressAdapter.clickItem {
    private lateinit var binding:FragmentAddresslistBinding
    private lateinit var viewModel:AddAdressViewModel
    private lateinit var myAdapter:AddressAdapter
    private val api= Api.getInstance()
    val arrayList=ArrayList<AddAddress>()
    var accessToken:String?=null
    var address:String?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentAddresslistBinding.inflate(inflater, container, false)
        viewModel=ViewModelProvider(requireActivity(),UserViewmodelfactory(UserRepository(api))).get(AddAdressViewModel::class.java)
        myAdapter= AddressAdapter(this)
        binding.rvAddress.adapter=myAdapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        UserPreferences(requireContext()).getAccessToken.asLiveData().observe(viewLifecycleOwner){
            accessToken=it
        }
        UserPreferences(requireContext()).getAddress.asLiveData().observe(viewLifecycleOwner){
            address=it
        }



        viewModel._getAddress.observe(requireActivity(), Observer {
        Log.i("addressList","${it.toString()}")


            myAdapter.addProduct(it)

        })


        binding.placeOrderBtn.setOnClickListener {
    CoroutineScope(Dispatchers.Main).launch {
        viewModel.placeOrder(accessToken!!,address!!)

            findNavController().navigate(R.id.action_addresslistFragment_to_myOrderFragment)
     //   Toast.makeText(requireContext(), "runBlocking", Toast.LENGTH_SHORT).show()


    }

            //  findNavController().navigate(R.id.action_addresslistFragment_to_myOrderFragment)
        }


        viewModel._placeOrder.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireContext(), "${it.user_msg}", Toast.LENGTH_SHORT).show()
        })

    }

    override fun onClick(address: AddAddress) {
        TODO("Not yet implemented")
    }
}