package com.zinoview.fragmenttagapp.service_locator.cache

import com.zinoview.fragmenttagapp.presentation.cache.CachePostViewModel
import com.zinoview.fragmenttagapp.service_locator.core.BaseModule
import com.zinoview.fragmenttagapp.service_locator.core.DependencyContainer


/**
 * @author Zinoview on 11.08.2021
 * k.gig@list.ru
 */
class CacheModule(
    private val dependencyContainer: DependencyContainer.Cache
) : BaseModule<CachePostViewModel.Base> {

    override fun getViewModel(): CachePostViewModel.Base
        = CachePostViewModel.Base(
            dependencyContainer.cachePostInteractor,
            dependencyContainer.cachePostCommunications,
            dependencyContainer.uiPostCacheMappers
        )
}