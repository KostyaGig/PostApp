package com.zinoview.fragmenttagapp.domain

import com.zinoview.fragmenttagapp.core.Resource
import com.zinoview.fragmenttagapp.data.CachePostRepository
import java.io.IOException


/**
 * @author Zinoview on 01.08.2021
 * k.gig@list.ru
 */
interface CachePostInteractor {

    suspend fun writeData(data: String) : CacheDomainPost
    suspend fun readData() : CacheDomainPost

    class Base(
        private val cachePostRepository: CachePostRepository,
        private val cacheDomainMapper: PostDomainCacheMapper,
    ) : CachePostInteractor {

        override suspend fun writeData(data: String): CacheDomainPost
            = cachePostRepository.writeData(data).map(cacheDomainMapper)

        override suspend fun readData(): CacheDomainPost
            = cachePostRepository.data().map(cacheDomainMapper)
    }
}