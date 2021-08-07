package com.zinoview.fragmenttagapp.data.cloud

import com.zinoview.fragmenttagapp.core.Abstract
import com.zinoview.fragmenttagapp.data.PostData


/**
 * @author Zinoview on 29.07.2021
 * k.gig@list.ru
 */
class PostDataMapper : Abstract.PostMapper<PostData> {

    override fun map(id: Int, userId: Int, title: String, body: String): PostData
        = PostData(id, userId, title, body)
}