package com.zinoview.fragmenttagapp.presentation

import com.zinoview.fragmenttagapp.core.Abstract


/**
 * @author Zinoview on 29.07.2021
 * k.gig@list.ru
 */
sealed class PostError(private val message: String) {

    fun <T> map(mapper: Abstract.PostMapper<T>) = mapper.map(-1,-1,message,"")

    class ServiceUnavailableError(message: String) : PostError(message)

    class NoConnectionError(message: String) : PostError(message)

    class GenericError(message: String) : PostError(message)

}
