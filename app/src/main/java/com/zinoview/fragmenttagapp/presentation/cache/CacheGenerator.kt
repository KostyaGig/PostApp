package com.zinoview.fragmenttagapp.presentation.cache


/**
 * @author Zinoview on 09.08.2021
 * k.gig@list.ru
 */
interface CacheGenerator {

    fun generate(oldCache: String) : String

    class Base(private val newCache: String) : CacheGenerator {

        override fun generate(oldCache: String): String
            = oldCache.replace(newCache, "")
    }
}