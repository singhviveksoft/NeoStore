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
import androidx.navigation.fragment.navArgs
import com.example.neosoftassignmentproject.adapter.ProductListAdapter
import com.example.neosoftassignmentproject.constants.interfaces.Api
import com.example.neosoftassignmentproject.databinding.FragmentProductListBinding
import com.example.neosoftassignmentproject.model.ProductCategory
import com.example.neosoftassignmentproject.model.ProductListData
import com.example.neosoftassignmentproject.repository.UserRepository
import com.example.neosoftassignmentproject.viewModelFactory.UserViewmodelfactory
import com.example.neosoftassignmentproject.viewmodels.ProductListViewModel


class ProductListFragment : Fragment(),ProductListAdapter.clickItem {


private lateinit var binding: FragmentProductListBinding
    val args:ProductListFragmentArgs by navArgs()
    private lateinit var viewModel: ProductListViewModel
    private val api=Api.getInstance()
    private lateinit var adapter:ProductListAdapter
    private val arrayList=ArrayList<ProductListData>()
    private var name:String?=null

 companion object{
     var prod_catgry_id="0"
     var prod_catg_name="0"

     fun Prod_catgry_id(_prod_catgry_id:String,_prod_catg_name:String){
         prod_catgry_id =_prod_catgry_id
         prod_catg_name =_prod_catg_name

     }

 }





    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentProductListBinding.inflate(inflater, container, false)
        viewModel=ViewModelProvider(requireActivity(),UserViewmodelfactory(UserRepository(api))).get(ProductListViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       val product_category_id=args.productCategoryId
        name=args.name
        viewModel.getProductList(product_category_id)
      //  Toast.makeText(requireContext(), "$product_id", Toast.LENGTH_SHORT).show()
      /* if (prod_catgry_id=="0"&&prod_catg_name=="0") {
           viewModel.getProductList(product_category_id)
       }
        else{
           viewModel.getProductList(prod_catgry_id)
           name=prod_catg_name

       }*/

        adapter= ProductListAdapter(this)
        binding.rvProduct.adapter=adapter
        viewModel.ProductList.observe(viewLifecycleOwner, Observer {
          arrayList.clear()
            arrayList.addAll(it.data)
            adapter.addProduct(arrayList)
        })
      //  Toast.makeText(requireContext(), "onViewCreated", Toast.LENGTH_SHORT).show()


    }


    override fun onResume() {
        super.onResume()
     //   Toast.makeText(requireContext(), "onResume", Toast.LENGTH_SHORT).show()
    }

    override fun onClick(productlist: ProductListData) {
   val productId=productlist.id.toString()
    val action=ProductListFragmentDirections.actionProductListFragmentToProductDetailFragment(productId,name!!)
  findNavController().navigate(action)
}

}
