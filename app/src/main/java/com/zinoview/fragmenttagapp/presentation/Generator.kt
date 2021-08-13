package com.zinoview.fragmenttagapp.presentation

import java.io.Serializable


/**
 * @author Zinoview on 09.08.2021
 * k.gig@list.ru
 */
interface Generator<S,R> {

    fun generate(src: S) : R

    class Cache(private val newCache: String) : Generator<String, String> {

        override fun generate(src: String): String
            = src.replace(newCache, "")
    }

    class Bundle : Generator<Pair<String, Serializable>, android.os.Bundle> {
        override fun generate(src: Pair<String, Serializable>): android.os.Bundle = android.os.Bundle().apply {
            putSerializable(src.first, src.second)
        }
    }

}