package com.example.neosoftassignmentproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.neosoftassignmentproject.constants.interfaces.Api
import com.example.neosoftassignmentproject.databinding.FragmentAddAddressBinding
import com.example.neosoftassignmentproject.repository.UserRepository
import com.example.neosoftassignmentproject.viewModelFactory.UserViewmodelfactory
import com.example.neosoftassignmentproject.viewmodels.AddAdressViewModel


class AddAddressFragment : Fragment() {
private lateinit var binding: FragmentAddAddressBinding
    private lateinit var viewModel: AddAdressViewModel
    private val api= Api.getInstance()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentAddAddressBinding.inflate(inflater, container, false)
        viewModel= ViewModelProvider(requireActivity(),UserViewmodelfactory(UserRepository(api))).get(AddAdressViewModel::class.java)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel=viewModel
        binding.saveAddressBtn.setOnClickListener {
            viewModel.saveAddress()

            findNavController().navigate(R.id.action_addAddressFragment_to_addresslistFragment)
        }
    }
}