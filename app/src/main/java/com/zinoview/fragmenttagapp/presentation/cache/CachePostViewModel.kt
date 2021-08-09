package com.zinoview.fragmenttagapp.presentation.cache

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zinoview.fragmenttagapp.domain.cache.CachePostInteractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


/**
 * @author Zinoview on 01.08.2021
 * k.gig@list.ru
 */
class CachePostViewModel(
    private val cachePostInteractor: CachePostInteractor,
    private val cachePostCommunications: Pair<CachePostCommunication<RecordCacheState>, CachePostCommunication<String>>,
    private val cacheUiPostMappers: Pair<UiPostCacheMapper<RecordCacheState>, UiPostCacheMapper<String>>
) : ViewModel() { //todo make interface

    fun writeData(cachedUiPost: CacheUiPostClicked) = viewModelScope.launch(Dispatchers.IO) {
        val cacheDomainPost = cachedUiPost.writeData(cachePostInteractor)

        val cachePostUi = cacheDomainPost.map(cacheUiPostMappers.first)

        withContext(Dispatchers.Main) {
            cachePostUi.map(cachePostCommunications.first)
        }
    }

    fun cachedData() = viewModelScope.launch(Dispatchers.IO) {
        val cacheDomainPost = cachePostInteractor.readData()
        val cachePostUi = cacheDomainPost.map(cacheUiPostMappers.second)

        withContext(Dispatchers.Main) {
            cachePostUi.map(cachePostCommunications.second)
        }
    }

    fun updateCache(cachedUiPost: CacheUiPostClicked) = viewModelScope.launch(Dispatchers.IO) {
        val resultDomainUpdateCache = cachedUiPost.updateFile(cachePostInteractor)

        val resultUiUpdateCache = resultDomainUpdateCache.map(cacheUiPostMappers.first)

        withContext(Dispatchers.Main) {
            resultUiUpdateCache.map(cachePostCommunications.first)
        }
    }


    fun observe(owner: LifecycleOwner, cacheRecordObserver: Observer<RecordCacheState>, cacheReadObserver: Observer<String>) {
        with(cachePostCommunications) {
            first.observe(owner, cacheRecordObserver)
            second.observe(owner, cacheReadObserver)
        }
    }

}