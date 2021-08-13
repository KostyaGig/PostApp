package com.zinoview.fragmenttagapp.presentation.post

import com.zinoview.fragmenttagapp.core.Abstract
import com.zinoview.fragmenttagapp.domain.PostDomain
import java.io.IOException


/**
 * @author Zinoview on 29.07.2021
 * k.gig@list.ru
 */
class ListPostUiMapper(
    private val postUiMapper: Abstract.PostMapper<PostUi>,
    private val ioExMapper: Abstract.FactoryMapper<IOException, PostError>
) : Abstract.ListPostMapper<PostDomain, ListPostUi,IOException> {

    override fun map(list: List<PostDomain>): ListPostUi {
        val postsUi = list.map { it.map(postUiMapper) }
        return ListPostUi.Success(postsUi)
    }

    override fun map(e: IOException): ListPostUi
        = ListPostUi.Fail(ioExMapper.map(e))
}