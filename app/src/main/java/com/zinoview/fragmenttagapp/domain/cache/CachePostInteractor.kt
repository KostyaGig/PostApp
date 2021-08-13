package com.zinoview.fragmenttagapp.domain.cache

import com.zinoview.fragmenttagapp.data.cache.CachePostRepository
import com.zinoview.fragmenttagapp.presentation.cache.RecordCacheState


/**
 * @author Zinoview on 01.08.2021
 * k.gig@list.ru
 */
interface CachePostInteractor {

    suspend fun writeData(data: String) : CacheDomainPost<RecordCacheState>
    suspend fun readData() : CacheDomainPost<String>
    suspend fun commonData() : String
    suspend fun updateFile(newCache: String) : CacheDomainPost<RecordCacheState>

    class Base(
        private val cachePostRepository: CachePostRepository,
        private val postDomainCacheMappers: Pair<PostDomainCacheMapper<RecordCacheState>, PostDomainCacheMapper<String>>,
    ) : CachePostInteractor {

        override suspend fun writeData(data: String): CacheDomainPost<RecordCacheState>
            = cachePostRepository.writeData(data).map(postDomainCacheMappers.first)

        override suspend fun readData(): CacheDomainPost<String>
            = cachePostRepository.data().map(postDomainCacheMappers.second)

        override suspend fun commonData(): String
            = cachePostRepository.commonData()

        override suspend fun updateFile(newCache: String) : CacheDomainPost<RecordCacheState>
            = cachePostRepository.updateFile(newCache).map(postDomainCacheMappers.first)
    }
}