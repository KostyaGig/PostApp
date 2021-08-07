package com.zinoview.fragmenttagapp.domain

import com.zinoview.fragmenttagapp.core.Abstract
import java.io.IOException


/**
 * @author Zinoview on 04.08.2021
 * k.gig@list.ru
 */
class PostDomainCacheMapper(
    private val ioExceptionMapper: Abstract.FactoryMapper<java.lang.Exception,IOException>
) : Abstract.PostCacheMapper<CacheDomainPost,Exception> {
    override fun map(data: String): CacheDomainPost
        = CacheDomainPost.Success(data)

    override fun map(e: Exception): CacheDomainPost
        = CacheDomainPost.Fail(ioExceptionMapper.map(e))
}