package com.zinoview.fragmenttagapp.data.cloud

import com.zinoview.fragmenttagapp.data.DataSource


/**
 * @author Zinoview on 29.07.2021
 * k.gig@list.ru
 */

interface PostCloudDataSource : DataSource<List<PostCloud>> {

    class Base(
        private val postService: PostService
    ) : PostCloudDataSource {

        override suspend fun data(): List<PostCloud> = postService.posts()
    }
}

