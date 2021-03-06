package com.zinoview.fragmenttagapp.presentation.cache

import com.zinoview.fragmenttagapp.core.Abstract
import com.zinoview.fragmenttagapp.presentation.Communication

/**
 * @author Zinoview on 01.08.2021
 * k.gig@list.ru
 */
abstract class CacheUiPost<T : Any> : Abstract.FactoryMapper<Communication<T>,Unit> {

    override fun map(src: Communication<T>) = Unit

    abstract class RecordCacheUiPost : CacheUiPost<RecordCacheState>() {

        class Success(
            private val data: String
        ) : RecordCacheUiPost() {

            override fun map(src: Communication<RecordCacheState>)
                = src.changeValue(RecordCacheState.Success(data))
        }

        class UpdateSuccess(
            private val message: String
        ) : RecordCacheUiPost() {

            override fun map(src: Communication<RecordCacheState>)
                = src.changeValue(RecordCacheState.UpdateSuccess(message))
        }

        class Fail(
            private val e: String
        ) : RecordCacheUiPost() {
            override fun map(src: Communication<RecordCacheState>)
                = src.changeValue(RecordCacheState.Fail(e))
        }
    }

    abstract class ReadCacheUiPost : CacheUiPost<String>() {
        class Success(
            private val data: String
        ) : ReadCacheUiPost() {

            override fun map(src: Communication<String>)
                = src.changeValue(data)
        }

        class Fail(
            private val e: String
        ) : ReadCacheUiPost() {
            override fun map(src: Communication<String>)
                = src.changeValue(e)
        }
    }

}
