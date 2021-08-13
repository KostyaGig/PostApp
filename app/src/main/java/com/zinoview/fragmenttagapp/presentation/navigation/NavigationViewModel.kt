package com.zinoview.fragmenttagapp.presentation.navigation

import android.os.Bundle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.zinoview.fragmenttagapp.core.FragmentManager
import com.zinoview.fragmenttagapp.presentation.Communication


/**
 * @author Zinoview on 13.08.2021
 * k.gig@list.ru
 */
interface NavigationViewModel {

    fun navigate(type: TypeFragmentNavigator, fragmentManager: FragmentManager)
    fun navigate(fragmentManager: FragmentManager, bundle: Bundle)
    fun navigateToBack(fragmentManager: FragmentManager) : Boolean

    fun observe(owner: LifecycleOwner, observer: Observer<String>)

    class Base(
        private val navigator: Navigator,
        private val communication: Communication<String>
    ) : ViewModel(), NavigationViewModel {


        override fun navigate(type: TypeFragmentNavigator, fragmentManager: FragmentManager){
            navigator.navigate(type,fragmentManager)
        }

        override fun navigate(fragmentManager: FragmentManager, bundle: Bundle)
                = navigator.navigate(fragmentManager, bundle)

        override fun navigateToBack(fragmentManager: FragmentManager) : Boolean
                = navigator.navigateToBack(fragmentManager)

        override fun observe(owner: LifecycleOwner, observer: Observer<String>){
            communication.observe(owner,observer)
        }
    }

}