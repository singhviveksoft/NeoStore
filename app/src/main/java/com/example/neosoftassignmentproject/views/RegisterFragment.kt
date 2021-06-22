package com.example.neosoftassignmentproject.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.neosoftassignmentproject.R
import com.example.neosoftassignmentproject.databinding.FragmentRegisterBinding
import com.example.neosoftassignmentproject.constants.interfaces.Api
import com.example.neosoftassignmentproject.constants.utils.ApiResult
import com.example.neosoftassignmentproject.constants.utils.Validation
import com.example.neosoftassignmentproject.constants.utils.Validation.Companion.isCharValid
import com.example.neosoftassignmentproject.constants.utils.Validation.Companion.isEmailId
import com.example.neosoftassignmentproject.constants.utils.Validation.Companion.isMobileValid
import com.example.neosoftassignmentproject.constants.utils.Validation.Companion.isValidPassword
import com.example.neosoftassignmentproject.repository.UserRepository
import com.example.neosoftassignmentproject.viewModelFactory.UserViewmodelfactory
import com.example.neosoftassignmentproject.viewmodels.RegisterViewmodel
import com.google.android.material.snackbar.Snackbar

class RegisterFragment : Fragment() {
private lateinit var binding:FragmentRegisterBinding
private lateinit var registerViewmodel: RegisterViewmodel
private val api:Api=Api.getInstance()
    private  var FLAG:Boolean=false
    var gender:String?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_register, container, false)
        registerViewmodel=ViewModelProvider(this,UserViewmodelfactory(
            UserRepository(api)
        )).get(RegisterViewmodel::class.java)
        binding.registerViewmodel=registerViewmodel
        binding.lifecycleOwner=this
            return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rgGender.setOnCheckedChangeListener { group, checkedId ->
         gender=    when(checkedId){
                R.id.rbMale->"Men"
                else->"Female"
            }

            registerViewmodel.gender.value=gender



        }
        registerViewmodel._apiResult.observe(viewLifecycleOwner, Observer {

            when (it) {
                is ApiResult.Success -> {
                    Snackbar.make(
                        binding.root,
                        "${it.msg}",
                        Snackbar.LENGTH_SHORT
                    ).show()

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

        })



        binding.regrBtn.setOnClickListener {
            if (validateUser()) {
//            if ( binding.checkbox.isChecked){
//                registerViewmodel.userRegister()
//         //  try {
//
//         //  }
                val mob = binding.mobEdt.text.toString().toLong()
                val phone = mob.toLong()
                //  Toast.makeText(requireContext(), "$p", Toast.LENGTH_SHORT).show()
                registerViewmodel.phoneNo.value = phone

                registerViewmodel.userRegister()

                //    }
            }
        }


        registerViewmodel._userRegr?.observe(viewLifecycleOwner, Observer {
            if (it==null){
                FLAG=false
            }else {
                FLAG = true
                findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
            }
        })
    }






 private   fun validateUser():Boolean{

        var flag1=binding.pwdEdttxt.text.toString()
        var flag2=binding.confPwdEdttxt.text.toString()

        //  if (!Validation.isCharValid(binding.fNameEditText))
        if (!binding.userFirstNameEdttxt.text.toString().isCharValid(binding.userFirstNameEdttxt.text.toString().trim()))
        {          binding.userFirstNameEdttxt.error="Enter more than 3 characters first name"
            //  return Validation.errormsg(requireContext(),"Enter more than 3 characters first name",binding.fNameEditText)
            return false
        }

        else if (!binding.userLastNameEdttxt.text.toString().isCharValid(binding.userLastNameEdttxt.text.toString().trim()))
        {
            binding.userLastNameEdttxt.error= "Enter more than 3 characters  last name"

            return false

        }


        else if (!binding.emailEdttxt.text.toString().isEmailId(binding.emailEdttxt.text.toString()))
        {
            binding.emailEdttxt.error="Enter your proper email id"
              return false

        }
        else  if (!binding.pwdEdttxt.text.toString().isValidPassword(binding.pwdEdttxt.text.toString()))
        {

            binding.pwdEdttxt.error="Enter a strong password of minimum 8 digit"
            return false
        }
        /* else  if (!Validation.isValidPassword(binding.confPwdEditText))
         {
             return Validation.errormsg(requireContext(),"Enter a strong password of minimum 8 digit",binding.confPwdEditText)
         }*/

        else  if (flag1!=flag2)
        {
            Toast.makeText(requireContext(), "Enter password does not match", Toast.LENGTH_SHORT).show()
            return false
        }
        else  if (!binding.mobEdt.text.toString().isMobileValid(binding.mobEdt.text.toString()))
        {
            binding.mobEdt.error="Enter valid mobile number"

            return false
        }
        else if (gender==null){
            Toast.makeText(requireContext(), "select your gender", Toast.LENGTH_SHORT).show()
            return false

        }
     else if (!binding.checkbox.isChecked){
            Toast.makeText(requireContext(), "please check terms and condition", Toast.LENGTH_SHORT).show()
            return false
     }

        return true

    }


}