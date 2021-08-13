package com.zinoview.fragmenttagapp.data

import com.zinoview.fragmenttagapp.core.Abstract


/**
 * @author Zinoview on 29.07.2021
 * k.gig@list.ru
 */
data class PostData(
    private val id: Int,
    private val userId: Int,
    private val title: String,
    private val body: String
) : Abstract.Object {

    override fun <T> map(mapper: Abstract.PostMapper<T>): T = mapper.map(id, userId, title, body)
}