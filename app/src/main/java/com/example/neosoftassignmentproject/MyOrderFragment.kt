package com.example.neosoftassignmentproject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.neosoftassignmentproject.adapter.MyOrderAdapter
import com.example.neosoftassignmentproject.constants.UserPreferences
import com.example.neosoftassignmentproject.constants.interfaces.Api
import com.example.neosoftassignmentproject.databinding.FragmentMyOrderBinding
import com.example.neosoftassignmentproject.model.MyOrderData
import com.example.neosoftassignmentproject.repository.UserRepository
import com.example.neosoftassignmentproject.viewModelFactory.UserViewmodelfactory
import com.example.neosoftassignmentproject.viewmodels.MyOrderListViewModel


class MyOrderFragment : Fragment(),MyOrderAdapter.onItemClick {
private lateinit var binding:FragmentMyOrderBinding
private lateinit var myOrderAdapter: MyOrderAdapter
private lateinit var viewModel: MyOrderListViewModel
private val api= Api.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentMyOrderBinding.inflate(inflater, container, false)
        viewModel=ViewModelProvider(requireActivity(), UserViewmodelfactory(UserRepository(api))).get(
            MyOrderListViewModel::class.java
        )
        myOrderAdapter= MyOrderAdapter(this)
        binding.rvMyOrder.addItemDecoration(
            DividerItemDecoration(
                binding.rvMyOrder.getContext(),
                DividerItemDecoration.VERTICAL
            )
        )
        binding.rvMyOrder.adapter=myOrderAdapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        UserPreferences(requireContext()).getAccessToken.asLiveData().observe(viewLifecycleOwner){
            viewModel.myOrder(it)

        }

        viewModel._myOrder.observe(requireActivity(), Observer {
            if (it != null && it.data != null) {
                myOrderAdapter.addOrder(it.data)
            }
        })
    }

    override fun itemClick(myOrderData: MyOrderData) {
        val action=MyOrderFragmentDirections.actionMyOrderFragmentToOrderDetailFragment(myOrderData.id)
        findNavController().navigate(action)
    }
}