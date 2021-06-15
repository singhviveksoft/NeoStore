package com.example.neosoftassignmentproject.views

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.neosoftassignmentproject.HomeActivity
import com.example.neosoftassignmentproject.R
import com.example.neosoftassignmentproject.constants.UserPreferences
import com.example.neosoftassignmentproject.constants.interfaces.Api
import com.example.neosoftassignmentproject.databinding.FragmentLoginBinding
import com.example.neosoftassignmentproject.repository.UserRepository
import com.example.neosoftassignmentproject.viewModelFactory.UserViewmodelfactory
import com.example.neosoftassignmentproject.viewmodels.LoginViewmodel
import kotlinx.coroutines.launch


class LoginFragment : Fragment() {
    private lateinit var loginBinding: FragmentLoginBinding
    private lateinit var loginViewmodel: LoginViewmodel
    private lateinit var userPreferences: UserPreferences
    private val api: Api = Api.getInstance()
    private  var FLAG:Boolean=false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        loginBinding=DataBindingUtil.inflate(inflater,R.layout.fragment_login,container,false)

        loginViewmodel=ViewModelProvider(requireActivity(),UserViewmodelfactory(UserRepository(api))).get(LoginViewmodel::class.java)

        loginBinding.loginViewModel=loginViewmodel
        return loginBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


            loginViewmodel._userLogin?.observe(requireActivity(), Observer {
            if (it==null){
                FLAG=false
            }else{
                FLAG=true
                storeUserId(it.data.access_token)
             //   findNavController().navigate(R.id.action_loginFragment_to_homeFragment)

              val intent=Intent(requireContext(),HomeActivity::class.java)
                startActivity(intent)

            }
        })





        loginBinding.addAccountImg.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
        loginBinding.button.setOnClickListener {






                loginViewmodel.userLogin()




        }
    }



    private fun storeUserId(aceess_token:String){
        userPreferences= UserPreferences(requireContext())
        lifecycleScope.launch {
            userPreferences.setAccesToken(aceess_token)
        }
    }
}