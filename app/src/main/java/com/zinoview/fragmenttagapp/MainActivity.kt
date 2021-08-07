package com.zinoview.fragmenttagapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.zinoview.fragmenttagapp.core.Abstract
import com.zinoview.fragmenttagapp.core.PostApp
import com.zinoview.fragmenttagapp.presentation.fragment.CacheFragment
import com.zinoview.fragmenttagapp.presentation.fragment.ListPostFragment
import com.zinoview.fragmenttagapp.presentation.ResultNavigation
import com.zinoview.fragmenttagapp.presentation.navigation.CacheFragmentConfig
import com.zinoview.fragmenttagapp.presentation.navigation.ListPostFragmentConfig

class MainActivity : AppCompatActivity() {

    private val baseFragmentsConfig by lazy {
        listOf(
            ListPostFragmentConfig(
                ListPostFragment(),
                supportFragmentManager
            ), CacheFragmentConfig(
                CacheFragment(),
                supportFragmentManager
            )
        )
    }

    //todo this viewmodel used in listpostfragnent
    private val viewmodel by lazy {
        (application as PostApp).postViewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_nav)

        viewmodel.navigate(baseFragmentsConfig[LIST_POST_BASE_FRAGMENT_POSITION])
//        val navigator: FragmentNavigator = FragmentNavigator.Base
//        supportFragmentManager.beginTransaction().apply {
//            replace(R.id.container,ListPostFragment(),"ListPostFragment")
//            commit()
//        }

        val itemIdMapper = Abstract.FactoryMapper.MenuItemIdMapper()
        bottomNavigation.setOnNavigationItemReselectedListener {item ->
            val baseFragment = itemIdMapper.map(item.itemId)

            if (baseFragment is CacheFragment) {
                viewmodel.navigateToCacheFragment(baseFragmentsConfig[CACHE_BASE_FRAGMENT_POSITION])
            } else {
                viewmodel.navigateToListPostFragment(baseFragmentsConfig[LIST_POST_BASE_FRAGMENT_POSITION])
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {}

    override fun onBackPressed() {
        val resultNavigation = viewmodel.navigateToBack(baseFragmentsConfig[LIST_POST_BASE_FRAGMENT_POSITION])
        when(resultNavigation) {
            ResultNavigation.EXIT -> super.onBackPressed()
            ResultNavigation.BACK -> Log.d("TestFragment","Result nav BACK")
            else -> Log.d("TestFragment","Result nav NONE")
        }

    }

    private companion object {
        const val LIST_POST_BASE_FRAGMENT_POSITION = 0
        const val CACHE_BASE_FRAGMENT_POSITION = 1
    }

}