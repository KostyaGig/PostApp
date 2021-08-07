package com.zinoview.fragmenttagapp.data.cache


/**
 * @author Zinoview on 01.08.2021
 * k.gig@list.ru
 */
interface Record<in T> {

    fun writeData(data: T)
}