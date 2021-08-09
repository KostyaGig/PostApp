package com.zinoview.fragmenttagapp.presentation.cache

import com.zinoview.fragmenttagapp.core.Abstract
import java.io.IOException


/**
 * @author Zinoview on 02.08.2021
 * k.gig@list.ru
 */

abstract class UiPostCacheMapper<T: Any> : Abstract.PostCacheMapper<CacheUiPost<T>,IOException> {

    class Record(
        private val ioExceptionMapper: Abstract.FactoryMapper<IOException, String>,
        private val recordUiCacheStringMapper: Abstract.FactoryMapper<String, CacheUiPost<RecordCacheState>>
    ) : UiPostCacheMapper<RecordCacheState>() {

        override fun map(data: String): CacheUiPost<RecordCacheState>
            = recordUiCacheStringMapper.map(data)

        override fun map(e: IOException): CacheUiPost<RecordCacheState>
            = CacheUiPost.RecordCacheUiPost.Fail(ioExceptionMapper.map(e))

    }

    class Read(
        private val ioExceptionMapper: Abstract.FactoryMapper<IOException, String>
    ) : UiPostCacheMapper<String>() {

        override fun map(data: String): CacheUiPost<String>
            = CacheUiPost.ReadCacheUiPost.Success(data)


        override fun map(e: IOException): CacheUiPost<String>
            = CacheUiPost.ReadCacheUiPost.Fail(ioExceptionMapper.map(e))
    }
}