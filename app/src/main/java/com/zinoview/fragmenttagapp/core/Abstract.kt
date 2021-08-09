package com.zinoview.fragmenttagapp.core

import com.zinoview.fragmenttagapp.R
import com.zinoview.fragmenttagapp.data.PostData
import com.zinoview.fragmenttagapp.data.cache.FailUpdatedFileException
import com.zinoview.fragmenttagapp.domain.*
import com.zinoview.fragmenttagapp.domain.cache.CacheDomainPost
import com.zinoview.fragmenttagapp.presentation.*
import com.zinoview.fragmenttagapp.presentation.cache.CacheUiPost
import com.zinoview.fragmenttagapp.presentation.cache.RecordCacheState
import com.zinoview.fragmenttagapp.presentation.fragment.BaseFragment
import com.zinoview.fragmenttagapp.presentation.fragment.CacheFragment
import com.zinoview.fragmenttagapp.presentation.fragment.ListPostFragment
import java.io.FileNotFoundException
import java.io.IOException
import java.lang.IllegalArgumentException
import java.net.HttpRetryException
import java.net.UnknownHostException


/**
 * @author Zinoview on 29.07.2021
 * k.gig@list.ru
 */
abstract class Abstract {

    interface Object {
        fun <T> map(mapper: PostMapper<T>) : T
    }

    interface PostMapper<T> {
        fun map(id: Int, userId: Int, title: String, body: String) : T
    }

    interface ListObject<E,T,F> {
        fun map(mapper: ListPostMapper<E,T,F>) : T

        interface Domain : ListObject<PostData,ListPostDomain,Exception>

        interface Ui : ListObject<PostDomain,ListPostUi,IOException>
    }

    interface ListPostMapper<E,T,F> {
        fun map(list: List<E>) : T

        fun map(e: F) : T
    }

    interface PostCacheObject<T,F> {
        fun <T> map(mapper: PostCacheMapper<T,F>) : T

        interface Domain<T : Any> : PostCacheObject<CacheDomainPost<T>,Exception>

        interface Ui<T : Any> : PostCacheObject<CacheUiPost<T>,IOException>
    }
    interface PostCacheMapper<T,F> {

        fun map(data: String) : T
        fun map(e: F) : T

    }

    interface FactoryMapper<S,R> {
        fun map(src: S) : R

        interface Ui : FactoryMapper<Pair<PostCommunication,PostMapper<UiPost>>,Unit>

        class CloudExceptionMapper : FactoryMapper<Exception,IOException> {
            override fun map(src: Exception): IOException = when(src) {
                is UnknownHostException -> NoConnectionException()
                is HttpRetryException -> ServiceUnavailableException()
                else -> GenericException()
            }
        }

        class IOExceptionMapper(
            private val resource: Resource
        ) : FactoryMapper<IOException,PostError> {
            override fun map(src: IOException): PostError = when(src) {
                is NoConnectionException -> PostError.NoConnectionError(resource.string(R.string.no_connection_message))
                is ServiceUnavailableException -> PostError.ServiceUnavailableError(resource.string(R.string.service_unavailable_message))
                else  -> PostError.GenericError(resource.string(R.string.some_went_wrong_message))
            }
        }

        class MenuItemIdMapper : FactoryMapper<Int, BaseFragment> {
            override fun map(src: Int): BaseFragment = when(src) {
                R.id.posts_item -> ListPostFragment()
                else -> CacheFragment()
            }
        }

        class RecyclerViewTypeMapper : FactoryMapper<UiPost, Int> {
             override fun map(src: UiPost): Int = when(src) {
                 is UiPost.Progress -> 0
                 is UiPost.Base -> 1
                 is UiPost.Cached -> 2
                 else -> 3
            }
        }

        class CacheExceptionMapper : FactoryMapper<Exception,IOException> {
            override fun map(src: Exception): IOException = when(src) {
                is FileNotFoundException -> FileNotCreatedException()
                is FailUpdatedFileException -> com.zinoview.fragmenttagapp.domain.FailUpdatedFileException()
                else -> GenericFileException()
            }
        }

        class CacheErrorMessageMapper(
            private val resource: Resource
        ) : FactoryMapper<IOException,String> {
            override fun map(src: IOException): String = when(src) {
                is FileNotCreatedException -> resource.string(R.string.not_created_file_message)
                else -> "${resource.string(R.string.some_went_wrong_with_exception_message)} ${src::javaClass}"
            }
        }

        class RecordDomainCacheStringMapper : FactoryMapper<String,CacheDomainPost<RecordCacheState>> {
            override fun map(src: String): CacheDomainPost<RecordCacheState> = when(src) {
                "Success update cache file" -> CacheDomainPost.RecordCacheDomainPost.UpdateSuccess(src)
                "Success writing data to cache" -> CacheDomainPost.RecordCacheDomainPost.Success(src)
                else -> throw IllegalArgumentException("RecordDomainCacheStringMapper argument $src not found")
            }
        }

        class RecordUiCacheStringMapper : FactoryMapper<String,CacheUiPost<RecordCacheState>> {
            override fun map(src: String): CacheUiPost<RecordCacheState> = when(src) {
                "Success update cache file" -> CacheUiPost.RecordCacheUiPost.UpdateSuccess(src)
                "Success writing data to cache" -> CacheUiPost.RecordCacheUiPost.Success(src)
                else -> throw IllegalArgumentException("RecordUiCacheStringMapper argument $src not found")
            }
        }
    }

}