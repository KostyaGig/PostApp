package com.zinoview.fragmenttagapp.data

import com.zinoview.fragmenttagapp.core.Abstract
import com.zinoview.fragmenttagapp.presentation.CachePostUi
import okhttp3.internal.notifyAll
import java.io.IOException


/**
 * @author Zinoview on 01.08.2021
 * k.gig@list.ru
 */
sealed class CacheDataPost : Abstract.PostCacheObject.Domain {

    class Success(
        private val data: String
    ) : CacheDataPost() {

        override fun <T> map(mapper: Abstract.PostCacheMapper<T, Exception>): T
            = mapper.map(data)
    }

    class Fail(
        private val e: Exception
    ) : CacheDataPost() {

        override fun <T> map(mapper: Abstract.PostCacheMapper<T, Exception>): T
            = mapper.map(e)
    }

}

