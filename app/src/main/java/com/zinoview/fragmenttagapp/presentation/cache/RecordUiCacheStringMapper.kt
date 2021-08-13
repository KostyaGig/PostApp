package com.zinoview.fragmenttagapp.presentation.cache

import com.zinoview.fragmenttagapp.R
import com.zinoview.fragmenttagapp.core.Abstract
import com.zinoview.fragmenttagapp.core.Resource
import java.lang.IllegalArgumentException


/**
 * @author Zinoview on 13.08.2021
 * k.gig@list.ru
 */

class RecordUiCacheStringMapper(
    private val resource: Resource
) : Abstract.FactoryMapper<String, CacheUiPost<RecordCacheState>> {

    override fun map(src: String): CacheUiPost<RecordCacheState> = when(src) {
        resource.string(R.string.success_updating_cache_file) -> CacheUiPost.RecordCacheUiPost.UpdateSuccess(src)
        resource.string(R.string.success_writing_data_to_cache_message) -> CacheUiPost.RecordCacheUiPost.Success(src)
        else -> throw IllegalArgumentException("RecordUiCacheStringMapper argument $src not found")
    }
}