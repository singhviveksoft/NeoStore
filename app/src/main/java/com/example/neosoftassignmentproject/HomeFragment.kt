package com.example.neosoftassignmentproject

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.setPadding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.viewpager2.widget.ViewPager2
import com.example.neosoftassignmentproject.adapter.SlideImageAdapter
import com.example.neosoftassignmentproject.adapter.UserProductAdapter
import com.example.neosoftassignmentproject.constants.UserPreferences
import com.example.neosoftassignmentproject.constants.interfaces.Api

import com.example.neosoftassignmentproject.databinding.FragmentHomeBinding
import com.example.neosoftassignmentproject.repository.UserRepository
import com.example.neosoftassignmentproject.viewModelFactory.RegisterViewmodelfactory
import com.example.neosoftassignmentproject.viewmodels.HomeViewModel
import androidx.navigation.fragment.NavHostFragment.findNavController as findNavController1


class HomeFragment : Fragment() {
    private lateinit var binding:FragmentHomeBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var slideImageAdapter: SlideImageAdapter
 private   val imageArrayList=ArrayList<Drawable>()
    private val indicatorsArraylist=ArrayList<TextView>()
    private val api= Api.getInstance()


private lateinit var viewModel: HomeViewModel
private lateinit var userProductAdapter: UserProductAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentHomeBinding.inflate(inflater, container, false)
        slideImageAdapter= SlideImageAdapter()
        userProductAdapter= UserProductAdapter()
        viewModel=ViewModelProvider(requireActivity(),RegisterViewmodelfactory(UserRepository(api))).get(HomeViewModel::class.java)


        binding.rvProduct.adapter=userProductAdapter
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imageArrayList.add(requireContext().resources.getDrawable(R.drawable.slider_img1))
        imageArrayList.add(requireContext().resources.getDrawable(R.drawable.slider_img2))
        imageArrayList.add(requireContext().resources.getDrawable(R.drawable.slider_img3))
        imageArrayList.add(requireContext().resources.getDrawable(R.drawable.slider_img4))
      //  addIndicator()

        UserPreferences(requireContext()).getUserId.asLiveData().observe(viewLifecycleOwner){
            viewModel.getProduct(it)

        }

        viewModel.getProduct.observe(viewLifecycleOwner, Observer {
           // if (it){
                userProductAdapter.addProduct(it)
          //  }
        })



        slideImageAdapter.addImages(imageArrayList)
        binding.viewPager.adapter=slideImageAdapter
        binding.viewPager.registerOnPageChangeCallback(object :ViewPager2.OnPageChangeCallback(){


            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
            }
        })



        binding.tableImg.setOnClickListener {
        val action=HomeFragmentDirections.actionHomeFragmentToProductListFragment("table")


           findNavController().navigate(action)
        }


    }

    fun addIndicator(){
        for (i in imageArrayList.indices){
            indicatorsArraylist.add(TextView(requireContext()))
            indicatorsArraylist[i].text= Html.fromHtml("&#9679;")
            indicatorsArraylist[i].setPadding(2)
            indicatorsArraylist[i].setTextSize(18F)
            indicatorsArraylist[i].setTextColor(resources.getColor(R.color.black))
            binding.linearLayout3.addView(indicatorsArraylist[i])
        }
    }
}