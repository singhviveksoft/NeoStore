package com.example.neosoftassignmentproject

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.setPadding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.viewpager2.widget.ViewPager2
import com.example.neosoftassignmentproject.adapter.SlideImageAdapter
import com.example.neosoftassignmentproject.adapter.UserProductAdapter
import com.example.neosoftassignmentproject.constants.UserPreferences
import com.example.neosoftassignmentproject.constants.interfaces.Api
import com.example.neosoftassignmentproject.constants.utils.InternetConnection

import com.example.neosoftassignmentproject.databinding.FragmentHomeBinding
import com.example.neosoftassignmentproject.model.ProductCategory
import com.example.neosoftassignmentproject.repository.UserRepository
import com.example.neosoftassignmentproject.viewModelFactory.UserViewmodelfactory
import com.example.neosoftassignmentproject.viewmodels.HomeViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_home_screen.*
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment(),UserProductAdapter.clickItem{
    private lateinit var binding:FragmentHomeBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var slideImageAdapter: SlideImageAdapter
 private   val imageArrayList=ArrayList<Drawable>()
    private val indicatorsArraylist=ArrayList<TextView>()
    private val api= Api.getInstance()
    private val arrayList=ArrayList<ProductCategory>()

private lateinit var viewModel: HomeViewModel
private lateinit var userProductAdapter: UserProductAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel=ViewModelProvider(this,UserViewmodelfactory(UserRepository(api))).get(HomeViewModel::class.java)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentHomeBinding.inflate(inflater, container, false)
        slideImageAdapter= SlideImageAdapter()
        userProductAdapter= UserProductAdapter(this)


        binding.rvProduct.adapter=userProductAdapter
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




        imageArrayList.add(requireContext().resources.getDrawable(R.drawable.slider_img1))
        imageArrayList.add(requireContext().resources.getDrawable(R.drawable.slider_img2))
        imageArrayList.add(requireContext().resources.getDrawable(R.drawable.slider_img3))
        imageArrayList.add(requireContext().resources.getDrawable(R.drawable.slider_img4))
        addIndicator()

        UserPreferences(requireContext()).getAccessToken.asLiveData().observe(viewLifecycleOwner){

            viewModel.getProduct(it)   // api call

        }
//observer

      viewModel.getProduct.observe(viewLifecycleOwner, Observer {
            arrayList.clear()

           // if (it){
            arrayList.addAll(it.data.product_categories)
           // for (i in arrayList.indices){
                userProductAdapter.addProduct(arrayList)
      //  Toast.makeText(requireContext(), "yyyy", Toast.LENGTH_SHORT).show()
           // }
        })



        slideImageAdapter.addImages(imageArrayList)
        binding.viewPager.adapter=slideImageAdapter
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {


            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                for (i in imageArrayList.indices) {
                    if (i == position) {
                        indicatorsArraylist[i].setTextColor(resources.getColor(R.color.grey))
                    } else {
                        indicatorsArraylist[i].setTextColor(resources.getColor(R.color.black))
                    }
                }
            }
        })


/*
        InternetConnection(requireContext()).observe(viewLifecycleOwner, Observer {isNetworkAvailable ->
            Snackbar.make(
                binding.root,
                "${isNetworkAvailable.toString()}",
                Snackbar.LENGTH_LONG
            ).show()

        })
*/



    }

    fun addIndicator(){
        for (i in imageArrayList.indices){
            indicatorsArraylist.add(TextView(requireContext()))
            indicatorsArraylist[i].text= Html.fromHtml("&#9679;")
            indicatorsArraylist[i].setPadding(2)
            indicatorsArraylist[i].setTextSize(18F)
            indicatorsArraylist[i].setTextColor(resources.getColor(R.color.black))
                if (indicatorsArraylist.size>=5) {
                    binding.lnr.removeView(indicatorsArraylist[i])

                }
            else{
                    binding.lnr.addView(indicatorsArraylist[i])

                }


        }
    }

    override fun onClick(productCategory: ProductCategory) {

        val product_category_id=productCategory.id.toString()
        val name=productCategory.name.toString()

        val action=HomeFragmentDirections.actionHomeFragmentToProductListFragment(product_category_id,name)


        findNavController().navigate(action)




        // Toast.makeText(requireContext(), "${product_id.toString()}", Toast.LENGTH_SHORT).show()
    }


    override fun onDestroy() {
        super.onDestroy()

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
      //  Toast.makeText(requireContext(), "onActivityCreated", Toast.LENGTH_SHORT).show()

    }

    override fun onDestroyView() {
        super.onDestroyView()
      //  viewModel.getProduct.removeObservers(requireActivity())


    }

    override fun onPause() {
        super.onPause()
        imageArrayList.clear()
        indicatorsArraylist.clear()
      //  Toast.makeText(requireContext(), "onPause", Toast.LENGTH_SHORT).show()
    }
}