package com.example.neosoftassignmentproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.neosoftassignmentproject.adapter.OrderDetailAdapter
import com.example.neosoftassignmentproject.constants.UserPreferences
import com.example.neosoftassignmentproject.constants.interfaces.Api
import com.example.neosoftassignmentproject.databinding.FragmentOrderDetailBinding
import com.example.neosoftassignmentproject.repository.UserRepository
import com.example.neosoftassignmentproject.viewModelFactory.UserViewmodelfactory
import com.example.neosoftassignmentproject.viewmodels.OrderDetailViewModel

class OrderDetailFragment : Fragment() {
private val args:OrderDetailFragmentArgs by navArgs()
private lateinit var binding:FragmentOrderDetailBinding
private lateinit var viewModel: OrderDetailViewModel
private val api=Api.getInstance()
    private lateinit var myAdapter: OrderDetailAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentOrderDetailBinding.inflate(inflater,container,false)
        viewModel=ViewModelProvider(requireActivity(),UserViewmodelfactory(UserRepository(api))).get(OrderDetailViewModel::class.java)
        myAdapter=OrderDetailAdapter()
        binding.rvOrderDetail.adapter=myAdapter
        binding.rvOrderDetail.addItemDecoration(DividerItemDecoration(requireContext(),LinearLayoutManager.VERTICAL))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
     val order_id=   args.orderId
        UserPreferences(requireContext()).getAccessToken.asLiveData().observe(requireActivity()){
            viewModel.orderDetail(it,order_id)
        }

        viewModel._orderdetail.observe(requireActivity(), Observer {
        if (it!=null){
            myAdapter.addOrderDetail(it.data.order_details)
        }
        })
    }
}