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
import com.example.neosoftassignmentproject.databinding.FragmentChangePwdBinding
import com.example.neosoftassignmentproject.repository.UserRepository
import com.example.neosoftassignmentproject.viewModelFactory.UserViewmodelfactory
import com.example.neosoftassignmentproject.viewmodels.HomeViewModel


class ChangePwdFragment : Fragment() {
    private lateinit var binding:FragmentChangePwdBinding
    private lateinit var viewmodel:HomeViewModel
    private var api= Api.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentChangePwdBinding.inflate(inflater, container, false)
        viewmodel=ViewModelProvider(requireActivity(),UserViewmodelfactory(UserRepository(api))).get(HomeViewModel::class.java)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewmodel._changePwd.observe(requireActivity(), Observer {
            Toast.makeText(requireContext(), "${it.user_msg}", Toast.LENGTH_SHORT).show()
        })



      //btn click
        binding.resetPwdBtn.setOnClickListener {
        val oldPwd=    binding.oldPwdEdt.text.toString()
        val newPwd=    binding.newPwdEdt.text.toString()
        val confirmPwd=    binding.confirmPwdEdt.text.toString()
            UserPreferences(requireContext()).getAccessToken.asLiveData().observe(requireActivity()){
                viewmodel.changePwd(it,oldPwd,newPwd,confirmPwd)    // api call
            }
        }

    }


   private fun validatation(){

    }

}