package com.zinoview.fragmenttagapp.data

import com.zinoview.fragmenttagapp.core.Abstract
import com.zinoview.fragmenttagapp.domain.ListPostDomain


/**
 * @author Zinoview on 29.07.2021
 * k.gig@list.ru
 */
sealed class ListPostData : Abstract.ListObject.Domain {

    class Success(
        private val list: List<PostData>
    ) : ListPostData() {
        override fun map(mapper: Abstract.ListPostMapper<PostData, ListPostDomain, Exception>): ListPostDomain
            = mapper.map(list)
    }

    class Fail(private val e: Exception) : ListPostData() {
        override fun map(mapper: Abstract.ListPostMapper<PostData, ListPostDomain, Exception>): ListPostDomain
            = mapper.map(e)
    }
}
