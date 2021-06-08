package com.example.neosoftassignmentproject.views

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.example.neosoftassignmentproject.HomeActivity
import com.example.neosoftassignmentproject.R
import com.example.neosoftassignmentproject.constants.UserPreferences


class SplashFragment : Fragment() {
    private lateinit var userPreferences: UserPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_splash, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        UserPreferences(requireContext()).getUserId.asLiveData().observe(viewLifecycleOwner) {
            Handler().postDelayed({
                if (it == "0") {
                    findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
                } else {
                    val intent = Intent(requireContext(), HomeActivity::class.java)
                    startActivity(intent)
                }


            }, 3000)

        }
    }
}
