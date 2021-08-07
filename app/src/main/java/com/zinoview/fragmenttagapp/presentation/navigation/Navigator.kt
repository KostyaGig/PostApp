package com.zinoview.fragmenttagapp.presentation.navigation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.zinoview.fragmenttagapp.R
import com.zinoview.fragmenttagapp.core.Check
import com.zinoview.fragmenttagapp.presentation.fragment.BaseFragment
import com.zinoview.fragmenttagapp.presentation.fragment.CacheFragment
import com.zinoview.fragmenttagapp.presentation.ResultNavigation


/**
 * @author Zinoview on 29.07.2021
 * k.gig@list.ru
 */

abstract class BaseFragmentConfig {

    open fun navigate(navigator: ModelNavigator){}

    open fun navigateToListPostFragment(navigator: ModelNavigator) {}

    open fun navigateToCacheFragment(navigator: ModelNavigator) {}

    open fun navigateToBack(navigator: ModelNavigator): ResultNavigation = ResultNavigation.NONE

}

class ListPostFragmentConfig(
    private val fragment: BaseFragment,
    private val fragmentManager: FragmentManager,
) : BaseFragmentConfig() {
    override fun navigate(navigator: ModelNavigator)
        = navigator.navigate(fragment,fragmentManager)

    override fun navigateToListPostFragment(navigator: ModelNavigator)
        = navigator.navigateToListPostFragment(fragment, fragmentManager)

    override fun navigateToBack(navigator: ModelNavigator): ResultNavigation = navigator.navigateToBack(fragmentManager)
}

class CacheFragmentConfig(
    private val fragment: BaseFragment,
    private val fragmentManager: FragmentManager,
) : BaseFragmentConfig() {
    override fun navigateToCacheFragment(navigator: ModelNavigator) = navigator.navigateToCacheFragment(fragment, fragmentManager)
}

interface ModelNavigator {

    fun navigate(fragment: BaseFragment, fragmentManager: FragmentManager)

    fun navigateToCacheFragment(fragment: BaseFragment,fragmentManager: FragmentManager)

    fun navigateToListPostFragment(fragment: BaseFragment,fragmentManager: FragmentManager)

    fun navigateToBack(fragmentManager: FragmentManager) : ResultNavigation

    class Base(
        private val check: Check<Fragment?>
    ) : ModelNavigator {
        override fun navigate(fragment: BaseFragment,fragmentManager: FragmentManager) {
            fragmentManager.beginTransaction().apply {
                replace(CONTAINER_ID, fragment, LIST_POST_FRAGMENT_TAG)
                commit()
            }
        }

        override fun navigateToCacheFragment(fragment: BaseFragment,fragmentManager: FragmentManager) {
            fragmentManager.beginTransaction().apply {
                val cachedCacheFragment = fragmentManager.findFragmentByTag(CACHE_FRAGMENT_TAG)
                val transaction = fragmentManager.beginTransaction()

                if (check.check(cachedCacheFragment)) {
                    transaction.apply {
                        replace(CONTAINER_ID,fragment, CACHE_FRAGMENT_TAG)
                        addToBackStack(null)
                        commit()
                    }
                } else {
                    transaction.apply {
                        replace(CONTAINER_ID,fragment)
                        commit()
                    }
                }
            }
        }

        override fun navigateToListPostFragment(fragment: BaseFragment, fragmentManager: FragmentManager) {
            val cachedListPostFragment = fragmentManager.findFragmentByTag(LIST_POST_FRAGMENT_TAG)
            fragmentManager.beginTransaction().apply {
                replace(CONTAINER_ID, requireNotNull(cachedListPostFragment))
                commit()
            }
        }

        override fun navigateToBack(fragmentManager: FragmentManager) : ResultNavigation {
            val cachedCacheFragment = fragmentManager.findFragmentByTag(CACHE_FRAGMENT_TAG)

            return if (cachedCacheFragment != null && (cachedCacheFragment as CacheFragment).isVisible) {
                val cachedListPostFragment = fragmentManager.findFragmentByTag(
                    LIST_POST_FRAGMENT_TAG
                )
                fragmentManager.beginTransaction().apply {
                    replace(CONTAINER_ID, requireNotNull(cachedListPostFragment))
                    commit()
                }
                ResultNavigation.BACK
            } else {
                ResultNavigation.EXIT
            }
        }

    }

    private companion object {
        const val CONTAINER_ID = R.id.container

        const val LIST_POST_FRAGMENT_TAG = "ListPostFragment"
        const val CACHE_FRAGMENT_TAG = "CacheFragment"
    }

}

//interface Navigator {
//    fun navigate(nav: Nav) //todo make interface for Nav class [com.zinoview.fragmenttagapp.presentation.Nav]
//}

