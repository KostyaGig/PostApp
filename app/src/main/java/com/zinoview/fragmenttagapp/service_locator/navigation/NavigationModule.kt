package com.zinoview.fragmenttagapp.service_locator.navigation

import com.zinoview.fragmenttagapp.presentation.navigation.NavigationViewModel
import com.zinoview.fragmenttagapp.service_locator.core.BaseModule
import com.zinoview.fragmenttagapp.service_locator.core.DependencyContainer


/**
 * @author Zinoview on 13.08.2021
 * k.gig@list.ru
 */
class NavigationModule(
    private val dependencyContainer: DependencyContainer.Navigation
) : BaseModule<NavigationViewModel.Base> {

    override fun getViewModel(): NavigationViewModel.Base
        = NavigationViewModel.Base(
            dependencyContainer.navigator,
            dependencyContainer.communication
        )
}