package com.example.neosoftassignmentproject.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.neosoftassignmentproject.R
import com.example.neosoftassignmentproject.databinding.FragmentRegisterBinding
import com.example.neosoftassignmentproject.constants.interfaces.Api
import com.example.neosoftassignmentproject.repository.UserRepository
import com.example.neosoftassignmentproject.viewModelFactory.RegisterViewmodelfactory
import com.example.neosoftassignmentproject.viewmodels.RegisterViewmodel

class RegisterFragment : Fragment() {
private lateinit var binding:FragmentRegisterBinding
private lateinit var registerViewmodel: RegisterViewmodel
private val api:Api=Api.getInstance()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_register, container, false)
        registerViewmodel=ViewModelProvider(requireActivity(),RegisterViewmodelfactory(
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
                Toast.makeText(requireContext(), "$p", Toast.LENGTH_SHORT).show()
                registerViewmodel.phoneNo.value=q

                registerViewmodel.userRegister()

        //    }

        }
    }
}