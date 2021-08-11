package com.zinoview.fragmenttagapp.domain.cache

import com.zinoview.fragmenttagapp.core.Abstract
import com.zinoview.fragmenttagapp.presentation.cache.CacheUiPost
import com.zinoview.fragmenttagapp.presentation.cache.RecordCacheState
import com.zinoview.fragmenttagapp.presentation.cache.UiPostCacheMapper
import java.io.IOException


/**
 * @author Zinoview on 04.08.2021
 * k.gig@list.ru
 */

abstract class PostDomainCacheMapper<T : Any> : Abstract.PostCacheMapper<CacheDomainPost<T>,Exception> {

    class Record(
        private val exceptionMapper: Abstract.FactoryMapper<java.lang.Exception,IOException>,
        private val recordDomainCacheStringMapper: Abstract.FactoryMapper<String,CacheDomainPost<RecordCacheState>>
    ) : PostDomainCacheMapper<RecordCacheState>() {

        override fun map(data: String): CacheDomainPost<RecordCacheState>
                = recordDomainCacheStringMapper.map(data)


        override fun map(e: Exception): CacheDomainPost<RecordCacheState>
            = CacheDomainPost.RecordCacheDomainPost.Fail(exceptionMapper.map(e))

    }

    class Read(
        private val exceptionMapper: Abstract.FactoryMapper<java.lang.Exception,IOException>
    ) : PostDomainCacheMapper<String>() {

        override fun map(data: String): CacheDomainPost<String>
                = CacheDomainPost.ReadCacheDomainPost.Success(data)

        override fun map(e: Exception): CacheDomainPost<String>
                = CacheDomainPost.ReadCacheDomainPost.Fail(exceptionMapper.map(e))
    }
}


