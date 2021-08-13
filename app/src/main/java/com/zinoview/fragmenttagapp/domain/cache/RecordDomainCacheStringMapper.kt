package com.zinoview.fragmenttagapp.domain.cache

import com.zinoview.fragmenttagapp.R
import com.zinoview.fragmenttagapp.core.Abstract
import com.zinoview.fragmenttagapp.core.Resource
import com.zinoview.fragmenttagapp.presentation.cache.RecordCacheState
import java.lang.IllegalArgumentException


/**
 * @author Zinoview on 13.08.2021
 * k.gig@list.ru
 */

class RecordDomainCacheStringMapper(
    private val resource: Resource
) : Abstract.FactoryMapper<String, CacheDomainPost<RecordCacheState>> {

    override fun map(src: String): CacheDomainPost<RecordCacheState> = when(src) {
        resource.string(R.string.success_updating_cache_file) -> CacheDomainPost.RecordCacheDomainPost.UpdateSuccess(src)
        resource.string(R.string.success_writing_data_to_cache_message) -> CacheDomainPost.RecordCacheDomainPost.Success(src)

        else -> throw IllegalArgumentException("RecordDomainCacheStringMapper argument $src not found")
    }
}