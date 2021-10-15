package com.nads.dindinnapp.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.Menu
import android.view.MenuItem
import com.nads.dindinnapp.R
import com.nads.dindinnapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import androidx.appcompat.widget.PopupMenu
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import com.google.android.material.navigation.NavigationView
import android.widget.Toast
import androidx.activity.viewModels
import com.nads.dindinnapp.ui.viewmodel.HomeActivityViewModel
import com.nads.dindinnapp.ui.viewmodel.ViewModelFactory


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var appBarConfiguration : AppBarConfiguration
    private lateinit var navController: NavController
    private lateinit var toolbars: Toolbar
    private lateinit var navview: NavigationView

    private val activityViewModel: HomeActivityViewModel by viewModels { ViewModelFactory.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding:ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        binding.homeactivityviewmodel = activityViewModel

        binding.lifecycleOwner = this
        setContentView(binding.root)
        binding.menuicons.setColorFilter(R.color.black)
        binding.menuicons.setOnClickListener {
            val popup = PopupMenu(this, binding.menuicons)
            popup.getMenuInflater().inflate(R.menu.menupopup, popup.getMenu())
            popup.setOnMenuItemClickListener { item ->
                when(item.itemId){
                    R.id.ingredients->goToIngredient(navController)
                    else->Toast.makeText(this, "You Clicked : " + item.title, Toast.LENGTH_SHORT)
                        .show()
                }


                true
            }

            popup.show()
        }

        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_dashboard) as NavHostFragment
        navController = navHostFragment.navController

    }

    private fun goToIngredient(navController: NavController) {
        if (navController.currentDestination?.id == R.id.ingredientFrag)
            navController.navigateUp()
        else navController.navigate(R.id.action_orderFragment_to_ingredientFrag)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)

    }

    override fun onDestroy() {
        super.onDestroy()
        activityViewModel.arr.clear()
    }
}