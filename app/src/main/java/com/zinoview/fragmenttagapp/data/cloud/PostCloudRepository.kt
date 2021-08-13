package com.zinoview.fragmenttagapp.data.cloud

import com.zinoview.fragmenttagapp.core.Abstract
import com.zinoview.fragmenttagapp.data.ListPostData
import com.zinoview.fragmenttagapp.data.PostData
import java.lang.Exception


/**
 * @author Zinoview on 29.07.2021
 * k.gig@list.ru
 */
interface PostCloudRepository {

    suspend fun listPostData() : ListPostData

    class Base(
        private val postCloudDataSource: PostCloudDataSource,
        private val postDataMapper: Abstract.PostMapper<PostData>
    ) : PostCloudRepository {
        override suspend fun listPostData(): ListPostData = try {
            val postsCloud = postCloudDataSource.data()
            val postsData = map(postsCloud)

            ListPostData.Success(postsData)
        } catch (e: Exception) {
            ListPostData.Fail(e)
        }

        private fun map(postsCloud: List<PostCloud>) : List<PostData>
            = postsCloud.map { postCloud -> postCloud.map(postDataMapper) }
    }
}