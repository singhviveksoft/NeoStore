package com.example.neosoftassignmentproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.example.neosoftassignmentproject.constants.UserPreferences
import com.example.neosoftassignmentproject.constants.interfaces.Api
import com.example.neosoftassignmentproject.constants.utils.ApiResult
import com.example.neosoftassignmentproject.databinding.FragmentChangePwdBinding
import com.example.neosoftassignmentproject.db.DataBase
import com.example.neosoftassignmentproject.repository.CategoryRepository
import com.example.neosoftassignmentproject.repository.UserRepository
import com.example.neosoftassignmentproject.viewModelFactory.CateyDBViewModelFactory
import com.example.neosoftassignmentproject.viewModelFactory.UserViewmodelfactory
import com.example.neosoftassignmentproject.viewmodels.HomeViewModel
import com.google.android.material.snackbar.Snackbar


class ChangePwdFragment : Fragment() {
    private lateinit var binding:FragmentChangePwdBinding
    private lateinit var viewmodel:HomeViewModel
    private var api= Api.getInstance()
    private lateinit var db: DataBase
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentChangePwdBinding.inflate(inflater, container, false)
        db=DataBase.getInstance(requireContext())
        viewmodel=ViewModelProvider(this, CateyDBViewModelFactory(CategoryRepository(api,db))).get(HomeViewModel::class.java)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewmodel._apiResult.observe(viewLifecycleOwner){
            when (it) {
                is ApiResult.Success -> {
                    Snackbar.make(
                        binding.root,
                        "${it.msg}",
                        Snackbar.LENGTH_SHORT
                    ).show()
                   // viewModel.isDataAvailable.value = null

                    //  binding.progressBar.isVisible = false
                }
                is ApiResult.Error -> {
                    Snackbar.make(
                        binding.root,
                        "${it.message}",
                        Snackbar.LENGTH_SHORT
                    ).show()
                    //  binding.progressBar.isVisible = false
                }
                is ApiResult.Loading -> {
                    Snackbar.make(
                        binding.root,
                        "Loading",
                        Snackbar.LENGTH_SHORT
                    ).show()
                    //  binding.progressBar.isVisible = false
                }

            }

        }



        viewmodel._changePwd.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireContext(), "${it.user_msg}", Toast.LENGTH_SHORT).show()
        })



      //btn click
        binding.resetPwdBtn.setOnClickListener {
        val oldPwd=    binding.oldPwdEdt.text.toString()
        val newPwd=    binding.newPwdEdt.text.toString()
        val confirmPwd=    binding.confirmPwdEdt.text.toString()
        if (!oldPwd.isNullOrBlank()&&!newPwd.isNullOrBlank()&&!confirmPwd.isNullOrBlank()&&newPwd.equals(confirmPwd))
            UserPreferences(requireContext()).getAccessToken.asLiveData().observe(requireActivity()){
                viewmodel.changePwd(it,oldPwd,newPwd,confirmPwd)    // api call
            }
            else
                Snackbar.make(binding.root,"enter your proper  password",Snackbar.LENGTH_SHORT).show()
        }

    }


   private fun validatation(){

    }

}