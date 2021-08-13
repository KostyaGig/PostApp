package com.zinoview.fragmenttagapp.presentation.navigation

import android.os.Bundle
import android.util.Log
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.zinoview.fragmenttagapp.R
import com.zinoview.fragmenttagapp.presentation.fragment.ListPostFragment
import com.zinoview.fragmenttagapp.core.*
import com.zinoview.fragmenttagapp.presentation.Communication
import com.zinoview.fragmenttagapp.presentation.fragment.CacheFragment

/**
 * @author Zinoview on 12.08.2021
 * k.gig@list.ru
 */
interface Navigator {

    fun navigate(type: TypeFragmentNavigator, fragmentManager: FragmentManager)

    fun navigate(fragmentManager: FragmentManager, bundle: Bundle)

    fun navigateToBack(fragmentManager: FragmentManager): Boolean

    class Base(
        private val communication: Communication<String>,
        private val resource: Resource
    ) : Navigator {

        override fun navigate(type: TypeFragmentNavigator, fragmentManager: FragmentManager) {
            when (type) {
                is TypeFragmentNavigator.FirstNavigation -> {
                    navigate(fragmentManager, ListPostFragment(), LIST_POST_FRAGMENT_TAG)
                    changeTitleToolbar(fragmentManager, R.string.posts_string)
                }

                is TypeFragmentNavigator.ListPostNavigation -> {
                    val listPostFragment = fragmentManager.provide()
                        .findFragmentByTag(LIST_POST_FRAGMENT_TAG)

                    if (fragmentManager.provide().backStackEntryCount > 0) {
                        val backStackEntryCount = fragmentManager.provide().backStackEntryCount
                        for (i in 0..backStackEntryCount) {
                            fragmentManager.provide().popBackStack()
                        }
                    }

                    listPostFragment?.let { fragment ->
                        navigate(fragmentManager, fragment)
                        changeTitleToolbar(fragmentManager, R.string.posts_string)
                    }
                }

                is TypeFragmentNavigator.CacheNavigation -> {
                    val cacheFragment = fragmentManager.provide()
                        .findFragmentByTag(CACHE_FRAGMENT_TAG)

                    cacheFragment?.let { fragment ->
                        navigate(fragmentManager, fragment)
                    } ?: navigate(fragmentManager, CacheFragment(), CACHE_FRAGMENT_TAG, true)

                    changeTitleToolbar(fragmentManager, R.string.cache_string)
                }

            }
        }

        override fun navigate(fragmentManager: FragmentManager, bundle: Bundle) {
            val cacheFragment = CacheFragment()
            cacheFragment.arguments = bundle

            fragmentManager.provide().beginTransaction().apply {
                replace(R.id.container, cacheFragment)
                addToBackStack(null)
                commit()

                changeTitleToolbar(fragmentManager, R.string.post_info_text)
            }
        }

        override fun navigateToBack(fragmentManager: FragmentManager): Boolean =
            when (fragmentManager.provide().backStackEntryCount) {
                0 -> true
                1 -> {
                    changeTitleToolbar(fragmentManager,R.string.posts_string)
                    fragmentManager.provide().popBackStack()
                    false
                }
                2 -> {
                    changeTitleToolbar(fragmentManager,R.string.post_info_text)
                    fragmentManager.provide().popBackStack()
                    false
                }
                else -> {
                    Log.d("TestF","${fragmentManager.provide().backStackEntryCount}")
                    false
                }
            }

        private fun navigate(fragmentManager: FragmentManager, fragment: Fragment) =
            fragmentManager.provide().beginTransaction().apply {
                replace(R.id.container, fragment)
                commit()
            }

        private fun navigate(
            fragmentManager: FragmentManager,
            fragment: Fragment,
            fragmentTag: String,
            addToBackStack: Boolean = false
        ) {
            if (addToBackStack) {
                navigateWithBackStack(fragmentManager, fragment, fragmentTag)
            } else {
                fragmentManager.provide().beginTransaction().apply {
                    replace(R.id.container, fragment, fragmentTag)
                    commit()
                }
            }
        }

        private fun navigateWithBackStack(
            fragmentManager: FragmentManager,
            fragment: Fragment,
            fragmentTag: String)
        = fragmentManager.provide().beginTransaction().apply {
            replace(R.id.container, fragment, fragmentTag)
            addToBackStack(null)
            commit()
        }

        private fun changeTitleToolbar(fragmentManager: FragmentManager, @StringRes resId: Int)
            = fragmentManager.changeTitle(communication, resource.string(resId))
        }

        private companion object {
            const val LIST_POST_FRAGMENT_TAG = "ListPostFragmentTag"
            const val CACHE_FRAGMENT_TAG = "CacheFragmentTag"
        }
    }