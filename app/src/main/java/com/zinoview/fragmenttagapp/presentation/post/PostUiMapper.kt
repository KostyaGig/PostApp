package com.zinoview.fragmenttagapp.presentation.post

import com.zinoview.fragmenttagapp.core.Abstract


/**
 * @author Zinoview on 29.07.2021
 * k.gig@list.ru
 */
class PostUiMapper : Abstract.PostMapper<PostUi> {

    override fun map(id: Int, userId: Int, title: String, body: String): PostUi
        = PostUi(id, userId, title, body)
}