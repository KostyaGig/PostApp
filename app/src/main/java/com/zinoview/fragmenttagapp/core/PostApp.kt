package com.zinoview.fragmenttagapp.core

import android.app.Application
import androidx.lifecycle.ViewModel
import com.zinoview.fragmenttagapp.presentation.UiPost
import com.zinoview.fragmenttagapp.presentation.cache.*
import com.zinoview.fragmenttagapp.service_locator.cache.CacheModule
import com.zinoview.fragmenttagapp.service_locator.core.BaseModule
import com.zinoview.fragmenttagapp.service_locator.core.DependencyContainer
import com.zinoview.fragmenttagapp.service_locator.post.PostModule

/**
 * @author Zinoview on 29.07.2021
 * k.gig@list.ru
 */
class PostApp : Application() {

    lateinit var cacheModule: CacheModule
    lateinit var postModule: PostModule

    lateinit var checkerDependencyContainer: DependencyContainer.Checker

    override fun onCreate() {
        super.onCreate()

        val core = DependencyContainer.Core()
        val cache = DependencyContainer.Cache(core)
        val network = DependencyContainer.Network()
        val post = DependencyContainer.Post(network, cache, core)
        checkerDependencyContainer = DependencyContainer.Checker(core,cache)

        val dependencyContainers = listOf(
            core,cache,network,post,checkerDependencyContainer
        )

        for (dependencyContainer in dependencyContainers) {
            dependencyContainer.init(this)
        }

        cacheModule = CacheModule(cache)
        postModule = PostModule(post)

    }

    fun <T : ViewModel> viewModel(module: BaseModule<T>) : T = module.getViewModel()

    fun cacheUiPostClicked(cache: String) : CacheUiPostClicked
        = CacheUiPostClicked.Base(
            cache,
            checkerDependencyContainer.sameContentCheck,
            CacheGenerator.Base(cache),
            checkerDependencyContainer.cachePostInteractor,
            checkerDependencyContainer.resource
        )

}