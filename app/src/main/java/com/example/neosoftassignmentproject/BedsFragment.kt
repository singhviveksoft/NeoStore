package com.example.neosoftassignmentproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.neosoftassignmentproject.adapter.ProductListAdapter
import com.example.neosoftassignmentproject.constants.interfaces.Api
import com.example.neosoftassignmentproject.databinding.FragmentBedsBinding
import com.example.neosoftassignmentproject.model.ProductListData
import com.example.neosoftassignmentproject.repository.UserRepository
import com.example.neosoftassignmentproject.viewModelFactory.UserViewmodelfactory
import com.example.neosoftassignmentproject.viewmodels.ProductListViewModel


class BedsFragment : Fragment() ,ProductListAdapter.clickItem {
private lateinit var binding:FragmentBedsBinding
    private lateinit var viewModel: ProductListViewModel
    private val api= Api.getInstance()
    private lateinit var adapter: ProductListAdapter
    private val arrayList=ArrayList<ProductListData>()
    private var name:String?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentBedsBinding.inflate(inflater, container, false)

        viewModel= ViewModelProvider(requireActivity(), UserViewmodelfactory(UserRepository(api))).get(
            ProductListViewModel::class.java)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getProductList("4")

        adapter= ProductListAdapter(this)
        binding.rvBeds.adapter=adapter
        viewModel.ProductList.observe(viewLifecycleOwner, Observer {
            arrayList.clear()
            arrayList.addAll(it.data)
            adapter.addProduct(arrayList)
        })



    }

    override fun onClick(productlist: ProductListData) {

        val productId=productlist.id.toString()
     val action=   BedsFragmentDirections.actionBedsFragmentToProductDetailFragment(productId,"Beds")
        findNavController().navigate(action)
       // Toast.makeText(requireContext(), "onViewCreated", Toast.LENGTH_SHORT).show()

    }
}