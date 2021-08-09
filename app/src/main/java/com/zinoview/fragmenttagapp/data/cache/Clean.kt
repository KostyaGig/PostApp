package com.zinoview.fragmenttagapp.data.cache


/**
 * @author Zinoview on 08.08.2021
 * k.gig@list.ru
 */
interface Clean<T> {

    fun updateFile(newData: String) : T

    class Test(private val text: String) : Clean<String> {

        override fun updateFile(newData: String) : String = text.replace(newData,"")
    }
}