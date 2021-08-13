package com.zinoview.fragmenttagapp.presentation.post

import androidx.lifecycle.*
import androidx.lifecycle.ViewModel
import com.zinoview.fragmenttagapp.core.Abstract
import com.zinoview.fragmenttagapp.data.cache.Read
import com.zinoview.fragmenttagapp.domain.PostDomain
import com.zinoview.fragmenttagapp.domain.PostInteractor
import com.zinoview.fragmenttagapp.domain.cache.CachePostInteractor
import com.zinoview.fragmenttagapp.presentation.Communication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException


/**
 * @author Zinoview on 29.07.2021
 * k.gig@list.ru
 */

interface PostViewModel : Read<Unit> {

    fun observe(owner: LifecycleOwner,observer: Observer<List<UiPost>>)

    class Base(
        private val postInteractor: PostInteractor,
        private val cachePostInteractor: CachePostInteractor,
        private val listPostMapper: Abstract.ListPostMapper<PostDomain, ListPostUi,IOException>,
        private val communicate: Pair<Communication<List<UiPost>>,Abstract.PostMapper<UiPost>>,
    ) : ViewModel(), PostViewModel {

        override fun data() {
            communicate.first.changeValue(listOf(UiPost.Progress))
            viewModelScope.launch(Dispatchers.IO) {
                val currentCache = cachePostInteractor.commonData()
                val listPostUi = postInteractor.listPostDomain().map(listPostMapper)

                withContext(Dispatchers.Main) {
                    with(listPostUi) {
                        addCurrentCache(currentCache)
                        map(communicate)
                    }
                }
            }
        }

        override fun observe(owner: LifecycleOwner, observer: Observer<List<UiPost>>) {
            communicate.first.observe(owner, observer)
        }
    }
}


