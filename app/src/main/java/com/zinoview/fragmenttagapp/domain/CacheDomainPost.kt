package com.zinoview.fragmenttagapp.domain

import com.zinoview.fragmenttagapp.core.Abstract
import com.zinoview.fragmenttagapp.presentation.CachePostUi
import okhttp3.internal.notifyAll
import java.io.IOException


/**
 * @author Zinoview on 01.08.2021
 * k.gig@list.ru
 */
sealed class CacheDomainPost : Abstract.PostCacheObject.Ui {

    class Success(
        private val data: String
    ) : CacheDomainPost() {

        override fun <T> map(mapper: Abstract.PostCacheMapper<T, IOException>): T
            = mapper.map(data)
    }

    class Fail(
        private val e: IOException
    ) : CacheDomainPost() {

        override fun <T> map(mapper: Abstract.PostCacheMapper<T, IOException>): T
            = mapper.map(e)
    }

}

