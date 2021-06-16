package com.example.neosoftassignmentproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.neosoftassignmentproject.constants.UserPreferences
import com.example.neosoftassignmentproject.databinding.FragmentLogoutDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class LogoutDialogFragment : BottomSheetDialogFragment() {
private lateinit var binding:FragmentLogoutDialogBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentLogoutDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.yesBtn.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                UserPreferences(requireContext()).clearData()
               // findNavController().navigate(R.id.action_logoutDialogFragment_to_loginFragment2)
                activity?.finish()
            }
        }

    }
}