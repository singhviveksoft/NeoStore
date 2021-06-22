package com.example.neosoftassignmentproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import coil.load
import coil.transform.CircleCropTransformation
import com.example.neosoftassignmentproject.constants.interfaces.Api
import com.example.neosoftassignmentproject.constants.utils.InternetConnection
import com.example.neosoftassignmentproject.db.DataBase
import com.example.neosoftassignmentproject.repository.CategoryRepository
import com.example.neosoftassignmentproject.repository.UserRepository
import com.example.neosoftassignmentproject.viewModelFactory.CateyDBViewModelFactory
import com.example.neosoftassignmentproject.viewModelFactory.UserViewmodelfactory
import com.example.neosoftassignmentproject.viewmodels.HomeViewModel
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_home_screen.*

class HomeScreenActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener{
    private lateinit var navController: NavController
    private lateinit var viewModel: HomeViewModel
    private val api = Api.getInstance()
    var product_catgy_id:String?=null
    var product_name:String?=null
    private lateinit var db: DataBase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)
        db= DataBase.getInstance(this)
        viewModel=ViewModelProvider(this, CateyDBViewModelFactory(CategoryRepository(api,db))).get(HomeViewModel::class.java)
        setSupportActionBar(findViewById(R.id.toolbar))
        val navHostFragment =

            supportFragmentManager.findFragmentById(R.id.home_container_fragment) as NavHostFragment

        navController = navHostFragment.navController
        NavigationUI.setupWithNavController(navigationView, navController)    // setting navigation of menu item
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)


        val headerText=   navigationView.getHeaderView(0)
        val hView =  navigationView.getHeaderView(0)
        val textViewName =    hView.findViewById(R.id.userName_txt) as TextView
        val textViewEmail =   hView.findViewById(R.id.userEmail_txt) as TextView
        val imgvw = hView.findViewById(R.id.imageView) as ImageView
        imgvw.setImageResource(R.drawable.user_icon)
        viewModel.getProduct.observe(this, Observer {
            product_catgy_id = it.data.product_categories[0].id.toString()
            product_name = it.data.product_categories[0].name.toString()
            val imgUrl=it.data.user_data.profile_pic
            val email = it.data.user_data.email
            val name = it.data.user_data.first_name.plus(it.data.user_data.last_name)
               textViewEmail.text = email.toString()
                textViewName.text = name.toString()
            imgvw.load(imgUrl){
                crossfade(true)
                crossfade(2000)
                transformations(CircleCropTransformation())
            }
        })

        navigationView.setNavigationItemSelectedListener(this)

        InternetConnection(this).observe(this, Observer {isNetworkAvailable ->
           if (!isNetworkAvailable)
            Snackbar.make(
                drawerLayout,
                "no internet connection",
                Snackbar.LENGTH_SHORT
            ).show()

        })



    }
    override fun onSupportNavigateUp(): Boolean {    // to handle back btn
        return NavigationUI.navigateUp(navController, drawerLayout)
    }

    override fun onNavigationItemSelected(menuitem: MenuItem): Boolean {
        val handled = NavigationUI.onNavDestinationSelected(menuitem, navController)

/*
        when (menuitem.itemId){
            R.id.productListFragment ->
                //   val action=HomeFragmentDirections.actionHomeFragmentToProductListFragment(product_catgy_id!!,product_name!!)
                //   navController.navigate(action)
*/
/*
                supportFragmentManager.beginTransaction().apply {
                    replace(R.id.home_container_fragment,TableFragment())
                        .commit()
                }
*//*


                // Creating the new Fragment with the name passed in.
               // TableFragment.ARG_NAME="vivek"
            ProductListFragment.Prod_catgry_id(product_catgy_id!!,product_name!!)



        }
*/
        drawerLayout.closeDrawer(GravityCompat.START)
        return handled
    }


}