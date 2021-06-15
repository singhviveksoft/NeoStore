package com.example.neosoftassignmentproject

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.example.neosoftassignmentproject.adapter.MyCartAdapter
import com.example.neosoftassignmentproject.constants.UserPreferences
import com.example.neosoftassignmentproject.constants.interfaces.Api
import com.example.neosoftassignmentproject.databinding.FragmentMyCartBinding
import com.example.neosoftassignmentproject.model.CartData
import com.example.neosoftassignmentproject.model.Product
import com.example.neosoftassignmentproject.repository.UserRepository
import com.example.neosoftassignmentproject.viewModelFactory.UserViewmodelfactory
import com.example.neosoftassignmentproject.viewmodels.MyCartViewModel
import kotlinx.coroutines.*


class MyCartFragment : Fragment(),MyCartAdapter.clickItem {
private lateinit var binding: FragmentMyCartBinding
private lateinit var viewModel: MyCartViewModel
private lateinit var myAdapter:MyCartAdapter
var access_Token:String="0"
var arrayList=ArrayList<Product>()
    var emptylist= emptyList<CartData>()
private val api= Api.getInstance()




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentMyCartBinding.inflate(inflater, container, false)
        viewModel=ViewModelProvider(requireActivity(),UserViewmodelfactory(UserRepository(api))).get(MyCartViewModel::class.java)

        myAdapter=MyCartAdapter(this)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.mycartRv.adapter=myAdapter

        UserPreferences(requireContext()).getAccessToken.asLiveData().observe(viewLifecycleOwner){
           //api call
            access_Token=it
            viewModel.accessToken.value=access_Token
            viewModel.getMyCart(access_Token)

        }




        viewModel._cart.observe(viewLifecycleOwner, Observer {
          if (it!=null&&it.data!==null) {
              arrayList.clear()
             val  list = it.data
              val total = it.total.toString()
              binding.totalAmtTxt.text = total

              for (item in list) {
                  arrayList.add(item.product)

              }
              myAdapter.addProduct(arrayList, list)
          }else{
              arrayList.clear()

              myAdapter.addProduct(arrayList, emptylist)
              binding.totalAmtTxt.text="0.0"

          }

        })




     binding.orderBtn.setOnClickListener {
         findNavController().navigate(R.id.action_myCartFragment_to_addAddressFragment)
     }



      // live data




        //live data observe for delete status
/*
        viewModel._iteamDelete.observe(viewLifecycleOwner, Observer {
            if (it != null) {
              //  viewModel.getMyCart(accessToken)

                Toast.makeText(requireContext(), "${it.user_msg.toString()}", Toast.LENGTH_SHORT).show()
            }
        })
*/

/*
        viewModel._changeQuantity.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                viewModel.getMyCart(access_Token)
            }
        })
*/


    }






    override fun onClick(mycartList: CartData,status:String,quantity:Number) {
      if (status.equals("delete")) {
          deleteItem(mycartList.product_id)
     //     viewModel.getMyCart(it)

      }else{
          viewModel.changeQuantity(access_Token,mycartList.product_id,quantity)

      //   UserPreferences(requireContext()).getAccessToken.asLiveData().observe(viewLifecycleOwner){
/*
       CoroutineScope(Dispatchers.IO).launch {


     val job1=   async {
            viewModel.getMyCart(accessToken)

        }
    val job2=    async {
            viewModel.changeQuantity(accessToken,mycartList.product_id,quantity)

        }
    job1.await()
    job2.await()


        //  }




}
*/
      }
    }


    private fun deleteItem(productId: Int) {
        val dialog=AlertDialog.Builder(requireContext())
        dialog.setTitle("Delete Item")
        dialog.setMessage("Are you sure you want to delete your product from my cart")
        dialog.setPositiveButton("YES"){dialog, which->
            //api call
            UserPreferences(requireContext()).getAccessToken.asLiveData().observe(viewLifecycleOwner){
            viewModel.deleteCartItem(it,productId)
            }

        }
        dialog.setNegativeButton("No"){dialog,which->
            dialog.dismiss()
        }

        dialog.show()
    }


    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(requireContext(), "onDestroy", Toast.LENGTH_SHORT).show()
    }
}