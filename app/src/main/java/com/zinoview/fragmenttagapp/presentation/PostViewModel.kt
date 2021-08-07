package com.zinoview.fragmenttagapp.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zinoview.fragmenttagapp.core.Abstract
import com.zinoview.fragmenttagapp.domain.PostDomain
import com.zinoview.fragmenttagapp.domain.PostInteractor
import com.zinoview.fragmenttagapp.presentation.navigation.BaseFragmentConfig
import com.zinoview.fragmenttagapp.presentation.navigation.ModelNavigator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException


/**
 * @author Zinoview on 29.07.2021
 * k.gig@list.ru
 */
class PostViewModel(
    private val postInteractor: PostInteractor,
    private val listPostMapper: Abstract.ListPostMapper<PostDomain,ListPostUi,IOException>,
    private val modelNavigator: ModelNavigator,
    private val communicate: Pair<PostCommunication,Abstract.PostMapper<UiPost>>
) : ViewModel() {

    fun posts() {
        communicate.first.changeValue(listOf(UiPost.Progress))
        viewModelScope.launch(Dispatchers.IO) {
            val listPostUi = postInteractor.listPostDomain().map(listPostMapper)

            withContext(Dispatchers.Main) {
                listPostUi.map(communicate)
            }
        }
    }

    fun observe(owner: LifecycleOwner,observer: Observer<List<UiPost>>) {
        communicate.first.observe(owner, observer)
    }

    fun navigate(baseFragmentConfig: BaseFragmentConfig) {
        baseFragmentConfig.navigate(modelNavigator)
    }

    fun navigateToListPostFragment(baseFragmentConfig: BaseFragmentConfig) {
        baseFragmentConfig.navigateToListPostFragment(modelNavigator)
    }

    fun navigateToCacheFragment(baseFragmentConfig: BaseFragmentConfig) {
        baseFragmentConfig.navigateToCacheFragment(modelNavigator)
    }

    fun navigateToBack(baseFragmentConfig: BaseFragmentConfig) : ResultNavigation
        = baseFragmentConfig.navigateToBack(modelNavigator)

}