package com.zinoview.fragmenttagapp.presentation

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.zinoview.fragmenttagapp.R
import com.zinoview.fragmenttagapp.core.FragmentManager
import com.zinoview.fragmenttagapp.core.PostApp
import com.zinoview.fragmenttagapp.presentation.navigation.TypeFragmentNavigator


class MainActivity : AppCompatActivity() {

    private val navigationViewModel by lazy {
        val application = (application as PostApp)
        application.viewModel(application.navigationModule)
    }

    private val fragmentManager by lazy {
        FragmentManager.Base(this)
    }

    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_nav)

        navigationViewModel.observe(this, {title ->
            supportActionBar?.title = title
        })

        navigationViewModel.navigate(TypeFragmentNavigator.FirstNavigation,fragmentManager)

        val bottomMenuItemIdMapper = (application as PostApp).mapper.menuItemIdMapper
        bottomNavigation.setOnNavigationItemReselectedListener {item ->

            val typeFragmentNavigator = bottomMenuItemIdMapper.map(item)
            navigationViewModel.navigate(typeFragmentNavigator,fragmentManager)
        }

    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {}

    override fun onBackPressed() {
        if (navigationViewModel.navigateToBack(fragmentManager))
            super.onBackPressed()
    }

}

