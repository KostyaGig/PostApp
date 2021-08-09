package com.zinoview.fragmenttagapp.data.cache

import com.zinoview.fragmenttagapp.core.Abstract
import com.zinoview.fragmenttagapp.presentation.cache.RecordCacheState


/**
 * @author Zinoview on 01.08.2021
 * k.gig@list.ru
 */
abstract class CacheDataPost<T : Any> : Abstract.PostCacheObject.Domain<T> {

    abstract class RecordCacheDataPost : CacheDataPost<RecordCacheState>() {

        class Success(
            private val data: String
        ) : RecordCacheDataPost() {

            override fun <T> map(mapper: Abstract.PostCacheMapper<T, Exception>): T
                    = mapper.map(data)
        }

        class UpdateSuccess(
            private val message: String
        ) : RecordCacheDataPost() {

            override fun <T> map(mapper: Abstract.PostCacheMapper<T, Exception>): T
                = mapper.map(message)
        }

        class Fail(
            private val e: Exception
        ) : RecordCacheDataPost() {

            override fun <T> map(mapper: Abstract.PostCacheMapper<T, Exception>): T
                    = mapper.map(e)
        }
    }

    abstract class ReadCacheDataPost : CacheDataPost<String>() {
        class Success(
            private val data: String
        ) : ReadCacheDataPost() {

            override fun <T> map(mapper: Abstract.PostCacheMapper<T, Exception>): T
                    = mapper.map(data)
        }

        class Fail(
            private val e: Exception
        ) : ReadCacheDataPost() {

            override fun <T> map(mapper: Abstract.PostCacheMapper<T, Exception>): T
                    = mapper.map(e)
        }
    }

}

