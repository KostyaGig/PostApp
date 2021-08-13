package com.zinoview.fragmenttagapp.core

import android.app.Application
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import com.zinoview.fragmenttagapp.presentation.Generator
import com.zinoview.fragmenttagapp.presentation.cache.*
import com.zinoview.fragmenttagapp.service_locator.cache.CacheModule
import com.zinoview.fragmenttagapp.service_locator.core.BaseModule
import com.zinoview.fragmenttagapp.service_locator.core.DependencyContainer
import com.zinoview.fragmenttagapp.service_locator.navigation.NavigationModule
import com.zinoview.fragmenttagapp.service_locator.post.PostModule

/**
 * @author Zinoview on 29.07.2021
 * k.gig@list.ru
 */
class PostApp : Application() {

    lateinit var cacheModule: CacheModule
    lateinit var postModule: PostModule
    lateinit var navigationModule: NavigationModule

    lateinit var checker: DependencyContainer.Checker
    lateinit var mapper: DependencyContainer.Mapper
    lateinit var generator: DependencyContainer.Generator

    override fun onCreate() {
        super.onCreate()

        val core = DependencyContainer.Core()
        val cache = DependencyContainer.Cache(core)
        val network = DependencyContainer.Network()
        val post = DependencyContainer.Post(network, cache, core)
        val navigation = DependencyContainer.Navigation(core)
        checker = DependencyContainer.Checker(core,cache)
        mapper = DependencyContainer.Mapper()
        generator = DependencyContainer.Generator()

        val dependencyContainers = listOf(
            core,cache,network,post,navigation,checker,mapper,generator
        )

        for (dependencyContainer in dependencyContainers) {
            dependencyContainer.init(this)
        }

        cacheModule = CacheModule(cache)
        postModule = PostModule(post)
        navigationModule = NavigationModule(navigation)
    }

    fun <T : ViewModel> viewModel(module: BaseModule<T>) : T = module.getViewModel()

    fun cacheUiPostClicked(cache: String) : CacheUiPostClicked
        = CacheUiPostClicked.Base(
            cache,
            checker.sameContentCheck,
            Generator.Cache(cache),
            checker.cachePostInteractor,
            checker.resource
        )

    fun string(@StringRes idRes: Int) : String = checker.resource.string(idRes)
}