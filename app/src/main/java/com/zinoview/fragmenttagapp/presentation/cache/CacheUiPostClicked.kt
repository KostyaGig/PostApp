package com.zinoview.fragmenttagapp.presentation.cache

import com.zinoview.fragmenttagapp.R
import com.zinoview.fragmenttagapp.core.Check
import com.zinoview.fragmenttagapp.core.Resource
import com.zinoview.fragmenttagapp.domain.cache.CacheDomainPost
import com.zinoview.fragmenttagapp.domain.cache.CachePostInteractor

/**
 * @author Zinoview on 08.08.2021
 * k.gig@list.ru
 */
interface CacheUiPostClicked {

    suspend fun updateFile() : CacheDomainPost<RecordCacheState>
    suspend fun writeData() : CacheDomainPost<RecordCacheState>

    class Base(
        private val cacheUiPostClicked: String,
        private val checker: Check<Pair<String, String>>,
        private val cacheGenerator: CacheGenerator,
        private val cachePostInteractor: CachePostInteractor,
        private val resource: Resource
    ) : CacheUiPostClicked {


        override suspend fun updateFile(): CacheDomainPost<RecordCacheState> {
            val newCache = cacheGenerator.generate(cachePostInteractor.commonData())
            return cachePostInteractor.updateFile(newCache)
        }

        override suspend fun writeData() : CacheDomainPost<RecordCacheState> {
            val currentCache = cachePostInteractor.commonData()
            val sameCache = Pair(currentCache,cacheUiPostClicked)
            return if (samePartOfCacheCheck(sameCache).not()) {
                cachePostInteractor.writeData(cacheUiPostClicked)
            } else {
                CacheDomainPost.RecordCacheDomainPost.Success(resource.string(R.string.existing_post_text))
            }
        }

        private fun samePartOfCacheCheck(samesString: Pair<String,String>) : Boolean
            = checker.check(samesString)
    }

}