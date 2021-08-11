package com.zinoview.fragmenttagapp.data.cache


/**
 * @author Zinoview on 08.08.2021
 * k.gig@list.ru
 */
interface Update<in S,T> {

    fun update(src: S) : T

    class Test(private val text: String) : Update<String, String> {

        override fun update(src: String) : String = text.replace(src,"")
    }
}