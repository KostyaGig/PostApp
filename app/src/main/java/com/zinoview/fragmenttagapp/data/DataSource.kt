package com.zinoview.fragmenttagapp.data


/**
 * @author Zinoview on 29.07.2021
 * k.gig@list.ru
 */
interface DataSource<out T> {

    suspend fun data() : T
}