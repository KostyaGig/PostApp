package com.zinoview.fragmenttagapp.presentation

import com.zinoview.fragmenttagapp.core.Abstract


/**
 * @author Zinoview on 01.08.2021
 * k.gig@list.ru
 */
class ClickedUiPostMapper : Abstract.PostMapper<ClickedUiPost> {

    override fun map(id: Int, userId: Int, title: String, body: String): ClickedUiPost
        = ClickedUiPost(id,userId,body, title)
}