package com.zinoview.fragmenttagapp.presentation.cache

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zinoview.fragmenttagapp.data.cache.Read
import com.zinoview.fragmenttagapp.data.cache.Record
import com.zinoview.fragmenttagapp.data.cache.Update
import com.zinoview.fragmenttagapp.domain.cache.CachePostInteractor
import com.zinoview.fragmenttagapp.presentation.Communication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


/**
 * @author Zinoview on 01.08.2021
 * k.gig@list.ru
 */

interface CachePostViewModel : Record<CacheUiPostClicked>, Read<Job>,  Update<CacheUiPostClicked, Job> {

    fun observe(
        owner: LifecycleOwner, cacheRecordObserver: Observer<RecordCacheState>,
        cacheReadObserver: Observer<String>
    )

    class Base(
        private val cachePostInteractor: CachePostInteractor,
        private val cachePostCommunications:
        Pair<Communication<RecordCacheState>, Communication<String>>,
        private val cacheUiPostMappers:
        Pair<UiPostCacheMapper<RecordCacheState>, UiPostCacheMapper<String>>
    ) : ViewModel(), CachePostViewModel {

        override fun writeData(data: CacheUiPostClicked) {
            viewModelScope.launch(Dispatchers.IO) {
                val cacheDomainPost = data.writeData()

                val cachePostUi = cacheDomainPost.map(cacheUiPostMappers.first)

                withContext(Dispatchers.Main) {
                    cachePostUi.map(cachePostCommunications.first)
                }
            }
        }

        override fun data() = viewModelScope.launch(Dispatchers.IO) {
            val cacheDomainPost = cachePostInteractor.readData()
            val cachePostUi = cacheDomainPost.map(cacheUiPostMappers.second)

            withContext(Dispatchers.Main) {
                cachePostUi.map(cachePostCommunications.second)
            }
        }

        override fun update(src: CacheUiPostClicked) = viewModelScope.launch(Dispatchers.IO) {
            val resultDomainUpdateCache = src.updateFile()

            val resultUiUpdateCache = resultDomainUpdateCache.map(cacheUiPostMappers.first)

            withContext(Dispatchers.Main) {
                resultUiUpdateCache.map(cachePostCommunications.first)
            }
        }


        override fun observe(
            owner: LifecycleOwner,
            cacheRecordObserver: Observer<RecordCacheState>,
            cacheReadObserver: Observer<String>
        ) {
            with(cachePostCommunications) {
                first.observe(owner, cacheRecordObserver)
                second.observe(owner, cacheReadObserver)
            }
        }
    }
}
