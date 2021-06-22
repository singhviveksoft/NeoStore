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
import com.example.neosoftassignmentproject.HomeScreenActivity
import com.example.neosoftassignmentproject.R
import com.example.neosoftassignmentproject.constants.UserPreferences
import com.example.neosoftassignmentproject.constants.interfaces.Api
import com.example.neosoftassignmentproject.constants.utils.ApiResult
import com.example.neosoftassignmentproject.constants.utils.Validation.Companion.isEmailId
import com.example.neosoftassignmentproject.constants.utils.Validation.Companion.isValidPassword
import com.example.neosoftassignmentproject.databinding.FragmentLoginBinding
import com.example.neosoftassignmentproject.repository.UserRepository
import com.example.neosoftassignmentproject.viewModelFactory.UserViewmodelfactory
import com.example.neosoftassignmentproject.viewmodels.LoginViewmodel
import com.google.android.material.snackbar.Snackbar
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

        loginViewmodel=ViewModelProvider(this,UserViewmodelfactory(UserRepository(api))).get(LoginViewmodel::class.java)

        loginBinding.loginViewModel=loginViewmodel
        return loginBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginViewmodel._apiResult.observe(viewLifecycleOwner, Observer {

            when (it) {
                is ApiResult.Success -> {
                    Snackbar.make(
                        loginBinding.root,
                        "${it.msg}",
                        Snackbar.LENGTH_SHORT
                    ).show()

                    //  binding.progressBar.isVisible = false
                }
                is ApiResult.Error -> {
                    Snackbar.make(
                        loginBinding.root,
                        "${it.message}",
                        Snackbar.LENGTH_SHORT
                    ).show()
                    //  binding.progressBar.isVisible = false
                }
                is ApiResult.Loading -> {
                    Snackbar.make(
                        loginBinding.root,
                        "Loading",
                        Snackbar.LENGTH_SHORT
                    ).show()
                    //  binding.progressBar.isVisible = false
                }

            }

        })



            loginViewmodel._userLogin?.observe(viewLifecycleOwner, Observer {
            if (it==null){
                FLAG=false
            }else{
                FLAG=true
                storeUserId(it.data.access_token)
             //   findNavController().navigate(R.id.action_loginFragment_to_homeFragment)

              val intent=Intent(requireContext(), HomeScreenActivity::class.java)
                startActivity(intent)
                activity?.finish()

            }
        })


        loginBinding.forgotPwdTxt.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_forgotPasswordFragment)
        }


        loginBinding.addAccountImg.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
        loginBinding.button.setOnClickListener {



        if (userInput()) {


            loginViewmodel.userLogin()


        }

        }
    }



    private fun storeUserId(aceess_token:String){
        userPreferences= UserPreferences(requireContext())
        lifecycleScope.launch {
            userPreferences.setAccesToken(aceess_token)
        }
    }


    private fun userInput():Boolean{
         if (!loginBinding.emailEdttxt.text.toString().isEmailId(loginBinding.emailEdttxt.text.toString()))
        {
            loginBinding.emailEdttxt.error="Enter your proper email id"
            return false

        }
         else  if (!loginBinding.pwdEdttxt.text.toString().isValidPassword(loginBinding.pwdEdttxt.text.toString()))
         {

             loginBinding.pwdEdttxt.error="Enter a strong password of minimum 8 digit"
             return false
         }
        return true
    }
}