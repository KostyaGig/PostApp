package com.zinoview.fragmenttagapp.presentation

import com.zinoview.fragmenttagapp.core.Abstract
import com.zinoview.fragmenttagapp.presentation.post.UiPost


/**
 * @author Zinoview on 13.08.2021
 * k.gig@list.ru
 */

class RecyclerViewTypeMapper : Abstract.FactoryMapper<UiPost, Int> {
    override fun map(src: UiPost): Int = when(src) {
        is UiPost.Progress -> 0
        is UiPost.Base -> 1
        is UiPost.Cached -> 2
        else -> 3
    }
}