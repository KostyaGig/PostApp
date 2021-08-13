package com.zinoview.fragmenttagapp.presentation

import android.view.MenuItem
import com.zinoview.fragmenttagapp.R
import com.zinoview.fragmenttagapp.core.Abstract
import com.zinoview.fragmenttagapp.presentation.navigation.TypeFragmentNavigator


/**
 * @author Zinoview on 13.08.2021
 * k.gig@list.ru
 */

class MenuItemIdMapper : Abstract.FactoryMapper<MenuItem, TypeFragmentNavigator> {
    override fun map(src: MenuItem): TypeFragmentNavigator = when(src.itemId) {
        R.id.posts_item -> TypeFragmentNavigator.ListPostNavigation
        else -> TypeFragmentNavigator.CacheNavigation
    }
}