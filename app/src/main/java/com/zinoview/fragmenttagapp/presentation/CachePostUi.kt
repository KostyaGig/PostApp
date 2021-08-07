package com.zinoview.fragmenttagapp.presentation

import com.zinoview.fragmenttagapp.core.Abstract


/**
 * @author Zinoview on 01.08.2021
 * k.gig@list.ru
 */
sealed class CachePostUi : Abstract.FactoryMapper<CachePostCommunication,Unit> {

    override fun map(src: CachePostCommunication) = Unit

    class Success(
        private val data: String
    ) : CachePostUi() {

        override fun map(src: CachePostCommunication)
            = src.changeValue(data)
    }

    class Fail(
        private val message: String
    ) : CachePostUi() {

        override fun map(src: CachePostCommunication)
            = src.changeValue(message)
    }
}
