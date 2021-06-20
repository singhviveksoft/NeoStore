package com.example.neosoftassignmentproject

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.neosoftassignmentproject.constants.UserPreferences
import com.example.neosoftassignmentproject.constants.interfaces.Api
import com.example.neosoftassignmentproject.constants.utils.ApiResponse
import com.example.neosoftassignmentproject.constants.utils.ApiResult
import com.example.neosoftassignmentproject.databinding.FragmentProductDetailBinding
import com.example.neosoftassignmentproject.databinding.RatingBarPopupBinding
import com.example.neosoftassignmentproject.model.ProductDetailData
import com.example.neosoftassignmentproject.repository.UserRepository
import com.example.neosoftassignmentproject.viewModelFactory.UserViewmodelfactory
import com.example.neosoftassignmentproject.viewmodels.LoginViewmodel
import com.example.neosoftassignmentproject.viewmodels.ProductDetailViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.cart_quatity_popup.*
import kotlinx.android.synthetic.main.rating_bar_popup.*
import kotlinx.coroutines.launch


class ProductDetailFragment : Fragment() {
  val args:ProductDetailFragmentArgs by navArgs()
  //  val categoryName:ProductDetailFragmentArgs by navArgs()
    private lateinit var binding:FragmentProductDetailBinding
    private lateinit var viewModel: ProductDetailViewModel
    private var productId:String?=null
    private var categoryName:String?=null
    private val api= Api.getInstance()
    private lateinit var model:ProductDetailData
    var image:String?=null
    var name:String?=null
    var check:Boolean?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel=ViewModelProvider(this,UserViewmodelfactory(UserRepository(api))).get(
            ProductDetailViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentProductDetailBinding.inflate(inflater, container, false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       productId=  args.productId
       categoryName=  args.name
        viewModel.product_id.value=productId
     // api call
       viewModel.getProductDetail(productId!!)

     // observer
        viewModel.productDetail.observe(viewLifecycleOwner, Observer {
             name=it.data.name
            val producer=it.data.producer
            val description=it.data.description
            val rating=it.data.rating
            val price= it.data.cost
             image=it.data.product_images[0].image

            binding.productNameTxt.text=name
            binding.productProducerTxt.text=producer
            binding.productCategoryNameTxt.text=categoryName
            binding.descriptionTxt.text=description
            binding.ratingBar.rating=rating.toFloat()
            binding.prductImage.LoadImg(image!!)
            binding.productPriceText.text="Rs. "+price.toString()
        })


        binding.rateBtn.setOnClickListener {
            ratingPopUp(productId!!)
        }

        binding.buyNowBtn.setOnClickListener {
            cartPopUp(productId!!)
        }

     //   viewModel._rating.removeObservers(this)



      //  lifecycleScope.launch {
          viewModel._addToCart.observe(viewLifecycleOwner, Observer {
              if (it!=null){
                  viewModel._apiResult.observe(viewLifecycleOwner, Observer {

                      when(it){
                          is ApiResult.Success->{
                              Snackbar.make(
                                  binding.constraintLayout,
                                  "${it.msg}",
                                  Snackbar.LENGTH_LONG
                              ).show()
                              viewModel.oneTimeCall.value==true
                              //  binding.progressBar.isVisible = false
                          }
                          is ApiResult.Error->{
                              Snackbar.make(
                                  binding.constraintLayout,
                                  "${it.message}",
                                  Snackbar.LENGTH_LONG
                              ).show()
                              viewModel.oneTimeCall.value==true
                              //  binding.progressBar.isVisible = false
                          }
                          is ApiResult.Loading->{
                              Snackbar.make(
                                  binding.constraintLayout,
                                  "Loading",
                                  Snackbar.LENGTH_LONG
                              ).show()
                              viewModel.oneTimeCall.value==true
                              //  binding.progressBar.isVisible = false
                          }

                      }

                  })

              }
          })
    //    }





        viewModel._rating.observe(viewLifecycleOwner, Observer {
        if (it != null) {
            viewModel._apiResult.observe(viewLifecycleOwner, Observer {

                 when(it){
                    is ApiResult.Success->{
                        Snackbar.make(
                            binding.constraintLayout,
                            "${it.msg}",
                            Snackbar.LENGTH_LONG
                        ).show()
                        viewModel.oneTimeCall.value==true
                        //  binding.progressBar.isVisible = false
                    }
                    is ApiResult.Error->{
                        Snackbar.make(
                            binding.constraintLayout,
                            "${it.message}",
                            Snackbar.LENGTH_LONG
                        ).show()
                        viewModel.oneTimeCall.value==true
                        //  binding.progressBar.isVisible = false
                    }
                    is ApiResult.Loading->{
                        Snackbar.make(
                            binding.constraintLayout,
                            "Loading",
                            Snackbar.LENGTH_LONG
                        ).show()
                        viewModel.oneTimeCall.value==true
                        //  binding.progressBar.isVisible = false
                    }

                }


            })

        }


    })


}





    override fun onResume() {
        super.onResume()

    }

    private fun ratingPopUp(product_id:String){
        val ratingBarPopUp=layoutInflater.inflate(R.layout.rating_bar_popup,null)
        val customDialog = AlertDialog.Builder(requireContext())
            .setView(ratingBarPopUp)
            .show()
        customDialog.rating_productImage.LoadImg(image!!)
        val btn=customDialog.button2
       val ratingBar=customDialog.ratingBar
        customDialog.productNameText.text=name
        btn.setOnClickListener {

            val rating=ratingBar.rating
            viewModel.addRating(product_id,rating)

            customDialog.dismiss()
            //  Toast.makeText(requireContext(), "$rating", Toast.LENGTH_SHORT).show()
        }


    }



    private fun showDialog() {

    }







    private fun cartPopUp(product_id:String){
       /* val Dialog = Dialog(requireActivity())
        Dialog.window?.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        Dialog.window?.setBackgroundDrawable(ColorDrawable.)



        Dialog.setContentView(R.layout.cart_quatity_popup)


        Dialog.show()*/

        val cartPopUp=layoutInflater.inflate(R.layout.cart_quatity_popup,null)
        val customDialog=AlertDialog.Builder(requireContext())
            .setView(cartPopUp)
            .show()
      //  customDialog.cardView_cart_pop_up.cardBackgroundColor=Color.TRANSPARENT
        customDialog.ProductNameText.text=name

        customDialog.productImage.LoadImg(image!!)
         val btn=  customDialog.submitBtn
        btn.setOnClickListener {
          val quatity=  customDialog.quatity_editText.text.toString()
          if (!quatity.isNullOrBlank()) {
              val _quatity = quatity.toInt()


              UserPreferences(requireContext()).getAccessToken.asLiveData()
                  .observe(viewLifecycleOwner) {
                      viewModel.addToCart(it, product_id.toInt(), _quatity)
                      customDialog.dismiss()
                  }


          }






            // customDialog.dismiss()
        }
    }


    private fun acessToken(){
    }


  /*  override fun onPause() {
        super.onPause()
        binding.root==null

    }*/
}