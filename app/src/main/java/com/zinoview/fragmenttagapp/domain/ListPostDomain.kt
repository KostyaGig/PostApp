package com.zinoview.fragmenttagapp.domain

import com.zinoview.fragmenttagapp.core.Abstract
import com.zinoview.fragmenttagapp.presentation.post.ListPostUi
import java.io.IOException


/**
 * @author Zinoview on 29.07.2021
 * k.gig@list.ru
 */
sealed class ListPostDomain : Abstract.ListObject.Ui {

    class Success(
        private val list: List<PostDomain>
    ) : ListPostDomain() {

        override fun map(mapper: Abstract.ListPostMapper<PostDomain, ListPostUi, IOException>): ListPostUi
            = mapper.map(list)
    }

    class Fail(private val e: IOException) : ListPostDomain() {
        override fun map(mapper: Abstract.ListPostMapper<PostDomain, ListPostUi, IOException>): ListPostUi
            = mapper.map(e)
    }

}
