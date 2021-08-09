package com.zinoview.fragmenttagapp.core


/**
 * @author Zinoview on 09.08.2021
 * k.gig@list.ru
 */
interface Matches<T> {

    fun match(arg: T) : Boolean
}