package com.zinoview.fragmenttagapp.domain

import com.zinoview.fragmenttagapp.core.Abstract


/**
 * @author Zinoview on 29.07.2021
 * k.gig@list.ru
 */
class PostDomainMapper : Abstract.PostMapper<PostDomain> {

    override fun map(id: Int, userId: Int, title: String, body: String): PostDomain
        = PostDomain(id, userId, title, body)
}