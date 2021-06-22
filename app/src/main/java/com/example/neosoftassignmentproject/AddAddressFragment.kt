package com.example.neosoftassignmentproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.neosoftassignmentproject.constants.interfaces.Api
import com.example.neosoftassignmentproject.constants.utils.ApiResult
import com.example.neosoftassignmentproject.databinding.FragmentAddAddressBinding
import com.example.neosoftassignmentproject.db.DataBase
import com.example.neosoftassignmentproject.repository.AddressRepository
import com.example.neosoftassignmentproject.repository.UserRepository
import com.example.neosoftassignmentproject.viewModelFactory.DBViewModelFactory
import com.example.neosoftassignmentproject.viewModelFactory.UserViewmodelfactory
import com.example.neosoftassignmentproject.viewmodels.AddAdressViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.*


class AddAddressFragment : Fragment() {
private lateinit var binding: FragmentAddAddressBinding
    private lateinit var viewModel: AddAdressViewModel
    private lateinit var db:DataBase
    private val api= Api.getInstance()
var status:Boolean?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db=DataBase.getInstance(requireContext())
        viewModel= ViewModelProvider(this,DBViewModelFactory(AddressRepository(api,db))).get(AddAdressViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentAddAddressBinding.inflate(inflater, container, false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel=viewModel

        viewModel.isDataAvailable.observe(viewLifecycleOwner){
            status=it
        }

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
                                findNavController().navigate(R.id.action_addAddressFragment_to_addresslistFragment)

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


      //  })

  //  }




        binding.saveAddressBtn.setOnClickListener {
            viewModel.saveAddress()
/*
            lifecycleScope.launch {
                val job=    CoroutineScope(Dispatchers.Main).launch{


                }

                job.join()

            }
*/


        }




    }


    override fun onPause() {
        super.onPause()
    }
}