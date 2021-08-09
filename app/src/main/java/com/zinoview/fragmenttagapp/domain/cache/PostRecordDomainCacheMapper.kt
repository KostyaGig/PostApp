package com.zinoview.fragmenttagapp.domain.cache

import com.zinoview.fragmenttagapp.core.Abstract
import com.zinoview.fragmenttagapp.domain.cache.CacheDomainPost
import com.zinoview.fragmenttagapp.presentation.cache.RecordCacheState
import java.io.IOException


/**
 * @author Zinoview on 04.08.2021
 * k.gig@list.ru
 */
class PostRecordDomainCacheMapper(
    private val ioExceptionMapper: Abstract.FactoryMapper<java.lang.Exception,IOException>,
    private val recordDomainCacheStringMapper: Abstract.FactoryMapper<String,CacheDomainPost<RecordCacheState>>
) : Abstract.PostCacheMapper<CacheDomainPost<RecordCacheState>,Exception> {

    override fun map(data: String): CacheDomainPost<RecordCacheState>
        = recordDomainCacheStringMapper.map(data)


    override fun map(e: Exception): CacheDomainPost<RecordCacheState>
        = CacheDomainPost.RecordCacheDomainPost.Fail(ioExceptionMapper.map(e))
}