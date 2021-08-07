package com.zinoview.fragmenttagapp.domain

import com.zinoview.fragmenttagapp.core.Abstract
import com.zinoview.fragmenttagapp.data.PostData
import com.zinoview.fragmenttagapp.data.PostRepository
import java.lang.Exception


/**
 * @author Zinoview on 29.07.2021
 * k.gig@list.ru
 */
interface PostInteractor {

    suspend fun listPostDomain() : ListPostDomain

    class Base(
        private val postRepository: PostRepository,
        private val postDomainMapper: Abstract.ListPostMapper<PostData, ListPostDomain, Exception>
    ) : PostInteractor {
        override suspend fun listPostDomain(): ListPostDomain {
            val postsData = postRepository.listPostData()
            return postsData.map(postDomainMapper)
        }
    }

}