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
import com.example.neosoftassignmentproject.model.ProductListData
import com.example.neosoftassignmentproject.repository.UserRepository
import com.example.neosoftassignmentproject.viewModelFactory.UserViewmodelfactory
import com.example.neosoftassignmentproject.viewmodels.ProductListViewModel
import kotlinx.android.synthetic.main.fragment_beds.*
import kotlinx.android.synthetic.main.fragment_table.*

class TableFragment : Fragment(),ProductListAdapter.clickItem  {
var userName:String?=null
    private lateinit var viewModel: ProductListViewModel
    private val api= Api.getInstance()
    private lateinit var adapter: ProductListAdapter
    private val arrayList=ArrayList<ProductListData>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_table, container, false)
        viewModel= ViewModelProvider(requireActivity(), UserViewmodelfactory(UserRepository(api))).get(
            ProductListViewModel::class.java)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getProductList("1")

        adapter= ProductListAdapter(this)
        rv_table.adapter=adapter
        viewModel.ProductList.observe(viewLifecycleOwner, Observer {
            arrayList.clear()
            arrayList.addAll(it.data)
            adapter.addProduct(arrayList)
        })

    }
    override fun onClick(productlist: ProductListData) {

        val productId=productlist.id.toString()
        val action=  TableFragmentDirections.actionTableFragmentToProductDetailFragment(productId,"Table")
        findNavController().navigate(action)
        // Toast.makeText(requireContext(), "onViewCreated", Toast.LENGTH_SHORT).show()

    }

}