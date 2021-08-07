package com.zinoview.fragmenttagapp.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zinoview.fragmenttagapp.core.Abstract
import com.zinoview.fragmenttagapp.domain.CachePostInteractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException


/**
 * @author Zinoview on 01.08.2021
 * k.gig@list.ru
 */
class CachePostViewModel(
    private val cachePostInteractor: CachePostInteractor,
    private val cachePostCommunication: CachePostCommunication,
    private val cachePostUiMapper: Abstract.PostCacheMapper<CachePostUi,IOException>,
) : ViewModel() {

    fun writeData(data: String) = viewModelScope.launch(Dispatchers.IO) {
        val cacheDomainPost = cachePostInteractor.writeData(data)
        val cachePostUi = cacheDomainPost.map(cachePostUiMapper)

        withContext(Dispatchers.Main) {
            cachePostUi.map(cachePostCommunication)
        }
    }

    fun cachedData() = viewModelScope.launch(Dispatchers.IO) {
        val cacheDomainPost = cachePostInteractor.readData()
        val cachePostUi = cacheDomainPost.map(cachePostUiMapper)

        withContext(Dispatchers.Main) {
            cachePostUi.map(cachePostCommunication)
        }
    }

    fun observe(owner: LifecycleOwner, observer: Observer<String>) {
        cachePostCommunication.observe(owner, observer)
    }
}