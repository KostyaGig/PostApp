package com.zinoview.fragmenttagapp.domain.cache

import com.zinoview.fragmenttagapp.core.Abstract
import java.io.IOException


/**
 * @author Zinoview on 04.08.2021
 * k.gig@list.ru
 */

//todo replace this class and PostRecord in one abstract class
class PostReadDomainCacheMapper(
    private val exceptionMapper: Abstract.FactoryMapper<java.lang.Exception,IOException>
) : Abstract.PostCacheMapper<CacheDomainPost<String>,Exception> {

    override fun map(data: String): CacheDomainPost<String>
        = CacheDomainPost.ReadCacheDomainPost.Success(data)

    override fun map(e: Exception): CacheDomainPost<String>
        = CacheDomainPost.ReadCacheDomainPost.Fail(exceptionMapper.map(e))
}

