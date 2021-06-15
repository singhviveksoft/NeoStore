package com.example.neosoftassignmentproject

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.neosoftassignmentproject.databinding.ActivityHomeBinding
import com.example.neosoftassignmentproject.viewmodels.HomeViewModel

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var navController: NavController
    private val viewModel: HomeViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(findViewById(R.id.toolbar))
        val navHostFragment =

            supportFragmentManager.findFragmentById(R.id.fragment2) as NavHostFragment
        navController = navHostFragment.navController
        NavigationUI.setupWithNavController(binding.navigationView,navController)    // setting navigation of menu item
        NavigationUI.setupActionBarWithNavController(this,navController,binding.drawerLayout)


     val headerText=   binding.navigationView.getHeaderView(0)
        val hView =  binding.navigationView.getHeaderView(0)
        val textViewName = hView.findViewById(R.id.textView2) as TextView
        val textViewEmail = hView.findViewById(R.id.textView7) as TextView
        val imgvw = hView.findViewById(R.id.imageView) as ImageView
        imgvw.setImageResource(R.drawable.user_icon)
        viewModel.getProduct.observe(this, Observer {
         val email=   it.data.user_data.email
         val name=   it.data.user_data.first_name.plus(it.data.user_data.last_name)
            textViewEmail.text=email.toString()
            textViewName.text=name.toString()
        })

    }
    override fun onSupportNavigateUp(): Boolean {    // to handle back btn
        return NavigationUI.navigateUp(navController,binding.drawerLayout)
    }

}

/*
    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }
*/
