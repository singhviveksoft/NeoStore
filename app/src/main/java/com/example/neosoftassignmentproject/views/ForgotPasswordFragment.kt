package com.example.neosoftassignmentproject.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.neosoftassignmentproject.R
import com.example.neosoftassignmentproject.constants.interfaces.Api
import com.example.neosoftassignmentproject.constants.utils.ApiResult
import com.example.neosoftassignmentproject.constants.utils.Validation.Companion.isEmailId
import com.example.neosoftassignmentproject.databinding.FragmentForgotPasswordBinding
import com.example.neosoftassignmentproject.repository.UserRepository
import com.example.neosoftassignmentproject.viewModelFactory.UserViewmodelfactory
import com.example.neosoftassignmentproject.viewmodels.ForgotPasswordViewmodel
import com.example.neosoftassignmentproject.viewmodels.LoginViewmodel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_forgot_password.*

class ForgotPasswordFragment : Fragment() {
    private lateinit var binding:FragmentForgotPasswordBinding
    private lateinit var ViewModel: ForgotPasswordViewmodel
    private val api: Api = Api.getInstance()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentForgotPasswordBinding.inflate(inflater, container, false)
        ViewModel=
            ViewModelProvider(requireActivity(), UserViewmodelfactory(UserRepository(api))).get(ForgotPasswordViewmodel::class.java)
        binding.viewModel=ViewModel
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        ViewModel._apiResult.observe(viewLifecycleOwner, Observer {

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


        ViewModel._forgotPwd.observe(requireActivity(), Observer {
          Toast.makeText(requireContext(), "${it.user_msg}", Toast.LENGTH_SHORT).show()
      })

        button.setOnClickListener {
            if (userInput()) {
                ViewModel.forgotPwd()
            }
        }
    }

    private fun userInput():Boolean {
        if (!binding.emailEdttxt.text.toString()
                .isEmailId(binding.emailEdttxt.text.toString())
        ) {
            binding.emailEdttxt.error = "Enter your proper email id"
            return false

        }
        return true
    }
}