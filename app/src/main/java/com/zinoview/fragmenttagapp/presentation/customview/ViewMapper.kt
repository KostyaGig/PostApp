package com.zinoview.fragmenttagapp.presentation.customview

import com.zinoview.fragmenttagapp.core.Abstract


/**
 * @author Zinoview on 02.08.2021
 * k.gig@list.ru
 */
interface ViewMapper : Abstract.PostMapper<Unit> {

    override fun map(postId: Int,userId: Int,body: String,title: String)
}