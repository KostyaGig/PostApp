package com.zinoview.fragmenttagapp.service_locator.post

import com.zinoview.fragmenttagapp.presentation.PostViewModel
import com.zinoview.fragmenttagapp.service_locator.core.BaseModule
import com.zinoview.fragmenttagapp.service_locator.core.DependencyContainer


/**
 * @author Zinoview on 11.08.2021
 * k.gig@list.ru
 */

//todo make for PostViewModel and CachePostViewModel abstract viewwmodel, whick should be extends от ViewModel,for DIP
class PostModule(
    private val dependencyContainer: DependencyContainer.Post
) : BaseModule<PostViewModel.Base> {

    override fun getViewModel(): PostViewModel.Base
        = PostViewModel.Base(
            dependencyContainer.postInteractor,
            dependencyContainer.cachePostInteractor,
            dependencyContainer.listPostUiMapper,
            dependencyContainer.modelNavigator,
            dependencyContainer.communicate
        )
}