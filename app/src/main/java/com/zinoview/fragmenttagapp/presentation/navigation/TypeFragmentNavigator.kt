package com.zinoview.fragmenttagapp.presentation.navigation



/**
 * @author Zinoview on 12.08.2021
 * k.gig@list.ru
 */
sealed class TypeFragmentNavigator {

    object FirstNavigation : TypeFragmentNavigator()

    object ListPostNavigation : TypeFragmentNavigator()

    object CacheNavigation : TypeFragmentNavigator()

}
