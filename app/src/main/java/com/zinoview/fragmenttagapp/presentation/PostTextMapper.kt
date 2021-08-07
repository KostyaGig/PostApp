package com.zinoview.fragmenttagapp.presentation

import com.zinoview.fragmenttagapp.core.Abstract


/**
 * @author Zinoview on 04.08.2021
 * k.gig@list.ru
 */
class PostTextMapper : Abstract.PostMapper<String> {

    override fun map(id: Int, userId: Int, title: String, body: String): String
            = "Post id: $id \n\n, User id: $userId \n\n Title: $title \n\n Body post: $body"
}