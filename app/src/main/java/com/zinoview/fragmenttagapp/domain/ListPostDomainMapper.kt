package com.zinoview.fragmenttagapp.domain

import com.zinoview.fragmenttagapp.core.Abstract
import com.zinoview.fragmenttagapp.data.PostData
import java.io.IOException


/**
 * @author Zinoview on 29.07.2021
 * k.gig@list.ru
 */
class ListPostDomainMapper(
    private val mapper: Abstract.PostMapper<PostDomain>,
    private val exceptionMapper: Abstract.FactoryMapper<Exception,IOException>
): Abstract.ListPostMapper<PostData, ListPostDomain, Exception> {

    override fun map(list: List<PostData>): ListPostDomain {
        val postsData = list.map {
            it.map(mapper)
        }
        return ListPostDomain.Success(postsData)
    }

    override fun map(e: Exception): ListPostDomain
        = ListPostDomain.Fail(exceptionMapper.map(e))

}