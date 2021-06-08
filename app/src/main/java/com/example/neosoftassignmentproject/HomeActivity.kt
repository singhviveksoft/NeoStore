package com.example.neosoftassignmentproject

import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.neosoftassignmentproject.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var navController: NavController


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


    }
    override fun onSupportNavigateUp(): Boolean {    // to handle back btn
        return NavigationUI.navigateUp(navController,binding.drawerLayout)
    }


    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }
}