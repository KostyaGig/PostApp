package com.zinoview.fragmenttagapp.presentation.cache

import android.util.Log
import com.zinoview.fragmenttagapp.core.Check
import com.zinoview.fragmenttagapp.data.cache.CachePostRepository
import com.zinoview.fragmenttagapp.data.cache.FileCommunicator
import com.zinoview.fragmenttagapp.domain.PostInteractor
import com.zinoview.fragmenttagapp.domain.cache.CacheDomainPost
import com.zinoview.fragmenttagapp.domain.cache.CachePostInteractor

/**
 * @author Zinoview on 08.08.2021
 * k.gig@list.ru
 */
interface CacheUiPostClicked {

    suspend fun updateFile(interactor: CachePostInteractor): CacheDomainPost<RecordCacheState>
    suspend fun writeData(interactor: CachePostInteractor) : CacheDomainPost<RecordCacheState>

    class Base(
        private val cacheUiPostClicked: String,
        private val checker: Check<Pair<String, String>>,
        private val cacheGenerator: CacheGenerator
    ) : CacheUiPostClicked {

        //todo move interactor to constructor (DRY)

        override suspend fun updateFile(interactor: CachePostInteractor): CacheDomainPost<RecordCacheState> {
            val newCache = cacheGenerator.generate(interactor.commonData())
            return interactor.updateFile(newCache)
        }

        override suspend fun writeData(interactor: CachePostInteractor) : CacheDomainPost<RecordCacheState> {
            val currentCache = interactor.commonData()
            val sameStrings = Pair(currentCache,cacheUiPostClicked)
            return if (samePartOfCacheCheck(sameStrings).not()) {
                interactor.writeData(cacheUiPostClicked)
            } else {
                CacheDomainPost.RecordCacheDomainPost.Success("This is post already exist") //todo use resourceProvider
            }
        }

        private fun samePartOfCacheCheck(samesString: Pair<String,String>) : Boolean
            = checker.check(samesString)

    }
}