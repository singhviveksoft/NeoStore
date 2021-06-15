package com.example.neosoftassignmentproject.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.neosoftassignmentproject.R
import com.example.neosoftassignmentproject.databinding.FragmentRegisterBinding
import com.example.neosoftassignmentproject.constants.interfaces.Api
import com.example.neosoftassignmentproject.repository.UserRepository
import com.example.neosoftassignmentproject.viewModelFactory.UserViewmodelfactory
import com.example.neosoftassignmentproject.viewmodels.RegisterViewmodel

class RegisterFragment : Fragment() {
private lateinit var binding:FragmentRegisterBinding
private lateinit var registerViewmodel: RegisterViewmodel
private val api:Api=Api.getInstance()
    private  var FLAG:Boolean=false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_register, container, false)
        registerViewmodel=ViewModelProvider(requireActivity(),UserViewmodelfactory(
            UserRepository(api)
        )).get(RegisterViewmodel::class.java)
        binding.registerViewmodel=registerViewmodel
        binding.lifecycleOwner=this
            return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rgGender.setOnCheckedChangeListener { group, checkedId ->
        val gender=    when(checkedId){
                R.id.rbMale->"Men"
                else->"Female"
            }

            registerViewmodel.gender.value=gender



        }


        binding.regrBtn.setOnClickListener {
//            if ( binding.checkbox.isChecked){
//                registerViewmodel.userRegister()
//         //  try {
//
//         //  }
            val p=   binding.mobEdt.text.toString().toLong()
              val q=  p.toLong()
              //  Toast.makeText(requireContext(), "$p", Toast.LENGTH_SHORT).show()
                registerViewmodel.phoneNo.value=q

                registerViewmodel.userRegister()

        //    }

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
}