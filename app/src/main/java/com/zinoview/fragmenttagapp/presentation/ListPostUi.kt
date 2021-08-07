package com.zinoview.fragmenttagapp.presentation

import com.zinoview.fragmenttagapp.core.Abstract


/**
 * @author Zinoview on 29.07.2021
 * k.gig@list.ru
 */
sealed class ListPostUi : Abstract.FactoryMapper.Ui {

    class Success(
        private val list: List<PostUi>,
    ) : ListPostUi() {
        override fun map(src: Pair<PostCommunication, Abstract.PostMapper<UiPost>>) {
            val listUiPost = list.map { it.map(src.second) }
            src.first.changeValue(listUiPost)
        }
    }

    class Fail(
        private val error: PostError
    ) : ListPostUi(){
        override fun map(src: Pair<PostCommunication, Abstract.PostMapper<UiPost>>) {
            src.first.changeValue(listOf(UiPost.Fail(error)))
        }
    }

}
