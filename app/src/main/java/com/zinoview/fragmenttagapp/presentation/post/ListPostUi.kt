package com.zinoview.fragmenttagapp.presentation.post

import com.zinoview.fragmenttagapp.core.Abstract
import com.zinoview.fragmenttagapp.presentation.Communication


/**
 * @author Zinoview on 29.07.2021
 * k.gig@list.ru
 */
sealed class ListPostUi : Abstract.FactoryMapper.Ui {

    open fun addCurrentCache(currentCache: String) = Unit

    class Success(
        private val list: List<PostUi>,
    ) : ListPostUi() {

        private var currentCache: String = ""

        override fun map(src: Pair<Communication<List<UiPost>>, Abstract.PostMapper<UiPost>>) {
            val listUiPost = list.map { it.map(src.second) }
            val listUiPostWithCache = listUiPost.map { it.map(currentCache) }
            src.first.changeValue(listUiPostWithCache)
        }

        override fun addCurrentCache(currentCache: String) {
            this.currentCache = currentCache
        }

    }

    class Fail(
        private val error: PostError
    ) : ListPostUi() {

        override fun map(src: Pair<Communication<List<UiPost>>, Abstract.PostMapper<UiPost>>) =
            src.first.changeValue(listOf(UiPost.Fail(error)))
    }

}
