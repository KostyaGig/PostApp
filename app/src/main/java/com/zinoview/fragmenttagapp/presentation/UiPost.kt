package com.zinoview.fragmenttagapp.presentation

import com.zinoview.fragmenttagapp.core.Abstract
import com.zinoview.fragmenttagapp.presentation.customview.ViewMapper


/**
 * @author Zinoview on 29.07.2021
 * k.gig@list.ru
 */
sealed class UiPost {

    open fun map(mapper: ViewMapper){}

    open fun <T> map(mapper: Abstract.PostMapper<T>) : T = mapper.map(0,-1,"","")

    open fun map(postListener: PostItemListener) {}

    object Progress : UiPost()

    class Base(
        private val postId: Int,
        private val userId: Int,
        private val body: String,
        private val title: String
    ) : UiPost() {

        override fun map(mapper: ViewMapper)
            = mapper.map(postId,userId,body,title)

        override fun <T> map(mapper: Abstract.PostMapper<T>): T
            = mapper.map(postId,userId, title, body)

    }

    class Fail(
        private val error: PostError
    ) : UiPost() {

        override fun map(mapper: ViewMapper) {
            error.map(mapper)
        }
    }

}
