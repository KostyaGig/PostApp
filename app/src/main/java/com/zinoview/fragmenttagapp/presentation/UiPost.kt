package com.zinoview.fragmenttagapp.presentation

import com.zinoview.fragmenttagapp.core.Abstract
import com.zinoview.fragmenttagapp.core.Matches
import com.zinoview.fragmenttagapp.presentation.customview.ViewMapper


/**
 * @author Zinoview on 29.07.2021
 * k.gig@list.ru
 */
sealed class UiPost : Abstract.Object ,Matches<String> {

    override fun <T> map(mapper: Abstract.PostMapper<T>) : T = mapper.map(-1,-1,"","")

    open fun map(currentCache: String) : UiPost = Empty

    override fun match(arg: String): Boolean = false

    object Empty : UiPost()

    object Progress : UiPost()

    class Base(
        private val postId: Int,
        private val userId: Int,
        private val body: String,
        private val title: String
    ) : UiPost() {

        override fun <T> map(mapper: Abstract.PostMapper<T>): T
            = mapper.map(postId,userId, title, body)

        override fun match(arg: String): Boolean
            = arg.contains(body)

        override fun map(currentCache: String) : UiPost = if (match(currentCache)) {
                Cached(postId, userId, body, title)
            } else {
                this
            }
        }

    class Cached(
        private val postId: Int,
        private val userId: Int,
        private val body: String,
        private val title: String
    ) : UiPost() {

        override fun <T> map(mapper: Abstract.PostMapper<T>): T
            = mapper.map(postId,userId, title, body)
    }

    class Fail(
        private val error: PostError
    ) : UiPost() {

        override fun <T> map(mapper: Abstract.PostMapper<T>): T
            = error.map(mapper)
    }

}


