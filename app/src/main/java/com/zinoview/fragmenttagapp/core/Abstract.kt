package com.zinoview.fragmenttagapp.core

import android.view.MenuItem
import com.zinoview.fragmenttagapp.R
import com.zinoview.fragmenttagapp.data.PostData
import com.zinoview.fragmenttagapp.domain.ListPostDomain
import com.zinoview.fragmenttagapp.domain.NoConnectionException
import com.zinoview.fragmenttagapp.domain.PostDomain
import com.zinoview.fragmenttagapp.domain.ServiceUnavailableException
import com.zinoview.fragmenttagapp.domain.cache.CacheDomainPost
import com.zinoview.fragmenttagapp.presentation.*
import com.zinoview.fragmenttagapp.presentation.cache.CacheUiPost
import com.zinoview.fragmenttagapp.presentation.cache.RecordCacheState
import com.zinoview.fragmenttagapp.presentation.navigation.TypeFragmentNavigator
import com.zinoview.fragmenttagapp.presentation.post.ListPostUi
import com.zinoview.fragmenttagapp.presentation.post.PostError
import com.zinoview.fragmenttagapp.presentation.post.UiPost
import java.io.IOException
import java.lang.IllegalArgumentException


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

        interface Domain : ListObject<PostData, ListPostDomain,Exception>

        interface Ui : ListObject<PostDomain, ListPostUi,IOException>
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

        interface Ui : FactoryMapper<Pair<Communication<List<UiPost>>,PostMapper<UiPost>>,Unit>
    }
}