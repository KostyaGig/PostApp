package com.zinoview.fragmenttagapp.presentation

import com.zinoview.fragmenttagapp.core.Abstract


/**
 * @author Zinoview on 31.07.2021
 * k.gig@list.ru
 */
class UiPostMapper : Abstract.PostMapper<UiPost> {
    override fun map(id: Int, userId: Int, title: String, body: String): UiPost
        = UiPost.Base(id,userId,body,title)
}