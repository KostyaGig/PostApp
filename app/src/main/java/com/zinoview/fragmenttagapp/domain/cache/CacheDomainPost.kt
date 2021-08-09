package com.zinoview.fragmenttagapp.domain.cache

import com.zinoview.fragmenttagapp.core.Abstract
import com.zinoview.fragmenttagapp.data.cache.CacheDataPost
import com.zinoview.fragmenttagapp.presentation.cache.RecordCacheState
import java.io.IOException


/**
 * @author Zinoview on 01.08.2021
 * k.gig@list.ru
 */
abstract class CacheDomainPost<T : Any> : Abstract.PostCacheObject.Ui<T> {

    abstract class RecordCacheDomainPost : CacheDomainPost<RecordCacheState>() {
        class Success(
            private val data: String
        ) : RecordCacheDomainPost() {

            override fun <T> map(mapper: Abstract.PostCacheMapper<T, IOException>): T
                = mapper.map(data)
        }

        class UpdateSuccess(
            private val message: String
        ) : RecordCacheDomainPost() {

            override fun <T> map(mapper: Abstract.PostCacheMapper<T, IOException>): T
                = mapper.map(message)
        }

        class Fail(
            private val e: IOException
        ) : RecordCacheDomainPost() {

            override fun <T> map(mapper: Abstract.PostCacheMapper<T, IOException>): T
                = mapper.map(e)
        }
    }

    abstract class ReadCacheDomainPost : CacheDomainPost<String>() {
        class Success(
            private val data: String
        ) : ReadCacheDomainPost() {

            override fun <T> map(mapper: Abstract.PostCacheMapper<T, IOException>): T
                    = mapper.map(data)

        }

        class Fail(
            private val e: IOException
        ) : ReadCacheDomainPost() {

            override fun <T> map(mapper: Abstract.PostCacheMapper<T, IOException>): T
                    = mapper.map(e)

        }
    }

}

